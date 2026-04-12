@echo off
mvn clean package
start cmd /k java -jar target\hw-0.0.1-SNAPSHOT.jar --server.port=8084