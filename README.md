# Railway Central
Railway Central orchestrates railways trains. It does not do any actual train processing other than delegating the trains to their appropriate stations. It keeps track of the statusses of trains and tasks, which can be altered using the API. The API can also be used to create new trains and tasks.

##Keycloak
Keycloak is used for authentication and authorization. The *docker* folder contains a docker-compose file that will run keycloak with some preset test users and a testclient: *docker-compose -f keycloak.yml*.
Authentication is already enabled, authorization has been prototyped in the past. It can be added by adding roles to users and stations in keycloak and reading them from central.
In order to add or alter entries in Keycloak, visit *http://localhost:9080*. The default username/password is admin/admin. 
* Under the Clients tab, Testclient provides an example configuration of a client. 
* Under the Roles tab, maastro provies an example of a role that can be added to the client. This can be used as a means of authorizing certain calls in central based on the client.
* Under the Users tab, testuser provides an example of a user that accesses central. This user has default password *admin* and can be used for authentication when visiting *http://localhost:8080/*

## Design remarks 
We're using a repository pattern for JPA relations: https://stackoverflow.com/questions/51763093/spring-boot-how-to-persist-one-to-many-relation-separately-using-repository
In order to preload some example entities, set the *ddl-auto* in the application.yml to *validate*, copy data.sql and schema.sql from the test/resources to the main/resources folder and remove the h2 database before starting.

## Building Central
Central is a Spring-boot application serving an Angular front-end for the user interface. The application can be built with *mvn clean package* or run with *mvn*. The build of the *ng build* is included in the maven build. In order to debug with the front-end included, add a *mvn package* step in the before launch configuration of your IDE, or run *ng build* before running the spring-boot project. The front-end is built directly into the target folders assets folder.