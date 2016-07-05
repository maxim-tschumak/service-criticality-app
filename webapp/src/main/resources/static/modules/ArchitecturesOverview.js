import React from 'react'
import client from '../client'
import {Link} from 'react-router'

class ArchitectureOverview extends React.Component {
    render() {
        return (
            <div className="col s12 m6 l4">
                <div className="card blue-grey darken-1">
                    <div className="card-content white-text">
                        <span className="card-title">{this.props.architecture.name}</span>
                        <p>{this.props.architecture.description}</p>
                    </div>
                    <div className="card-action">
                        <Link to={'/' + this.props.architecture.id}>Dependency Graph</Link>
                    </div>
                </div>
            </div>
        )
    }
}
class Filter extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
        // bind non-lifecycle functions
        this.handleFilterChange = this.handleFilterChange.bind(this);
    }

    handleFilterChange(e) {
        this.props.updateFilter(e.target.value);
    }

    render() {
        return (
            <nav>
                <div className="nav-wrapper">
                    <div className="input-field">
                        <form>
                            <div className="input-field">
                                <input id="search" type="search" placeholder="Architecture Stack Name" required
                                       value={this.state.filterText} onChange={this.handleFilterChange}/>
                                <label htmlFor="search"><i className="material-icons">search</i></label>
                                <i className="material-icons">close</i>
                            </div>
                        </form>
                    </div>
                </div>
            </nav>
        )
    }
}

export class ArchitecturesOverviewList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {architectures: []};
        // bind non-lifecycle functions
        this.handleFilterUpdate = this.handleFilterUpdate.bind(this);
    }

    componentDidMount() {
        client({method: 'GET', path: '/architectures'}).done(response => {
            this.setState({architectures: response.entity});
        });
    }

    handleFilterUpdate(filterValue) {
        this.setState({
            nameFilter: filterValue
        });
    }

    render() {
        const filteredArchitectures = this.state.nameFilter ? this.state.architectures.filter(a => {
            return a.name.toLowerCase().includes(this.state.nameFilter.toLowerCase())
        }) : this.state.architectures;
        const architectures = filteredArchitectures.map(architecture =>
            <ArchitectureOverview key={architecture.id} architecture={architecture}/>
        );
        return (
            <div>
                <Filter updateFilter={this.handleFilterUpdate}/>
                <div id="architecture-overview-list" className="container row">
                    {architectures}
                </div>
            </div>
        )
    }
}