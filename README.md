Recipe Service
==============================

This project allows you to create, update, remove and fetch recipes.

Feel free to choose your recipe!!!

**Entity** 
 
Recipe ( recipeId(Long), name(String) , isVeg(Boolean) , instructions(List), ingredients(list)), numberOfServings(Integer)

**API's available**
1. Get Recipes (Filters possible with isVeg, instructions, ingredients, number of servings)

``
GET /recipes?isVeg={isVeg}&instructions={instructions}&ingredients={ingredients}&numberOfServings={numberOfServings}
``

2. Add Recipe

``
POST /recipes
``

3. Update Recipe

``
PUT /recipes/{recipeId}
``

4. Remove Recipe

``
DELETE /recipes/{recipeId}
``

**Technologies Used** 

1. Framework : Spring Boot
2. Database : H2/Postgres
3. Maven
4. Auto-Value for code generation (Only for a single POJO just for showcasing code generation)
5. Swagger for documentation

**Build/Execute**

Execute the below maven command to generate a jar in the target directory of recipe-service module.

``
mvn clean package
``

Then you can simply execute/run the service using the below command from the target directory:

``
java -jar recipe-service-1.0-SNAPSHOT.jar
``

Alternatively you can always import this maven project in your favourite IDE and execute it.

You can use the below environment variable to choose a profile:

``
spring.profiles.active=dev
``

Also when you suppose have multiple enviroments and properties file you can select the properties file using:

``
--spring.config.name=prod
``

**NOTE**

I have used Auto-Value for a single POJO (Recipes). A common issue is it not getting resolved in IDE. Mark the 
target/generated-sources/annotations directory as Generated Sources Root. It should solve the problem, if not then 
just go for the jar option as of now. ( Also it can be replaced with a simple pojo :) ) 

Once the application is started you can test it here : 

``
http://localhost:8080/swagger-ui/index.html
``

**TIP**

You can use the valid data mentioned in the json files from src/test/resources to test the api's.

**Unit/Integration tests**

Both Unit and Integration tests has been written (as much possible in the given time :) )
Execute it separately using IDE or by maven commands.

TODO/Probable Improvements:
============================

1. Authentication and Aspects has not been added as of now due to time constraints. It can be extended easily later.

2. Custom Exceptions and Mappers has been added to cover the boundary conditions as much possible. May be some more exhaustive 
scenarios could have been covered.





