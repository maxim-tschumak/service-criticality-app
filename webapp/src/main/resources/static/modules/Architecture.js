import React from 'react'
import client from '../client'
import {Link} from 'react-router'
import {DependencyGraph} from './DependencyGraph'
import {ServicesAndMetrics} from './ServicesAndMetrics'

export class Architecture extends React.Component {

    constructor(props) {
        super(props);
        this.state = {architecture: {}};
    }

    componentDidMount() {
        client({method: 'GET', path: '/architectures/' + this.props.params.architectureId}).done(response => {
            this.setState({architecture: response.entity});
        });
    }

    render() {
        return (
            <div>
                <nav>
                    <div className="nav-wrapper">
                        <a className="brand-logo center">{this.state.architecture.name}</a>
                        <ul id="nav-mobile" className="left hide-on-med-and-down">
                            <li><Link to='/#'><i className="material-icons">navigate_before</i></Link></li>
                        </ul>
                    </div>
                </nav>
                <div className="container">
                    <div className="flow-text card-panel">{this.state.architecture.description}</div>
                    <DependencyGraph architecture={this.state.architecture}/>
                    <ServicesAndMetrics architecture={this.state.architecture}/>
                </div>
            </div>
        )
    }
}