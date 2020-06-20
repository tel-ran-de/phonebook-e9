# phonebook-e9
The project of 9 evening group
Hi, we have developed an address book for you. Our application uses technologies such as Java 11, Angular Cli 9 inside Gradle, Springboot, in conjunction with the Docker database PostgresDB. Our entire application can be deployed on a local machine. Install Docker on Ubuntu:
$ sudo apt-get update
$ sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
    
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
$ sudo apt-key fingerprint 0EBFCD88
$ sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs)stable"
   
$ sudo apt-get update
 
$ sudo apt-get install docker-ce docker-ce-cli containerd.io
 After that you should open the application in the IntelliJ IDEA of how to project a gradle. Further, if everything went well, after the start of the project, go to the program terminal and execute the following command there:

$ sudo docker run --publish 5442:5432 --name postgres -v db:/var/lib/postgresql/data -e POSTGRES_PASSWORD=111111222 -e POSTGRES_DB=phonebook -d postgres

Then an admin panel is created in the database, with a username and password. Тhe gradle panel appears in the idea on the side, in it we follow the path phonebook-e/Task /build/ and run buildUI to load the NodeJs necessary for the project and install the necessary dependencies, then press shift + F10
Then we go to the browser and on the local host with port 8080 (http://localhost:8080/) we see our application
