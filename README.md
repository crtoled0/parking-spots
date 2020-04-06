# parking-spots
Back-End Application to manage parking spots

## Prerequisites
``` 

Java JDK 9 or higher. 
MongoDB 4.0.10 or higher
Any Application Server that support java web applications. 
For the purpose of this project Apache Tomcat/9.0.33 was used.
Apache ANT

```

## Installation

```
Step 1: Let's download our project.

git clone https://github.com/wutzcrtoledo/parking-spots.git

Step 2: On downloaded folder among other files in root. 
We'll be able to see files build.properties and config.properties

build.properties 
appserver.home = D:\\WorkP\\programs\\apache-tomcat-9.0.33
appserver.lib = ${appserver.home}/lib

On this file we can see properties related to our application server installed. 
Change accordingly to your environment. 

config.properties
mongo.url=mongodb://localhost:27017
mongo.db=parking-db

On this file we config our mongo database connection. 
You can keep or change this file. 
In any case make sure the database named as in 
property "mongo.db" is created in your local MongoDB.

Step 3: Now let's build our project.

Open a terminal in root folder of our 
project and execute build.xml typing ANT.

Step 4: If everything went well. We should see a folder /dist. 
And inside we'll find parking-spots.war file. 
We now need to deploy this war file in your application server. 

At this point we should be ready to start testing out endpoints. 
For more details take a look to our API.


```


