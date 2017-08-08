# Transfer file between client and Server Application

/**
 * Author: Thien Le
 * Github: http://github.com/thienle75
 * 
 */
## Require system

- Latest JDK
- Latest Maven


## Import Application
- Import file-transfer/parent project as Maven project.  It should import both server and client project as Maven as well. 

## Run Application
1) To build application, run "maven install". 
2) Copy server-0.0.1-SNAPSHOT.jar and client-0.0.1-SNAPSHOT.jar to desired location. 
3) To start the server run "java -cp server-0.0.1-SNAPSHOT.jar com.gihub.thienle75.file_transfer.server.APP"
4) to start the client run "java -cp client-0.0.1-SNAPSHOT.jar com.gihub.thienle75.file_transfer.client.APP <download url> <file path> [<download limit>]"
	* Source of download <download url>: http://localhost:8080/<file name>
	* Output file name <file path>: d:/downloads/<file name> 
	* (option) Max bytes to transfer in bytes [<download limit>]: 102400 


Note :
To make files available to the server for downloads, create D:/Downloads directory and place files in there
