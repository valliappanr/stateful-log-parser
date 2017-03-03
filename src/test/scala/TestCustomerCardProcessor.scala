import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

/**
  * Created by valli on 02/03/2017.
  */
class TestCustomerCardProcessor extends FeatureSpec with GivenWhenThen with Matchers {

  feature("Parsing of Customer card processor") {
    scenario("Check whether valid log file is processed properly") {
      Given("A file with 10 failed customer card transactions")
      val file = "sample-card-txn.log"
      When("processing the log file")
      val customerCardTxns = CustomerCardProcessor.processLinesFromFile(file)
      Then("It should have parsed 10 transactions correctly")
      customerCardTxns.cards.size shouldBe 10
    }
  }

}
