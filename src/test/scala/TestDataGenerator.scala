import org.scalatest.{FeatureSpec, GivenWhenThen}

import scala.util.Random

/**
  * Created by valli on 01/03/2017.
  */

case class CustomerCardTxn(customerId: Long, message: String, cardType: String)


class TestDataGenerator extends FeatureSpec with GivenWhenThen {

  val messages = Seq("address missmatch", "funds not available", "invalid card")

  val cardTypes = Seq("Visa", "Master", "Maestro")

  val random = Random

  feature("test data generator"){
    scenario("create random data generator for a case class") {
      createRandomCardTxns(10).map(cardTxn => {
        s"Add card failed for customer id: ${cardTxn.customerId} with result\r\n error message: ${cardTxn.message} \r\n Card Type: ${cardTxn.cardType}"
      }).foreach(println)
    }
  }

  def createRandomCardTxns(no: Int): Seq[CustomerCardTxn] = {
    (1 to no).map(no => {
      val customerId = random.nextInt(100)
      val cardType = random.nextInt(cardTypes.size)
      val messageType = random.nextInt(messages.size)
      CustomerCardTxn(customerId, messages(messageType), cardTypes(cardType))
    })
  }
}