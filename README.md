# phonebook-e9
The project of 9 evening group
Hi, we have developed an address book for you. Our application uses technologies such as Java 11, Angular Cli 9 inside Gradle, Springboot, in conjunction with the Docker database PostgresDB. Our entire application can be deployed on a local machine.
# Install Docker on Ubuntu:
https://docs.docker.com/engine/install/ubuntu/
# install Docker on Windows:
https://docs.docker.com/toolbox/toolbox_install_windows/
# install Docker on MacOS
https://docs.docker.com/toolbox/toolbox_install_mac/

 After that you should open the application in the IntelliJ IDEA of how to project a gradle. Further, if everything went well, after the start of the project, go to the program terminal and execute the following command there:

$ sudo docker run --publish 5442:5432 --name postgres -v db:/var/lib/postgresql/data -e POSTGRES_PASSWORD=<password> -e POSTGRES_DB=phonebook -d postgres
(The setting --publish 5442:5432 is needed ONLY if a Postgres server is installed on the local machine)

Then an admin panel is created in the database, with a username and password. Тhe gradle panel appears in the idea on the side, in it we follow the path phonebook-e/Task /build/ and run buildUI to load the NodeJs necessary for the project and install the necessary dependencies, then press shift + F10
Then we go to the browser and on the local host with port 8080 (http://localhost:8080/) we see our application
