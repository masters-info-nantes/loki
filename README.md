# Loki

##Compile & run

### Install common part

```
$ cd common
$ mvn install
```

### Run server

```
$ cd server
$ mvn compile exec:java
```

### Run client

```
$ cd client
$ mvn compile exec:java # -Dexec.args="127.0.0.1" (optionnal)
```

## How to use it

- join a topic by double click on its name
- delete a topic by right-click on its name
