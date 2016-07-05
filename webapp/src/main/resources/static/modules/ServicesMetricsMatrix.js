import React from 'react'

export class ServicesMetricsMatrix extends React.Component {

    extractMetricsSet(metrics) {
        const setOfMetricNames = [];
        metrics.forEach(s => {
            s.metrics.forEach(m => {
                if (!setOfMetricNames.includes(m.name)) setOfMetricNames.push(m.name)
            });
        });
        return setOfMetricNames;
    }

    render() {
        const metricNames = this.extractMetricsSet(this.props.servicesAndMetrics);
        const columnNames = metricNames.map(metricName => {
            return <MatrixHeader key={"service-metrics-matrix-header-" + metricName}
                                 metricName={metricName}/>
        });

        const serviceEntries = this.props.servicesAndMetrics.map(s => {
            const serviceMetrics = {};
            serviceMetrics.serviceName = s.serviceName;
            serviceMetrics.metrics = {};
            s.metrics.forEach(m => {
                serviceMetrics.metrics[m.name] = m.value;
            });
            return <MatrixLine key={"service-metrics-matrix-line-" + serviceMetrics.serviceName}
                               serviceMetrics={serviceMetrics} metricNames={metricNames}/>
        });
        return (
            <div>
                <h3>Services / Metrics Matrix</h3>
                <div className="row">
                    <div className="col s12">
                        <table className="highlight responsive-table">
                            <thead>
                            <tr>
                                <th data-field="service-id"> </th>
                                {columnNames}
                            </tr>
                            </thead>
                            <tbody>
                            {serviceEntries}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        )
    }
}

class MatrixHeader extends React.Component {
    render() {
        return (
            <th data-field="aggregated-metric-name">{this.props.metricName}</th>
        )
    }
}

class MatrixLine extends React.Component {
    render() {
        const entriesOfAService = this.props.metricNames.map(metricName => {
            return <MatrixEntry
                key={"service-metrics-line-entry-" + this.props.serviceMetrics.serviceName + "-" + metricName}
                serviceMetrics={this.props.serviceMetrics} serviceName={this.props.serviceMetrics.serviceName}
                metricName={metricName}/>
        });

        return (
            <tr>
                <td>{this.props.serviceMetrics.serviceName}</td>
                {entriesOfAService}
            </tr>
        )
    }
}

class MatrixEntry extends React.Component {
    render() {
        const value = this.props.serviceMetrics.metrics[this.props.metricName];
        const niceValue = typeof value === "number" ? (Math.floor(value * 100) / 100) : value;
        return (
            <td>{niceValue}</td>
        )
    }
}