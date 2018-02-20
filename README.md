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

You need to have both Tomcat and MySQL up and running to use this
application. To setup the application you'll need to customize an
`application.properties` file.

```bash
$ cp src/main/resources/example.properties src/main/resources/application.properties
```

Now you're ready to run the command line application in the class
`com.codeup.movies.setup.MoviesSetupApplication`. It will
create and seed the database.

```bash
$ mvn compile
$ java -cp "$(mvn -q exec:exec -Dexec.executable=echo -Dexec.args='%classpath')" com.codeup.movies.setup.MoviesSetupApplication
```

To run the application with Tomcat, create a `context.xml` file for Tomcat
with the same database information.

```bash
$ cp src/main/webapp/META-INF/context.dist.xml src/main/webapp/META-INF/context.xml
```

## Tests

The tests use a different MySQL database, so you'll need a different `properties`
file.

```bash
$ cp src/test/resources/example.properties src/test/resources/tests.properties
```

To run the tests execute.

```bash
$ mvn test
```
