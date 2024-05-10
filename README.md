# General idea

The general idea is to build a library system that archives all owned books, the affiliation and/or who has borrowed a book.

# Install/run the backend

The build process is handle by `maven`. From within the projects root directory, run:

```
mvn -f backend/db_conn/pom.xml compile
```

To run without re-compiling, run:

```
mvn -f backend/db_conn/pom.xml spring-boot:run
```

# Development plan

- [X] Conceptualize the basics
- [X] Set up the basic framework of the DB
- [X] Sketch out the spring-boot application
- [ ] Javadoc for the API
- [ ] Configure it such that the database gets initialized automatically (create user etc)
- [ ] Set up a Raspi for network access and make it secure
- [ ] Write the app -> kotlin using Jetpack Compose
- [ ] Think about deployment: Automatic deployment and build via git vs Docker
