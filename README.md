# ONLINE SHOP

![Header Image](src/main/resources/register_page.png)

---

[Project purpose](#project-purpose)

[Project structure](#project-structure)

[Implementation details](#implementation-details)

[Launch guide](#launch-guide)

[Author](#author)

---
### Project purpose

_This web project represents a **simplified online** shop with basic features such as:_

- Registration, login and logout
- User **_password encryption_**
- User **_authentication and RBAC authorization_**
- Product management
- Adding products to shopping cart and order completion
- Order history and management
- User management

*Users with USER role are authorized to:*

    - view all products
    - add to and remove products from their shopping cart
    - view shopping cart and complete order
    - view all their orders

*Users with ADMIN role are authorized to:*

    - add to or delete products from the list of products available for order
    - view all users and their orders
    - delete user's order or the user himself

---
### Project structure

_The project uses **MVC architectural pattern**. Project structure is the following:_

- Models (entity classes)
- **_DAO_** layer, containing basic CRUD-operations for communication with the persistence layer
- Service layer, containing business-logic of the application
- **_Servlets_**, implementing client-server communication logic
- **_JavaServer Pages_**

---
### Implementation details

- **_Dependency Injection_** design pattern is used in the project - DAO and Service dependencies are injected during runtime.
- DAO layer has two implementations: 
    - inner-storage (List-based)
	- outer-storage (using **_JDBC_** connecting to **_MySQL RDBMS_**)
	
  To switch between the two implementations, you will need to place **_@Dao annotation_** before class declaration of the chosen implementation.
- User authentication and RBAC authorization are realized through **_filters_**.
- JSPs use **_JSTL, EL_** and **_Twitter Bootstrap_**.
- Logging is implemented via **_Log4j2 library_**.
- **_Maven Checkstyle Plugin_** is configured.

---
### Launch guide

_To run this project you will need to install:_

- <a href="https://www.oracle.com/java/technologies/javase-jdk11-downloads.html">JDK 11 or higher</a>
- <a href="https://maven.apache.org/download.cgi">Apache Maven</a>
- <a href="https://tomcat.apache.org/download-90.cgi">Apache Tomcat</a>
- <a href="https://dev.mysql.com/downloads/installer/">MySQL RDBMS</a>

_Here are the steps for you to follow:_

- Add this project to your IDE as **_Maven project_**.
- If necessary, configure Java SDK 11 in Project Structure settings.
- Add new Tomcat Server configuration and select **_war-exploded artifact_** to deploy. Set **_application context_** parameter to "/".
- Change **_path to your log file_** in _src/main/resources/log4j2.properties_ on line 4. You may also want to change the 'filePattern' parameter on line 17.
- If you decide to use the default JDBC-based DAO implementation:
	- Execute queries listed in _src/main/resources/init_db.sql_ in MySQL RDBMS in order to **_create the schema and all the tables required_**.
	- Enter your **_own username and password_** in _src/main/java/com/internet/shop/util/ConnectionUtil.java_ class on lines 8-9.
- Run the project via Tomcat configuration.


    First, you will need to register as a new user. By default, the USER role is assigned to all registered users. 
    After a successful login you will be able to inject an ADMIN user by pushing the corresponding button from the main page.
    The ADMIN user will have a default email - "admin@gmail.com" and password - "1".
    At this point, you may wish to login as the ADMIN user and add some products that will become available for order by USERs.
    
---
### Author

[Kseniia Makarova](https://github.com/KseniiaMakarova)