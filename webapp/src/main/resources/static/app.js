import React from 'react'
import ReactDOM from 'react-dom'
import {Router, Route, hashHistory} from 'react-router'
import {Link} from 'react-router'
import {ArchitecturesOverviewList} from './modules/ArchitecturesOverview'
import {Architecture} from './modules/Architecture'

class ServiceCriticalityApp extends React.Component {
    render() {
        return (
            <div id="service-criticality-app">
                <Router history={hashHistory}>
                    <Route path="/" component={ArchitecturesOverviewList}/>
                    <Route path="/:architectureId" component={Architecture}/>
                </Router>
            </div>
        )
    }
}

ReactDOM.render(
    <ServiceCriticalityApp />,
    document.getElementById('service-criticality-app-wrapper')
);