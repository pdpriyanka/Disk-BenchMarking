Instructions to execute program for Disk benchmark.
1	For this assignment I have used java 1.8 version
2	Open the terminal.  If you want to use amazon EC2 instances then connect to it from terminal.
3	For compiling and running java programs , you must have jdk installed .
4	Run  the following command to install jdk
	sudo yum install java-1.8.0-openjdk-devel

5	 From terminal go to the folder where source code for disk is saved.
6	 To run the bash script
	Give executable permission to it
	chmod 500 disk.sh
	
	Then execute following command to run the bash script
	./disk.sh  

	Or
	Without bash script you can type following commands
	javac *.java
	jar -cf diskBenchmark.jar *.class
	java -cp diskBenchmark.jar DiskBenchmark
	After executing  the above  commands or bash script program will start running and you can see the printed output on screen. 


For extra credit Iozone 
1	Download the Iozone for linux by executing following commands and go to iozone3_394/src/current folder

2	wget http://www.iozone.org/src/current/iozone3_394.tar
3	tar xvf iozone3_394.tar 
4	cd iozone3_394/src/current
5	make
6	make linux

execute the following command
./ionoze –a -r 1m –s 10m –T
