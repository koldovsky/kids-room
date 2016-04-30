# lv-181java
! tomcat7 idea integration !
use this link as a manual: https://dzone.com/articles/headless-setup-java-project
pom.xml file is already configured!

on Runner tab in set VM Options: -Xms128m -Xmx8192m -XX:PermSize=128m -XX:MaxPermSize=256m

"Hello, it's kids room" page will be available on http://localhost:8080/home/

src/main/resources/hibernate.properties:
//зразок!
jdbc.driverClassName = com.mysql.jdbc.Driver
jdbc.url = jdbc:mysql://localhost:3306/hibernatedb
jdbc.username = root
jdbc.password = password
hibernate.dialect = org.hibernate.dialect.MySQLDialect
hibernate.show_sql = true
hibernate.format_sql = true
hibernate.hbm2ddl.auto = create
