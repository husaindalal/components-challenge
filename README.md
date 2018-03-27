#Running application
gradle bootRun


uses in-memory h2 database


#Application API docs 
http://localhost:8080/swagger-ui.html#/component-controller/getComponentsUsingGET

#Application health


#Code Structure
Component dto - Primary object used for transfer of information
ComponentController - Creates Rest APIs. converts json <-> dto. 
ComponentService - Main logic resides here. Interacts with client and database
ComponentRepository - Data access object for DB operations
ComponentEntity - DB entity to be saved in in-memory database
DigitalOceanClient - Client to interact with external service and return dto back to service

