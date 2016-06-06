#!/bin/bash
javac *.java
jar -cf diskBenchmark.jar *.class
java -cp diskBenchmark.jar DiskBenchmark
exit 0