# Railway Central
Railway Central orchestrates railways trains, which are docker containers created by researchers. It does not do any actual train processing other than delegating the trains to their appropriate stations (the client software). It keeps track of the statusses of trains and tasks (each train consists of one or more tasks per station. These represent an actual run of the software on the docker container), which can be altered using the API. The API can also be used to create new trains and tasks.

## Running a first example
In order to run a first example, run Keycloak and add the appropriate users and clients (see below). Then start central by running *mvn*. Next, clone the station repository and follow the instructions to build and run two separate station applications. Once all three applications are running, edit and run *add_example_entities.sh*. This shell script will perform API calls to central to add the stations, trains and tasks. Afterwards the station applications will pick up the tasks and start processing.

## Keycloak
Keycloak is used for authentication and authorization. The *src/main/docker* folder contains a docker-compose file that will run keycloak with some preset test users and a testclient. In the *src/main/docker* folder, run: *docker-compose -f railway.yml*.
Authentication is already enabled, authorization has been prototyped in the past. It can be added by adding roles to users and stations in keycloak and reading them from central.
In order to add or alter entries in Keycloak, visit *http://localhost:9080*. The default username/password is admin/admin. 
* Under the Clients tab, Testclient provides an example configuration of a client. 
  * Go to *Administration Console* 
  * Click on *Clients*
  * At the top right of the clients table, click on *Create*
  * Fill in the desired client id. This id will need to be matched in the stations *application.yml*
  * In the resulting window, set *Access Type* to *confidential*
  * Enable *Service Accounts Enabled* and *Authorization Enabled*
  * Set valid Redirect URI's to *
   
* Under the Roles tab, maastro provies an example of a role that can be added to the client. This can be used as a means of authorizing certain calls in central based on the client.
* Under the Users tab, testuser provides an example of a user that accesses central. This user has default password *admin* and can be used for authentication when visiting *http://localhost:8080/*

## Swagger UI
Swagger is currently running at https://dcra.railway.medicaldataworks.nl/swagger-ui.html. This can be used as API documentation and an easy way to access the API (Be sure to login using the same browser first).

## Design remarks 
We're using a repository pattern for JPA relations: https://stackoverflow.com/questions/51763093/spring-boot-how-to-persist-one-to-many-relation-separately-using-repository
In order to preload some example entities, set the *ddl-auto* in the application.yml to *validate*, copy data.sql and schema.sql from the test/resources to the main/resources folder and remove the h2 database before starting.

## Building Central
Central is a Spring-boot application serving an Angular front-end for the user interface. The application can be built with *mvn clean package* or run with *mvn*. The build of the *ng build* is included in the maven build. In order to debug with the front-end included, add a *mvn package* step in the before launch configuration of your IDE, or run *ng build* before running the spring-boot project. The front-end is built directly into the target folders assets folder.