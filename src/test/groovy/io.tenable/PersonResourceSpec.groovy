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

class PersonResourceSpec extends Specification {

    private Logger logger = LoggerFactory.getLogger(this.class)

    @Autowired
    TestRestTemplate restTemplate

    String BASE_URL = "/persons"


    def "should create new user"() {
        given: "information about new person"
        io.husaind.dto.Component person = new Component()
        person.setFirstName("Antony")
        person.setLastName("Zipkin")

        when: "saving the person is called"
        def createdPerson = restTemplate.postForObject(BASE_URL, person, Component.class)

        then: "should return success result"
        logger.debug("Created Component" + createdPerson.id)

        createdPerson.id != null
        createdPerson.firstName == person.firstName
        createdPerson.lastName == person.lastName

        when: "find the person just created"
        io.husaind.dto.Component foundPerson = restTemplate.getForObject(BASE_URL + "/" + createdPerson.id, io.husaind.dto.Component.class)
        then:
        foundPerson.firstName == person.firstName
        foundPerson.lastName == person.lastName

        when: "Delete person"
        restTemplate.delete(BASE_URL + "/" + createdPerson.id)
        io.husaind.dto.Component foundAfterDeletePerson = restTemplate.getForObject(BASE_URL + "/" + createdPerson.id, Component.class)

        then:
        foundAfterDeletePerson == null || foundAfterDeletePerson.id == null

    }

}
