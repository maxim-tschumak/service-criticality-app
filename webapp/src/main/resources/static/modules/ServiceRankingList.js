import React from 'react'

export class ServiceRankingList extends React.Component {

    metricsSetIntersection(servicesAndMetrics) {
        if (servicesAndMetrics.length == 0) return [];
        else return servicesAndMetrics
            .map(service => service.metrics.map(metric => metric.name)) // map each service entry to array of metrics
            .reduce((prev, next) => prev.filter(metric => next.includes(metric))); // compute intersection of metrics
    }

    render() {
        const metricsSetIntersection = this.metricsSetIntersection(this.props.servicesAndMetrics);
        const serviceList = this.props.servicesAndMetrics
            .map((serviceMetricsEntry) => {
                return {
                    serviceName: serviceMetricsEntry.serviceName,
                    aggregatedValue: serviceMetricsEntry.metrics
                        .filter(m => metricsSetIntersection.includes(m.name))
                        .map(m => m.normalizedValue)
                        .reduce((a, b) => a + b, 0)
                }
            })
            .sort((a, b) => {
                return b.aggregatedValue - a.aggregatedValue;
            });

        const serviceMetrics = serviceList.map((service) =>
            <ServiceRankingEntry key={"service-ranking-entry-" + service.serviceName} service={service.serviceName}
                                 score={service.aggregatedValue}/>
        );
        return (
            <div>
                <h3>Service Ranking</h3>
                <div className="row">
                    <div className="col s12 m12 l6">
                        <table className="highlight responsive-table">
                            <thead>
                            <tr>
                                <th data-field="service-id">Service</th>
                                <th data-field="aggregated-score">Score</th>
                            </tr>
                            </thead>
                            <tbody>
                            {serviceMetrics}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        )
    }
}

class ServiceRankingEntry extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.service}</td>
                <td>{this.props.score}</td>
            </tr>
        )
    }
}