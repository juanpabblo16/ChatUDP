# ChatUDP
This code sets up a simple chat application using UDP sockets. The server listens on port 5000 for incoming messages from clients, and the client sends messages to the server with the option to quit by entering 'q', and 'switch' to change the chat.

---

Note that in this version, when the user enters "switch", the chat mode is toggled between client and server, and a message is printed indicating the current chat mode. The continue statement is used to skip the sending of the message and the receive loop for the current iteration, allowing the user to immediately enter another command or message after switching chat mode.
