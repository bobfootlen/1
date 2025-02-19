#!/bin/bash

# Define container name
CONTAINER_NAME="ndd-tutorial"

# Step 1: Build the JAR file
echo "Building the Spring Boot application..."
mvn clean install || { echo "Maven build failed! Exiting."; exit 1; }

# Step 2: Build the Docker image
echo "Building the Docker image..."
docker build -t ndd/tutorial . || { echo "Docker build failed! Exiting."; exit 1; }

# Step 3: Stop and remove the existing container if running
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Stopping the existing container..."
    docker stop $CONTAINER_NAME
fi

if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
    echo "Removing the existing container..."
    docker rm $CONTAINER_NAME
fi

# Step 4: Run the new container
echo "Starting a new container from the latest image..."
docker run -d --name $CONTAINER_NAME -p 8080:8080 ndd/tutorial

echo "Deployment complete. Application running on port 8080."
