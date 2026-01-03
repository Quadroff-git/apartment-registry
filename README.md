# ApartmentRegistry
A simple Java console application developed
as a study project and to practice the following:

- JDBC usage (Postgres used as DBMS)
- proper application architecture
- basic git usage

The application also has a basic REST API built using Servlet API with Jackson Jr. used for automatic JSON de- and serialization. An outdated version of embedded Tomcat is used instead of war deployment.

REST API mode can be started from web/Server class. Can't be bothered to configure proper building and deployment. Change mainClass in build.gradle.kts to webServer and it might just work. 