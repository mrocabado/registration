# Registration

# Build
Run this @ root folder
mvn clean package
Will generate registration/registration-rest/target/registration.war that you can deploy in a servlet container. I've tested with Tomat 8

See it here in action here: 
https://www.screencast.com/t/Zpi8dFUbKJ
# Frameworks
Jersey 2.x was used to develop the REST endpoints
Junit 4. was used to develop core layer unit test
Swagger was used to generate the REST documentation and testing pages


# Design considerations
Did my best to align the code to these design considerations
### Dependency Inversion 
Abstract, business rules level module like *registration-core* does not have source dependencies on implementations modules *registration-db*, *registration-rest*. I mean, no name defined in an implemetation modules ever appears in *registration-core*.

This creates a limit in the system that protects more asbtract module from implementations modules. For example, We could change the way we handle persistence and *registration-core* does not have to be recompiled.

See here: https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html
### Don't use nulls
Do not accept or return null from methods. Do not set variables to null.

See here: https://www.cio.com/article/2433223/net/the-a-z-of-programming-languages--c-.html
"50% of the bugs that people run into today, coding with C# in our platform, and the same is true of Java for that matter, are probably null reference exceptions"

### Unit test
Most of registration-core code is covered by unit test.

Most of registration-core code is covered by unit test.
### Clean code
- Try to keep methods short, simple and in the same asbtraction level. I don't think there is a method that fills an entire screen.
- Choose descriptive names for indentifiers.

### Avoid Guards proliferation and conditional code
- Avoid using primitive types. For example, instead of lots of guards checking for empty/null string, create a data type like NonEmptyString that once created will never contain an empty string and use it instead of String.
- Try to use funtional like constructs instead. For example, Optional.ifPresent() instead of *if*. Other than the Null checks guards, I don't think there are other conditional structures in the code.

### Use patterns when required
-  Factory method user inteod or open accedd to constructors.
-  Command patter is used to model application UseCases

See: https://jlordiales.me/2012/12/26/static-factory-methods-vs-traditional-constructors/
# Known issues

- Filtering by object properties should be moved to db layer. Seems like a better place fro that than rest layer.
- Persistence was mocked using java Map. 
- I did not have time to fully test persistence or rest layer. But those seems to be working fine. 
