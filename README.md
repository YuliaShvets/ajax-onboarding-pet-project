# Parking REST API
Description of the task: Create API, that allows you to receive and transmit parking information: location, trade network, count of parking spots,
information about unavailable parking spots, duration of use parking spot, issued tickets at the entrance to the park, number of a vehicle.

1. Draw a UML diagram using classes, that will be used in the course work. Specify links between classes.
2. Implement classes. Use lombok to reduce the amount of code.
3. Implement rest services for all entities using Spring Boot. Also, you should implement GET/POST/PUT/DELETE operations for entities. GET/2 return entity with id=2,
   GET/ - return all entities.
4. Divide the code into controllers, services and data access layer.
5. Linking controllers, services and DAL should be done using dependency inversion.
6. Implement data storage and reading from .csv file. Important : each entity should be stored in separate file.
7. If file for entity doesn't exist, you should create it. Example of name : "parking-2022-06-17.csv".
8. Each file should contain headers(matching the names of the attributes of the designed classes) only in the first line.
9. When you start application, all entities should be read from the file and saved in the hash map.
   When reading data, you should search for all files for the entity that were created in the current month(for example, all files created in June if the program runs in June)
10. The course work code should be available as a Pull Request on GitHub.
11. The project must contain README.md with a description of the task and step-by-step instructions for starting the program(you can see them below).
12. The project must use maven to build the project.
13. The code should be checked using SpotBugs and CheckStyle.
14. The code should contain unit tests to check the logic of writing and searching for files on the file system.
15. Create a set (collection) of REST queries that test the performance of developed services.

# Instruction for running the application:
1. Download the project from GitHub by cloning it, or just downloading as .zip file.
2. Open Command Line and proceed to the project's path (ex: D:\\My Projects\\CourseWork - for Windows and cd Downloads/CourseWork for Mac OS).
3. Run "mvn spring-boot:run" command and wait till the application is fully loaded and started.
4. To shutdown the application, open the Command Line that runs the application, press "Ctrl + C", enter "Y" and press "Enter".
   The application should be shutdown normally, saving all the changes to appropriate files.