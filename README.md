# Java-Chat-Server
To work on an understanding of processes and threads. To get more comfortable with OOP


## Process
An independent program with its own memory space. Processes don't normally share memory they communicate through sockets, files, IPC, etc.

## Thread
A lightweight unit of execution within a process. Multiple threads in the same process share memory but run independently.

When we run the Chat Server program with Java, this is one process. For this project, each connected client will be handled by a `thread` within the `process`.

## OOP Applied
#### Encapsulation
#### Abstraction
#### Inheritance/Polymorphism