@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk-17.0.18"
set "PATH=%JAVA_HOME%\bin;%PATH%"
echo Starting Smart Ward Management System...
echo JAVA_HOME=%JAVA_HOME%
java -version
mvn spring-boot:run
