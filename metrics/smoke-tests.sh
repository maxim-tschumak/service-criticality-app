#!/usr/bin/env bash

url="localhost:8002"

# list architectures
curl ${url}:8080/metrics

# create a new metric
curl -X POST -H "Content-Type: application/json" -d '{"name": "test-coverage", "architectureId": 1, "serviceId": 1, "value": 0.1}' ${url}/metrics/
curl -X POST -H "Content-Type: application/json" -d '{"name": "test-coverage", "architectureId": 1, "serviceId": 2, "value": 0.2}' ${url}/metrics/
curl -X POST -H "Content-Type: application/json" -d '{"name": "test-coverage", "architectureId": 1, "serviceId": 3, "value": 0.3}' ${url}/metrics/

# update metric value
curl -X PUT -H "Content-Type: application/json" -d '{"name": "test-coverage", "architectureId": 1, "serviceId": 1, "value": 0.8}' ${url}/metrics/1