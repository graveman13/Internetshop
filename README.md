#Internet Shop
![shop](/images/online-shopping.jpg)

# Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)

# <a name="purpose"></a>Project purpose
The project is an online store model.
The functional model represents:
* User
  >A new user is created during registration.
   Existing users are granted access after authorization.
* Item
  >Is a product. The ability to create is available only to User.Role="Admin"".
* Bucket
  > Shopping cart.
* Order
  >Ordered goods.
* Role 
  >Project has  two role "Admin" , "User".
  
  ![shop](/images/Чертеж1-Model.jpg | width=100)
  
# <a name="structure"></a>Project Structure
* Java 11
* Maven 4.0.0
* javax.servlet-api 3.1.0
* jstl 1.2
* log4j 1.2.17
* maven-checkstyle-plugin
* mysql-connector-java 8.0.18
<hr>

# <a name="developer-start"></a>For developer
Open the project in your IDE.

Add it as maven project.

Configure Tomcat:
* add artifact
* add sdk 11.0.3

 Control, receipt and storage of information are carried out in 2 versions:
 * use of the "MySQL database";
 * application "ArrayList".
 
  DAO is implemented as a level of abstract access to information.
  You can  changes implementation at internet-shop/src/main/java/mate.academy.internetshop/factory/Factory.class.
  You need to change classes "...DaoJdbcImpl"(use sql) to "...DaoImpl"(use List<>). For example:
  
 ```
 public static BucketDao getBucketDao() {
           if (bucketDao == null) {
               bucketDao = new BucketDaoJdbcImpl(connection);
           }
           return bucketDao;
       }
 ```
change to:
```
> public static BucketDao getBucketDao() {
         if (bucketDao == null) {
             bucketDao = new BucketDaoImpl();
         }
         return bucketDao;
     }
```

if you use "mySQL" open file internet-shop/src/main/java/mate.academy.internetshop/resources/init_db.sql to create schema and all the tables.

You need to create a directory "log" and specify the path to it internet-shop/src/main/java/mate.academy.internetshop/resources/log4j.properties.

Run the project.

If you first time launch this project:
You need to run InjectDataController at URL =.../internet_shop_war_exploded/inject to create default users.
By default, there is one user with the
 * USER role (login = "user", password = "1") 
 * ADMIN role (login = "user", password = "1").
<hr>

