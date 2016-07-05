import React from 'react'
import d3 from 'd3'

export class DependencyGraph extends React.Component {
    constructor(props) {
        super(props);
        this.state = {graph: {}};
    }

    componentDidUpdate() {
        function extractLinks(architecture) {
            const dependencies = [];
            architecture.services.forEach(service => {
                service.dependencies.forEach(d => {
                    const dependency = {};
                    dependency.source = service.name;
                    dependency.type = 'depends_on';
                    dependency.target = architecture.services.find(s => {
                        return d === s.id;
                    }).name;
                    dependencies.push(dependency);
                });
            });
            return dependencies;
        }

        const links = extractLinks(this.props.architecture);
        const nodes = {};

        links.forEach(link => {
            link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
            link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
        });

        const width = $("#dependency-graph").parent().width(),
            height = width / 2.5;

        const force = d3.layout.force()
            .nodes(d3.values(nodes))
            .links(links)
            .size([width, height])
            .linkDistance(width / 15)
            .linkStrength(0.05)
            .charge(-500)
            .gravity(0.1)
            .on("tick", tick)
            .start();

        const drag = force.drag()
            .on("dragstart", dragstart);

        const svg = d3.select("#dependency-graph").insert("svg")
            .attr("width", width)
            .attr("height", height);

        svg.append("defs").selectAll("marker")
            .data(["depends_on", "communicates_with"])
            .enter().append("marker")
            .attr("id", d => {
                return d;
            })
            .attr("viewBox", "0 -5 10 10")
            .attr("refX", 15)
            .attr("refY", -1.5)
            .attr("markerWidth", 6)
            .attr("markerHeight", 6)
            .attr("orient", "auto")
            .append("path")
            .attr("d", "M0,-5L10,0L0,5");

        const path = svg.append("g").selectAll("path")
            .data(force.links())
            .enter().append("path")
            .attr("class", d => {
                return "link " + d.type;
            })
            .attr("marker-end", d => {
                return "url(#" + d.type + ")";
            });

        const circle = svg.append("g").selectAll("circle")
            .data(force.nodes())
            .enter().append("circle")
            .attr("r", width / 100)
            .on("dblclick", dblclick)
            .call(drag);

        const text = svg.append("g").selectAll("text")
            .data(force.nodes())
            .enter().append("text")
            .attr("font-size", width / 75 + "px")
            .attr("x", width / 75)
            .attr("y", width / 100)
            .text(d => {
                return d.name;
            });

        function tick() {
            path.attr("d", linkArc);
            circle.attr("transform", transform);
            text.attr("transform", transform);
        }

        function linkArc(d) {
            const dx = d.target.x - d.source.x,
                dy = d.target.y - d.source.y,
                dr = Math.sqrt(dx * dx + dy * dy);
            return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
        }

        function transform(d) {
            return "translate(" + d.x + "," + d.y + ")";
        }

        function dblclick(d) {
            d3.select(this).classed("fixed", d.fixed = false);
        }

        function dragstart(d) {
            d3.select(this).classed("fixed", d.fixed = true);
        }
    }

    render() {
        return (
            <div>
                <h3>Dependency Graph</h3>
                <div className="center-align" id="dependency-graph"></div>
            </div>
        )
    }
}