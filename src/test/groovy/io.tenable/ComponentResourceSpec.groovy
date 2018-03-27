package io.tenable

import io.tenable.dto.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

class ComponentResourceSpec extends Specification {

    private Logger logger = LoggerFactory.getLogger(this.class)

    @Autowired
    TestRestTemplate restTemplate

    String BASE_URL = "/api/v1/components"


    def "Test: Get component with names param"() {

        when: "get for particular names is called"

        def getByNames = restTemplate.getForObject(BASE_URL + "?name=api,Support Center", Component[].class)

        then: "should return success result"
        logger.debug("found Components" + getByNames)

        getByNames.size() >= 2
        getByNames.findAll { it -> it.name in ["API", "Support Center"] }*.name.sort() == ["API", "Support Center"].sort()

        when: "find the data in DB"
        def getByNamesDB = restTemplate.getForObject(BASE_URL + "/database", Component[].class)
        then:
        getByNamesDB.size() >= 2
        getByNamesDB.findAll { it -> it.name in ["API", "Support Center"] }*.name.sort() == ["API", "Support Center"].sort()

    }


    def "Test: Get component with null param"() {

        when: "get for all is called"

        def getAll = restTemplate.getForObject(BASE_URL, Component[].class)

        then: "should return success result"
        logger.debug("found Components" + getAll)

        getAll.size() >= 20
        getAll.findAll { it -> it.name in ["API", "Support Center", "Block Storage"] }*.name.sort() == ["API", "Support Center", "Block Storage"].sort()

        when: "find the data in DB"
        def getByNamesDB = restTemplate.getForObject(BASE_URL + "/database", Component[].class)
        then:
        getByNamesDB.size() >= 20
        getByNamesDB.findAll { it -> it.name in ["API", "Support Center", "Block Storage"] }*.name.sort() == ["API", "Support Center", "Block Storage"].sort()

    }

}
