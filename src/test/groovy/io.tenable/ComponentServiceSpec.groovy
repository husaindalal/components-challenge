package io.tenable

import io.tenable.client.DigitalOceanClient
import io.tenable.client.dto.DOComponent
import io.tenable.dto.Component
import io.tenable.service.ComponentEntityService
import io.tenable.service.ComponentService
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = Application.class)

class ComponentServiceSpec extends Specification {

    private DigitalOceanClient mockDigitalOceanClient

    private ComponentEntityService mockComponentEntityService

    private ComponentService componentService


    public void setup() {
        mockDigitalOceanClient = Mock(DigitalOceanClient)
        mockComponentEntityService = Mock(ComponentEntityService)
        componentService = new ComponentService(mockComponentEntityService, mockDigitalOceanClient)
    }


    def "Component service happy path unit test"() {
        given: "init mock results"

        mockDigitalOceanClient.getDoComponents() >> [new DOComponent(name: "abc", status: "blah", groupId: "foo", pageId: "bar"),
                                                     new DOComponent(name: "pqr", status: "operational", groupId: "fooz", pageId: "bar"),
                                                     new DOComponent(name: "lmn", status: "blah", groupId: "", pageId: "bar"),
                                                     new DOComponent(name: "xyz", status: "operational", groupId: "fooy", pageId: "bar")]

        when: "get All"
        def allComponents = componentService.getComponents(null)

        then: ""
        allComponents.size() == 2
        allComponents*.name.sort() == ["pqr", "xyz"].sort()
        allComponents*.compositeId.sort() == ["barfooz", "barfooy"].sort()

        1 * mockComponentEntityService.save(_ as List<Component>)


        when: "get Single"
        def findComponents = componentService.getComponents(["pqr"].toSet())

        then: "finds 1"
        findComponents.size() == 1
        findComponents*.name.sort() == ["pqr"].sort()
        findComponents*.compositeId.sort() == ["barfooz"].sort()

        1 * mockComponentEntityService.save(_ as List<Component>)


    }
}
