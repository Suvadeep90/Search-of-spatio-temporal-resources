This is the course project for CS 581 Spring 2016 at UIC
Steps to be followed to run the project
Required downloads:
1. SQL Server 2012
2. Apache Tomact Server (8.0)

The Folder => ReadyBuilds contains the Team3_SFPark.bak and CS581_SFPark_Team3_2016.war.

Steps to configure SQL Sever 2012:

a.	Install Microsoft SQL Server 2012 with default configurations.  

b. Copy the database file (Team3_SFPark.bak) of the project your account folder which is located in the C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\Backup\ 

c.	Connect to the database in the SQL server using web authentication mode. 

d.	Expand the project and right click the Databases and select restore Database option. 

e.	In the General tab, select Device option and then select the “…” option on right side.

f.	A dialog box appears, click on Add button. 

g.	Traverse to the path mentioned above and select “Team3_SFPark.bak” 

h.	Click OK for all subsequent dialog boxes 

i.	Disconnect the database and reconnect using web authentication or restart the database connect. 

j.	Expand the project and then “Security” and right click on “Logins” and select “New Login…”

k.	Select “SQL Server authentication” radio button and write name: “ridhi” and password: “ridhi129” and check all boxes as depicted in below diagram and then click on “OK”

l.	Refresh security and then right click on the user “ridhi” and give the admin rights to this user. 

m.	Right Click on the database and set authentication mode to both windows as well as SQL Server. 

n.	Restart the database engine.

o.	Database is now setup.

Steps for running the application

1.	Download tomcat 8 server from the following link  https://tomcat.apache.org/download- 80.cgi 
2.	Copy and paste the zip file 
3.	Unzip the zip file at some location. Let us call your folder “<tomcat8>” 
4.	Under your <tomcat8>/webapps copy the CS581_SFPark_Team3_2016.war 
5.	If server is running stop the server by running the shutdown.bat file under <tomcat8>/bin folder 
6.	Then start the server by running startup.bat Under <tomcat8>/bin folder 
7.	Access the application using http://localhost:8080/CS581_SFPark_Team3_2016	
8.	For accessing the deterministic algorithm use the link http://localhost:8080/ CS581_SFPark_Team3_2016/ 
9.	For accessing the probabilistic and baseline algorithm use the link http://localhost:8080/ CS581_SFPark_Team3_2016/Probabilistic.jsp



