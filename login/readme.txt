Simple login application, not much effort spent on making frontend look good

Done using Spring Boot in Java, using thymeleaf for view layer

For sake of simplifying assessment, since it is not given in requirements, password is not encrypted and is done in plain text


Login, logout and authentication done using Spring Security

Welcome page shows name, username and role of the logged in user

Only manager role can see a link on welcome page that leads to a restricted page

-------------------------

if login is unsuccessful, will remain in login page with message "Invalid userid or password"

All data is stored in an embedded h2 db, db is located in ./src/main/resources/db
Schema definition can be found in ./src/main/resources/schema.sql
Data definition can be found in ./src/main/resources/data.sq

Implements simple model-view-controller from Spring

Also added a feature to switch between English and Simplified Chinese (Translation accuracy not guaranteed)

-----------------------------------------------------------------------------------------------------------------------------------------

Steps used to test:

1) run ./src/main/java/com/example/login/LoginApplication.java

2) open any browser to "localhost:8080"

3) click "Log In" button on landing page

4) enter any values into username and password (or leave blank) and click "Log In" button, should result in error message showing "Invalid userid or password"

5) enter "emp" as username and "password" as password and click "Log In", should see welcome page with name, username and role of the logged in user

6) Regular user only has "Log Out" button, click on that button, will be taken to landing page

7) Click "Login" button, now use "mgr" as username and "password" as password, click "Log In"

8) Once again should see welcome page with name, username and role of the logged in user, but now there is a link to a restricted page

9) Click on "Restricted Page", should be taken to a restricted page with 1 line of text on it and button "Return"

10) Click "Go Back" to return to welcome page, then "Log Out". End of testing
