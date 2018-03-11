Hi!

I used a Game Maker visualization. Its a Windows executable. You should run 
the Java program first (the server), then run the exe (the client). If need
be, <space> will restart the exe program. The source can be found at:
	https://github.com/tlacasse/CS451-Project2
particularly in
	/CS451-Project2.gmx/scripts/*.gml
The main ones relating to IPC are:
	client_create.gml
	client_receive.gml
	process_buffer.gml


visual.png shows what will be in the visualization, but I think it's simple to
understand.


The .zip is the Netbeans project.

Classes:

Involves Synchronization
	bookstore.Queue
	bookstore.Store
	bookstore.people.Cashier
	bookstore.people.Visitor
	
Involves IPC
	bookstore.server.Buffer
	bookstore.server.Code
	bookstore.server.Server

bookstore.Program is the main method


