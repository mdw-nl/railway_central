We're using a repository pattern for JPA relations: https://stackoverflow.com/questions/51763093/spring-boot-how-to-persist-one-to-many-relation-separately-using-repository
In order to preload some example entities, copy data.sql and schema.sql from the test/resources to the main/resources folder and remove the h2 database before starting.

The Angular front-end is built seperately. In order to debug with the front-end included, add a mvn package step in the before launch configuration of your IDE. 