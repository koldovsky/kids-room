# lv-181java
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
```

` src/main/resources/messages_ua.properties`:
```
Login                  Password 
user@gmail.com         user
admin@gmail.com        admin
manager@gmail.com      manager
```
```properties
user.firstname = Ім'я
user.lastname = Прізвище
user.email = Поштова скринька
user.password = Пароль
user.confirm = Підтвердження паролю
user.phone = Номер телефону
user.register = Зареєструвати
user.prerules = Натиснувши "Зареєструвати" ви погоджуєтеся з
user.rules = правилами
user.registration = Реєстрація
user.login = Увійти
user.remember = Запам'ятати мене
user.logout = Вийти
```