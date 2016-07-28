# Service Criticality Framework
The goal of this framework is to find the most critical components within a distributed system. Those components are expected to cause problems with regards to maintainability.

The component (service) analysis is based on different metrics.
These represent different aspects of service criticality.

## The Approach
The system calculates all metrics based on the dependency graph.
But it is also possible to import externally calculated metrics (e.g. LoC, Test Coverage, Complexity).
The Framework will calculate some metrics from graph theory and network analysis.
Along with external metrics, they will be normalized and aggregated to build a ranking of the services in regard to their criticality.

## Metrics
These metrics are calculated by the application based on the dependency graph.
Description represents a possible metric's interpretation for the domain of distributed systems.

Metric          | Description
--------------- | -------------
Degree          | Number of direct communication partners of the given service
Out-Degree      | Number of direct dependencies of the given service
In-Degree       | Number of services wich depend directly on the given one
Constraint      | Order of constraint of dependency relations
Cycles          | Circular dependencies
Katz-Centrality | Importance of the service as a dependency on the big scale (transitive dependency)
Effective Size  | Number of independent subnetworks the service is connected to

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
First, you have to build *architectures*, *metrics* and *webapp* components. Therefore run:
```bash
cd [architectures|metrics|webapp] && mvn clean package
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

## Evaluation of the Results
To compare the results of the Service Criticality App and facts (bug database or experts opinion) _evaluation-tool_ can be used.
This tool calculates correlations between the data sets.
