# lv-181java
18.05.2016
To run tests make sure test/java folder is highlighted with green color like on this pic: http://devcolibri.com/cp/wp-content/uploads/2013/03/junit-testing.png

To do so go to Project Structure -> Sources, select "test/java" and mark it as "test" folder.
Run testcases by right-clicking on tab -> Run

---------------------------------


tomcat7 idea integration
------------------------
use [this](https://dzone.com/articles/headless-setup-java-project) link as a manual

pom.xml file is already configured!

on Runner tab set VM Options: 
```
-Xms128m -Xmx8192m -XX:PermSize=128m -XX:MaxPermSize=256m
```

"Hello, it's kids room" page will be available on http://localhost:8080/home/

` src/main/resources/hibernate.properties`:
```properties
jdbc.driverClassName = com.mysql.jdbc.Driver
jdbc.url = jdbc:mysql://localhost:3306/hibernatedb
jdbc.username = root
jdbc.password = password
hibernate.dialect = org.hibernate.dialect.MySQLDialect
hibernate.show_sql = true
hibernate.format_sql = true
hibernate.hbm2ddl.auto = create
hibernate.enable_lazy_load_no_trans = true
hibernate.search.default.directory_provider = ram
hibernate.search.default.indexBase = com.acme.hibernate.CustomDirectoryProvider
hibernate.search.indexing_strategy = event
```

|Login|Password|
|-----|--------|
|user@softserveinc.com|user|
|admin@softserveinc.com|admin|
|manager@softserveinc.com|manager|
