#Running application
gradle bootRun

#Application API docs 
http://localhost:8080/swagger-ui.html#/component-controller/getComponentsUsingGET

#Application health
http://localhost:8080/actuator/health

#Code Structure
Component dto - Primary object used for transfer of information
ComponentController - Creates Rest APIs. converts json <-> dto. 
ComponentService - Main logic resides here. Interacts with client and database
ComponentRepository - Data access object for DB operations
ComponentEntity - DB entity to be saved in in-memory database
ComponentEntityService - Service method to perform DB operations and mapping to dto
DigitalOceanClient - Client to interact with external service and return dto back to service

Integration test: ComponentResourceSpec
Unit test: ComponentServiceSpec