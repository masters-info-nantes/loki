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
$ mvn compile exec:java # -Dexec.args="127.0.0.1" (optionnal)
```

## Versions
- Chat with fixed topic number
- Client dientification
- Dynamic topic number
- Removable topics
- Store discussion, new client get topic topic message historic
- Shared server



