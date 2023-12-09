#!/bin/bash

# List of images to push
images=("inventory-service:1.0-SNAPSHOT"
        "order-service:1.0-SNAPSHOT"
        "notification-service:1.0-SNAPSHOT"
        "product-service:1.0-SNAPSHOT"
        "discovery-server:1.0-SNAPSHOT"
        "ghergutmadalin/api-gateway:latest"
        "api-gateway:1.0-SNAPSHOT")

# Docker Hub username
username="ghergutmadalin"

# Loop through the images and push each one
for image in "${images[@]}"; do
  docker tag $image $username/$image
  docker push $username/$image
done
