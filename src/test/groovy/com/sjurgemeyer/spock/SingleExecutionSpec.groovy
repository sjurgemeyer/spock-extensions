package com.sjurgemeyer.spock

import spock.lang.Shared
import spock.lang.Specification

class SingleExecutionSpec extends Specification {
    
    @SingleExecution ('doStuff')
    def "Should have implicit result field available for all tests"() {
        when:
        def row = result[record]

        then:
        assert row == value

        where:
        record | value
        0   | 10
        1   | 20
        2   | 30
    }

    def doStuff = {
        return [10,20,30]
    } 

}
