import React from 'react'
import client from '../client'
import {ServicesMetricsMatrix} from './ServicesMetricsMatrix'
import {ServiceRankingList} from './ServiceRankingList'

export class ServicesAndMetrics extends React.Component {
    constructor(props) {
        super(props);
        this.state = {servicesAndMetrics: []};
    }

    componentWillReceiveProps(nextProps) {
        client({method: 'GET', path: '/metrics/?architecture_id=' + nextProps.architecture.id}).done(response => {
            this.setState({servicesAndMetrics: this.metricsByService(nextProps.architecture, response.entity)});
        });
    }

    metricsByService(architecture, metrics) {
        return architecture.services.map(service => {
            return {
                serviceName: service.name,
                metrics: metrics.filter(m => m.serviceId === service.id)
            }
        });
    }

    render() {
        return (
            <div>
                <ServicesMetricsMatrix servicesAndMetrics={this.state.servicesAndMetrics}/>
                <ServiceRankingList servicesAndMetrics={this.state.servicesAndMetrics}/>
            </div>
        )
    }
}

