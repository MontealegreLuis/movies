# Java Web application

[![Build Status](https://travis-ci.org/MontealegreLuis/movies.svg?branch=master)](https://travis-ci.org/MontealegreLuis/movies)
[![codebeat badge](https://codebeat.co/badges/9a9920f0-dbf3-4424-a5dc-e7d429870bdc)](https://codebeat.co/projects/github-com-montealegreluis-movies)

## Movies catalog

This is a demo application using Servlets/JSPs and JDBC. It is a small 
catalog of movies with the following features for guest users.

* Filter movies by category.
* Review the details of a given movie.

Registered users have the ability to.

* Add more movies
* Rate movies

## Setup

To setup the application you'll need to customize a `config.properties` 
file.

```bash
$ cp config.dist.properties config.properties
```

Once you set your database credentials. Run the command line application
in the class `com.codeup.movies.setup.MoviesSetupApplication`. It will
create and seed the database.

You'll need to modify Tomcat's `context.xml` file using the following
configuration.

```xml
<Resource 
    name="jdbc/movies_db" 
    auth="Container" 
    type="javax.sql.DataSource" 
    driverClassName="com.mysql.cj.jdbc.Driver" 
    url="jdbc:mysql://localhost:3306/movies_db?useLegacyDatetimeCode=false&amp;serverTimezone=UTC"
    username="movies_user" 
    password="movies_password"
    maxActive="100" 
    maxIdle="20" 
    minIdle="5" 
    maxWait="10000"
/>
<ResourceLink 
    name="jdbc/movies_db"
    global="jdbc/movies_db"
    type="javax.sql.DataSource" 
/>
```

Use the same values you used in the properties file.

## Tests

To run the tests execute.

```bash
$ mvn test
```
