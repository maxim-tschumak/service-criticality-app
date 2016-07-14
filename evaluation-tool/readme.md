# Evaluation Tool
Tool for evaluation of Service Criticality Application's results.

The calculated by the Service Criticality App results are compared to the 'facts'.
Facts can be either a bug database or expert opinions.
The Bug database need to contain the number of occurred problems per service in some time frame.
The Survey consists of experts' judgments of the services, in regard to their criticality.

This Tool calculates the correlation between the facts and results of Service Criticality Application.
It's done for aggregated overall results and for each metric separately.

Evaluation Tool can be used to verify or refute the concept.
Furthermore, we can find out which of the metrics performs better and weight metrics according to the correlation.

## Usage

### Build and Package
```bash
sbt clean package
```

### Run
```bash
java -jar target/scala-2.11/evaluation-tool-assembly-1.0.jar \
    --survey-results survey-results.csv \
    --framework-results framework-results.csv \
    --correlation-type spearman \
    --exclude ismt-tool,user-service,bi,buying-articles,master-data
```

### Parameters
Argument            | Abbr  | Description
--------------------|-------|-------------
--survey-results    | -s    | Path to the CSV file with survey results (or bug database)
--framework-results | -f    | Path to the CSV file with framework calculations
--correlation-type  | -c    | Correlation function to be used (Pearson or Spearman)
--exclude           | -e    |Service names to be excluded from the evaluation (e.g. external dependencies)

Here are some sample CSV files : _framework-results.csv_ and _survey-results.csv_.

## Further Information
It's the first program I've implemented in Scala. So please don't judge ;)
