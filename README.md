# Loki

##Compile & run

### Install common part

```
$ cd common
$ mvn install
```

### Run server

```
$ rmiregistry &
$ cd server
$ mvn compile exec:java
```

### Run client

```
$ cd client
$ mvn compile exec:java
```

## Todo
- Create classes and add pseudo-code from google drive doc
- Create graphic interface in client (JFrame, ..)
- Test RMI on a side project

## Versions
- Chat with fixed topic number
- Client dientification
- Dynamic topic number
- Removable topics
- Store discussion, new client get topic topic message historic
- Shared server



