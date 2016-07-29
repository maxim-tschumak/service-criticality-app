# Service Criticality Framework
The goal of this framework is to find the most critical components and design issues within a distributed system.

The component (service) analysis is based on different metrics.
These represent different aspects of service criticality.
Criticality, in this case, is defined as probability of a failure and its impact on the whole system.
Components with high criticality are mission/business critical and deserve special treatment.
Another interpretation of high criticality is bad architectural design around the component, which has to be improved.

Service Criticality Tool visualizes dependency relationships within a distributed system.
It identifies possible design issues on architectural level.
And builds a service criticality ranking based on software and architecture metrics.
This tool can help software engineers and architects by providing a basis for white box architecture analysis.

## The Approach
A distinction is made between software and architecture metrics.

Software metrics are calculated externally and can be imported into the tool.
Reasonable software metrics are: Lines-of-Code, Test Coverage, Complexity, etc.

Architecture metrics are based on the dependency graph.
There are some metrics and properties from graph theory and network analysis.
All of them are calculated internally.

Along with external metrics, they get normalized and aggregated to build a ranking of the services in regard to their criticality.

## Metrics
These metrics are calculated by the application based on the dependency graph.
Description represents a possible metric's interpretation for the domain of distributed systems.

Metric          | Description
--------------- | -------------
Degree          | Number of direct communication partners of the given service
Out-Degree      | Number of direct dependencies of the given service
In-Degree       | Number of services which depend directly on the given one
Constraint      | Order of constraint of dependency relations
Cycles          | Circular dependencies
Katz-Centrality | Importance of the service as a dependency on the big scale (transitive dependency)
Effective Size  | Number of independent sub networks the service is connected to

## Components
The application consists of 3 services based on Spring Boot.
PostgreSQL RDBMS is used to persist the data.
The front end application is built using React and D3.

### Architectures
Architecture service is responsible for architecture management.
Via the RESTful API, you can do CRUD operations on architecture stacks, services and dependencies between the service.
Information from this service is taken to create dependency graphs, which are the basis for the analysis.

### Metrics
Metrics service manages internal and external metrics of the services.
External metrics can be imported via the API.
Internal metrics are calculated automatically based on dependency graphs.
Both types of metrics get normalized and are accessible over the metrics API.

### Front-End
The results of the service criticality analysis are shown in the web application.
Here you find all the architecture stacks, visualizations of the dependency graphs, services, and metrics.

## Running the Application locally
In order to run the application on your local machine, you have to build the projects and run the whole stack using docker-compose.

### Prerequisites
* Java 8 JDK
* Maven 3.1.0+
* Docker 1.11+
* Docker Compose 1.7.1+

### Build
First, you have to build the components. Therefore run:
```bash
mvn clean package
```

### Start the whole stack
*docker-compose* will create 4 Docker containers, link them, bootstrap a database with sample data and start the components:
```bash
docker-compose up --build
``` 
Now, the web application is accessible on <http://localhost:8080>.
Architectures and Metrics API can be used under <http://localhost:8001/architectures> and <http://localhost:8002/metrics> respectively.

If your docker host is not your localhost (e.g. MacOS), replace localhost in the link with the docker machine IP address:
```bash
docker-machine ip ${machine_name}
```

## Usage
First, we create a new architecture (meta information about a distributed system):
```bash
curl -X POST
     -H "Content-Type: application/json"
     -d ’{
            "name" : "service-criticality-framework",
            "description" : "Framework for Service Criticality Calculations"
         }’
     ${base_url}/architectures
```
And add some components to the architecture:
```bash
curl -X POST
     -H "Content-Type: application/json"
     -d '{
           "name": "metrics",
           "description": "Metrics Service"
         }'
     ${base_url}/architectures/${architecture_id}/services
```
```bash
curl -X POST
     -H "Content-Type: application/json"
     -d '{
           "name": "arch-stacks",
           "description": "Architecture Stacks Service"
         }'
     ${base_url}/architectures/${architecture_id}/services
```
Next, we define dependency relationships between the services:
```bash
curl -X POST ${base_url}/architectures/${architecture_id}
        /services/${service_id}
        /dependencies/${depends_on_service_id}
```
We can check created architecture by calling the architectures endpoint:
```bash
curl -X GET ${base_url}/architectures/${architecture_id}
```

(Optional) we can add some metrics which were calculated externally:
```bash
curl -X POST
     -H "Content-Type: application/json"
     -d '{
           "name": "test-coverage",
           "architectureId": ${architecture_id},
           "serviceId": ${service_id},
           "value": 0.85
         }'
     ${base_url}/metrics/
```

Now, Service Criticality Application will calculate all the defined metrics.
The metric values will be normalized and aggregated afterwords.
Finally, dependency graph visualisation, metrics and service ranking is available in the web application.

## Evaluation of the Results
To compare the results of the Service Criticality App to facts (bug database or experts opinion), _evaluation-tool_ can be used.
This tool calculates correlations between the data sets.

## License
The MIT License (MIT)
Copyright © 2016 Maxim Tschumak, maxim.tschumak@gmail.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the “Software”), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.