# Getting Started

## Build the project

1. `mvn clean install` will run the maven build to compile the code and load required libraries.
2. `docker build -t test/my-app .` will build the docker image in this folder and push it to the local repository as `test/my-app`.

## Run the project

* Use Docker Desktop to launch it and specify port forwards.