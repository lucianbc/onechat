# OneChat
Java chat app unsing network sockets and swing for the UI. You can log in anonymously or by using a nickname and chatin private or in a chat room shared by all the active users. 

This app was developed as a homework for the "Advanced OOP" course at the University of Bucharest.

## Features
* Users can log in with a nickname or anonymously
* Users can open multiple chat windows and chat in private or in the shared chat room
* The nicknames are saved on the client side, so that they can be reused

## Development
* The client is build in an MVC fashion and uses Swing as the UI toolkit
* An H2 database is used to store the nicknames on the client side
* The client connects to a socket exposed by the server
* The communication between the client and the server is done using a simple protocol based on the template `"TO: %s, PAYLOAD: %s;"`, where the `TO` part corresponds to a route and the `PAYLOAD` is serialized as JSON.
* The network endpoint maps all the known routes to actions that take as an argument the payload
* The server uses a MySql database to keep track of the users and the messages, so that the messages are not lost between restarts.
* The server needs an instance of MySql running on `localhost:3306`
* The server handles each client session on a different thread
* Everything is coded keeping in mind the SOLID principles
