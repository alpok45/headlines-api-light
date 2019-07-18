 = = = = = = = = = = = = = = = = = = = = = = = =

There are two data files in resources.
headlines-api-light/src/main/resources/employees.txt
headlines-api-light/src/main/resources/headlines.txt
They are permanently attached to the application and should be even a part of resulted jar.

headlines.txt file contains 555 records
It has comma-separated values: [id], [publication-date], [title], [language], [department] 

  [id] - from ‘1’ to ‘555’
  [publication-date] – randomly from ('2019-05-20','2019-05-21','2019-05-22', '2019-05-23', '2019-05-24', '2019-05-25') 
  [title] – from ‘NewsTitle1’ to ‘NewsTitle555’
  [language] – randomly from ('ENG','GEM','RUS','SPA','JPN','FRA')
  [department] – randomly from ('IT','FINANCE','SALES', 'PR', 'HR')

employees.txt file contains 5000 records
It has comma-separated values: [id], [name], [location], [job-role], [department]

  [id] - from ‘1’ to ‘500’
  [name] – from ‘Name1’ to ‘Name5000’
  [location] – randomly from ('US','GB','AT','BR','JP','FR')
  [job-role] – randomly from ('employees', 'consultant', 'business partner')
  [department] – randomly from ('IT','FINANCE','SALES', 'PR', 'HR')


There is simple relation from news to employee.
I assume that news is directly related to employee by department.

The following classes provided to test main functionality of this application.

com.alpok.news.endpoints.HeadlineEmployeesControllerTest
com.alpok.news.endpoints.HeadlinesControllerTest
com.alpok.news.service.EmployeeServiceImplTest
com.alpok.news.service.HeadlinesServiceImplTest

 = = = = = = = = = = = = = = = = = = = = = = = =

Here are ways how to build and run:

There are three ways how to run application 
as a docker container, in IDE or as standalone java application.

1.	To run as a docker container (it requires the docker already runs on your environment).
- Build and deploy container image.
Based on what you have Maven or Gradle could be used.
In the root of the project run:
for Maven      ./mvnw install dockerfile:build
for Gradle      ./gradlew build docker

Then start the container like:
docker run -p 8080:8080 -t springio/headlines-api-light

If 8080 port is used you can change it to any other.
For example you what to use 1090, then execute like:
docker run -p 1090:8080 -t springio/headlines-api-light

2.	To run it in IDE ( I used Eclipse )
- import project from GIT (clone URI provided in the beginning of this document)
- navigate to headlines-api-light/src/main/java/com/alpok/news/Application.java
and run it as Java application.

In case the default port is in use it can be changed in 
headlines-api-light/src/main/resources/application.yml
The value port: 8080 could be changed to any appropriate.

3.	To run as stand-alone Java application.
Build with either Maven or Gradle
- build with Maven from the root of project execute:
mvn clean install
and run like:
java -jar target/headlines-api-light-0.1.0.jar 

- build with Gradle from the root of project execute:
gradle build
and run like
java -jar build/libs/headlines-api-light-0.1.0.jar

In case the default port is in use it can be changed in 
headlines-api-light/src/main/resources/application.yml
The port: 8080 could be changed to any appropriate, than application should be rebuilt restarted.

 = = = = = = = = = = = = = = = = = = = = = = = =

Here are how to use examples:

In our application we have headline news only for following dates
('2019-05-20','2019-05-21','2019-05-22', '2019-05-23', '2019-05-24', '2019-05-25') ,
So other dates will return empty results

http://localhost:8080/headlines?date=2019-05-24
http://localhost:8080/headlines?date=2019-05-24&includeEmployees=true

will return list of news headlines with list of employees as subscribers for each news headline
in following format 

Content-Type	application/json;charset=UTF-8

[
    {
        "id": "18",
        "title": "NewsTitle18",
        "abstractStr": null,
        "language": "ENG",
        "publicationDate": "2019-05-24",
        "author": null,
        "department": "FINANCE",
        "subscribersUrlPath": "/headline/department/FINANCE/employees",
        "subscribers": [
            {
                "id": "13",
                "name": "Name13",
                "location": "GB",
                "jobRole": "business partner",
                "department": "FINANCE"
            }
        ]
    }
]



Since many headlines could be related to same department it is too expensive to add subscribers to each headline. There will be option to not include subscribed employees to headline data.
Instead there will be provided path "subscribersUrlPath" to API returned all employees by department .

http://localhost:8080/headlines?date=2019-05-23&includeEmployees=false

will return list of news headlines without list of employees as subscribers for each news headline
in following format 

Content-Type	application/json;charset=UTF-8

[
    {
        "id": "4",
        "title": "NewsTitle4",
        "abstractStr": null,
        "language": "ENG",
        "publicationDate": "2019-05-23",
        "author": null,
        "department": "HR",
        "subscribersUrlPath": "/headline/department/HR/employees",
        "subscribers": []
    }
]


http://localhost:8080/headline/department/HR/employees

will return list of of employees for requested department
in following format 

Content-Type	application/json;charset=UTF-8

	

[
    {
        "id": "6",
        "name": "Name6",
        "location": "FR",
        "jobRole": "consultant",
        "department": "HR"
    }
]

