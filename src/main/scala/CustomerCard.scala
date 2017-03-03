import cats.data.State
import monocle.Lens
import monocle.macros.GenLens

/**
  * Created by valli on 27/02/2017.
  */
case class CustomerCardInput (customerId: String, lines: Vector[String] = Vector.empty)

case class CustomerCards (activeCardInput: Option[CustomerCardInput] = None, cards: Vector[CustomerCardInput] = Vector.empty)


object CustomerCardProcessor {

  type CustomerCardState[A] = State[CustomerCardInput, A]
  type CustomerCardsState[A] = State[CustomerCards, A]

  val pattern = ".*Add card failed for customer id: (\\d+).*".r

  val cardsInputLens: Lens[CustomerCards, Option[CustomerCardInput]] = GenLens[CustomerCards](_.activeCardInput)
  val cardInputLineLens: Lens[CustomerCardInput, Vector[String]] = GenLens[CustomerCardInput](_.lines)

  def updateCustomerCardInputForLine(line: String): CustomerCardsState[Unit] = State.modify { s =>
    import monocle.std.option.some
    (cardsInputLens composePrism some composeLens cardInputLineLens).modify(lines => lines :+ line)(s)
  }
  def updateCustomerCardsCompletedActiveCardInput() : CustomerCardsState[Unit] = State.modify { s => {
    s.activeCardInput match {
      case None => s.copy(cards = s.cards)
      case Some(cardInput) => s.copy(cards = s.cards :+ cardInput)
    }
  }
  }

  def createNewActiveCardInput(customerId: String) : CustomerCardsState[Unit] = State.modify { s =>
    s.copy(cards = s.cards, activeCardInput = Some(CustomerCardInput(customerId)))
  }


  def updateAndCreateNewCardInput(line: String, customerId: String) : CustomerCardsState[Unit] = {
    for {
      s2 <- updateCustomerCardsCompletedActiveCardInput
      s3 <- createNewActiveCardInput(customerId)
    } yield(s3)
  }

  def isLineWithCustomerId(line: String): Boolean = {
    line match {
      case pattern(_) => true
      case _ => false
    }
  }

  def getCustomerId(line: String): String = {
    val pattern(customerId) = line
    customerId
  }
  def completeLine(customerCards: CustomerCards): CustomerCards = {
    updateCustomerCardsCompletedActiveCardInput().run(customerCards).value._1
  }
  def processInputLine(line: String, customerCards: CustomerCards ) : CustomerCards = {
    if (isLineWithCustomerId(line)) {
      val customerId = getCustomerId(line)
      updateAndCreateNewCardInput(line, customerId).run(customerCards).value._1
    } else {
      updateCustomerCardInputForLine(line).run(customerCards).value._1
    }
  }
  def processInputLines(lines: Seq[String]):  CustomerCards = {
    def doProcessInputLine(lines: Seq[String], currentCards: CustomerCards = CustomerCards()): CustomerCards = {
      lines match {
        case Nil => completeLine(currentCards)
        case x +: xs => {
          val processedState = processInputLine(x, currentCards)
          doProcessInputLine(xs, processedState)
        }
      }
    }
    doProcessInputLine(lines)
  }

  def processLinesFromFile(filePath: String) : CustomerCards = {
    val linesFromFile = io.Source.fromInputStream(getClass.getResourceAsStream(filePath)).mkString.split("\r\n").toSeq
    processInputLines(linesFromFile)
  }
}