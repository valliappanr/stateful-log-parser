# stateful-log-parser

* There are scenarios where the log file dumps the state of an object line by line (as yaml, csv, json) and the first line
contains the unique identifier of the object and the state follows after that in a specified format. For example,

Add card failed for customer id: 140
 error message: invalid card 
 Card Type: Master			
Add card failed for customer id: 2 with result
 error message: invalid card
 Card Type: Visa


After parsing the data we would contain two instances Customer information with unique id and the reason for the failure. The 
aim is to make it as generic as possible using scala / cats / shapeless / monocle.


# Pre-requisite
* Scala
* Cats
* Monocle


# Quick Start - Guide
* TestDataGenerator - used to generate sample data do dump the customer card transaction instances, which can be used to test
the functionality.
* Sample test data is there  under src\test\resources for testing
* TestCustomerCardProcessor - loads the sample test data and verifies that the data is parsed correctly.

#To do
* Parse the object state using generics / case clasases
* Make the module as generic as possible so that different data format could be processed.
