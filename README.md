spock-extensions
================
Currently this project contains one extension for the Spock framework https://github.com/spockframework/spock

The extension provides an annotation @SingleExecution.

The main purpose of SingleExecution is to allow the usage of data tables to test the results of a method that returns
a list of items.  This can best be illustrated by an example:

```
    @SingleExecution ('calculatePayments')
    def "Should have implicit result field available for all tests"() {
        when: 
        //result is implicitly created by the annotation
        def row = result[record]

        then: 
        assert row == value
        assert row.size() == size

        where:
        record | baseAmount | fees | tax
        0      | 20.00      | 5.00 | 2.00
        1      | 10.00      | 5.00 | 1.00
        2      | 1.00       | 0.00 | 0.10 
    }

    //This method gets called one time, regardless of the number of rows in the where clause
    def calculatePayments = {
      //This would normally call our method under test  
      return [
        new Payment(baseAmount:20.00, fees:5.00, tax:2.00),
        new Payment(baseAmount:10.00, fees:5.00, tax:1.00),
        new Payment(baseAmount:1.00, fees:0.00, tax:0.10)
      ]
    } 


```

In the previous example:
* We annotate our method with @SingleExecution and provide the name of a closure.
  * The closure provided should run the code under test and return a data structure with an index
* the when clause retrieves the record from the result using an index provided in the where clause
  * result is an implicit variable created by the annotation
* We then use the where clause to validate each item in the list.
  * The first item in our data table gives us a way to retrieve the item from the list
  * The other items validate the data

@SingleExecution will only run the specified method one time regardless of the number of rows in the where clause.
Items in the setup and when clauses run for every iteration specified in the where clause.  This is desired
behavior most of the time, but if we have a list of data being generated, as in the example above, it's nice to be
able to validate it with a data table while still only executing the code under test one time.
