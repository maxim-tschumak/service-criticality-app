#!/usr/bin/env bash

url="localhost:8001"

# list architectures
curl localhost:8080/architectures

# create an empty architecture stack
curl -X POST -H "Content-Type: application/json" -d '{"name": "arch-1", "description": "awesome architecture"}' ${url}/architectures

# create service-a and service-b within the architecture
curl -X POST -H "Content-Type: application/json" -d '{"name": "service-a", "description": "Service A"}' ${url}/architectures/1/services
curl -X POST -H "Content-Type: application/json" -d '{"name": "service-b", "description": "Service B"}' ${url}/architectures/1/services
curl -X POST -H "Content-Type: application/json" -d '{"name": "service-c", "description": "Service C"}' ${url}/architectures/1/services

# create dependency between service-a and service-b
curl -X POST -H "Content-Type: application/json" ${url}/architectures/1/services/1/dependencies/2

# delete dependency
curl -X DELETE ${url}/architectures/1/services/1/dependencies/2

# get created architecture
curl ${url}/architectures/1