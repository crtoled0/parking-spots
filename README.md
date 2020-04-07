# parking-spots
Back-End Application to manage parking spots

## Prerequisites

- Java JDK 9 or higher.   
- MongoDB 4.0.10 or higher.  
- Any Application Server that support java web applications.  
  For the purpose of this project Apache Tomcat/9.0.33 was used.  
- Apache ANT.  

## Installation


- Step 1:  Download project.
```
git clone https://github.com/wutzcrtoledo/parking-spots.git
```  
- Step 2: On downloaded folder among other files in root.  
We'll be able to see files build.properties and config.properties.  
```
build.properties  
appserver.home = D:\\WorkP\\programs\\apache-tomcat-9.0.33
appserver.lib = ${appserver.home}/lib
```
- On this file we can see properties related to our application server installed. In our case is Tomcat. Change accordingly to your environment.  
```
config.properties
mongo.url=mongodb://localhost:27017
mongo.db=parking-db
```  
- On this file we config our mongo database connection. You can keep or change this file.
In any case make sure the database named as in 
property "mongo.db" is created in your local MongoDB.  

- Step 3: Build Project.  
Open a terminal in root folder of our project and execute build.xml. 
```
ANT
```  

- Step 4: If everything went well. We should see a folder /dist.
Inside we'll find parking-spots.war file.  
We now need to deploy this war file in your application server.  

At this point we should be ready to start testing out endpoints. 

## Requirements, Design and Architecture 

This small project has an initial design. You can take a look **[HERE](design/index.md#Design-Project-Parking-Spots)**  

## API 

Application Rest API **[HERE](api/index.md#API-Parking-Spots)**  

## Testing 

Data Populate and Test Plan **[HERE](TestPlan/README.md#JMeter-Test-Plan)**  

## Pending Enhancements and Observations.

Following we have a list of pending items to be enhance/fixed. 
Each item will be removed as they are addressed.  

- ~~**Proper Exception Distribution:**   
In current solution if there's an exception through a endpoint call 
life cicle. This is catched and returns error payload and status 500 (Internal Error).
The idea of this enhancement is to manage each exception with its respective 
standar status. For instance if endpoint doesn't exist. We should return error 404. 
In case of unauthorize access error 401 and so on.~~ 

- ~~**Add Security to formulas's format:**   
Currently there's a function that calculates price based on custom formulas that are saved as String.  
We need to make sure than that formula doesn't include malware code. Avoiding any type of code injection.~~    

- **Add Auth Security Components:**  
Currently this application is not handlering proper authentication for private endpoints. 
Right now private endpoints require Authorization header. However just sending any value 
would grant access to the endpoint. 
A proper Auth structure needs to be build. Where admin user authenticate and system returns 
a temporal session token (15 minutes duration), and use this token as part of private 
endpoint header. 

- **Pagination for large results:**   
On endpoint where no filter is included. The system is returning all results at once. For largest business where 
we can have thousands of parking spots. We need to add a proper bulk pagination response.

- **Secure MongoDB Connection:**   
Current solution use an open MongoDB connection. This should be protected by password authentication. 

- **Move Development Implementation to newer technologies:**  
Now we are compiling and packaging through ANT. Plus, dependencies are uploaded to the repository.
This is not suitable for new age projects, where there are tools to make this simpler. 