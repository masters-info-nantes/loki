# Loki

## Compile & run

### Install common part

```
$ cd common
$ mvn install
```

### Run server

```
$ cd server
$ mvn compile exec:java -Dexec.args="database_path"
	# or
$ mvn compile exec:java -Dexec.args="database_path 127.0.0.1:server_port"
	# or
$ mvn compile exec:java -Dexec.args="database_path 127.0.0.1:server_port other_server_ip:other_server_port"
```

example :

```
$ mvn compile exec:java -Dexec.args="/tmp/storage1.db 127.0.0.1:9999"
```

### Run client

```
$ cd client
$ mvn compile exec:java
	# or
$ mvn compile exec:java -Dexec.args="server_ip:server_port"
```

## How to use it

- join a topic by double click on its name
- delete a topic by right-click on its name
