package io.tenable

import io.tenable.dto.Component
import io.tenable.service.ComponentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = Application.class)

class PersonServiceSpec extends Specification {

    @Autowired
    ComponentService personService

    def "Person Create, Update, Get, Delete - Happy path "() {
        given: "init person"
        Component person = io.husaind.dto.Component.builder()
                .firstName("Test-first")
                .lastName("Test-last")
                .build()

        when: "Component created"
        def createdPerson = personService.save(person)
        def foundPerson = personService.findById(createdPerson.id).get()

        then: "verify names are saved and retrieved correctly"
        foundPerson.firstName == person.firstName
        foundPerson.lastName == person.lastName

        when: "delete"
        personService.delete(createdPerson.id)
        def foundPersonAfterDelete = personService.findById(createdPerson.id)

        then: "should not be found"
        !foundPersonAfterDelete.isPresent()

    }
}
