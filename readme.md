1. This project is prepared by Group A:
	- B031810194	TAN ZHI ZHONG	S1G2
	- B031810223	CHON YAO JUN	S1G2
	- B031810144	TIANG KING JECK	S1G2

2. Import Schema Into MySQL Workbench
	i. Unzip the "ProjectBITP3123_Group A.zip" that we had submitted in Google Classroom.
	ii. Open MySQL Workbench and click on Local instance:3306 to enter the workspace.
	iii. Click Server on the main tool bar.
	iv. Select "Data Import".
	v. At "Import from Dump Project Folder", browse for folder "librarydb" ("librarydb" is located in "ProjectBITP3123_Group A") 
	vi. At "Default Target Schema" click "New..." to create a new schema and name it as "librarydb".
	vii. Click "Start Import" button at the bottom to import the schema.
	viii. Refresh the workspace and the "librarydb" will appear at the "Schemas" in "Navigator".

3. Project Setup
	i. Open Eclipse and create a new Dynamic Project name as "LibraryManagementSystem".
	ii. Open "ProjectBITP3123_Group A" > "LibraryManagementSystem"
	iii. Drag/import the "src" and "image" into the project.
	iv. Right click the project and click "Build Path" > "Configure Build Path...".
	v. Click "Add External JARs..." then browse to "ProjectBITP3123_Group A" > "LibraryManagementSystem" > "external jars".
	vi. Select "mysql-connector-java-8.0.20.jar" and "swingx-all-1.6.4.jar" then click "Open".
	vii. Click "Apply and Close".

3. Database Connection Setup
	i. Open "Connector.java" in the package "controller.manager.connector".
	ii. The connection string, database, username and password in "Connector.java" must be the same as declared in MySQL Workbench connection. Update the necessary data in "Connector.java" to ensure it match the connection information.
	iii. Save "Connector.java".

4. Run the Program
	i. Since our program are using created registry instead of local registry, so no need to loading the RMI registry at the terminal or MS DOS prompt.
	ii. "LibraryServerRMIApp.java" must be execute first.
	iii. Then execute "LibraryClientRMIApp.java".
	iv. The server and client sides UI will shown.
	v. Admin password is needed to terminate these program after "X" of the frame is clicked (PASSWORD = "abc123").