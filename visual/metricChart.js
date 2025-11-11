//dashboard will use this
export function drawChart(endpoint, title, color, dashboardContainer, chartTitle, dataValidityElement, tooltip, margin, width, height, formatXLabel) {    
    chartTitle.text(title);
    const svg = clearAndPrepareSvg(dashboardContainer, width, height, margin);
    
    //1. Fetch pre-processed JSON data (Update to handle the new JSON object)
    d3.json(endpoint).then(function(response) { 
        const transformedData = response.data;      
        const dateRange = response.dateRange;         

        // Set the date validity message
        if (dateRange && dateRange.earliest && dateRange.latest) {
            dataValidityElement.text(`Data is collected and applicable from ${dateRange.earliest} to ${dateRange.latest}`);
        } else {
            dataValidityElement.text("Data validity range could not be determined."); 
        }

        if (!transformedData || transformedData.length === 0) {
            svg.append("text")
               .attr("x", width/2).attr("y", height/2)
               .attr("text-anchor", "middle").text("No data available.");
            return;
        }
        //2. Define X and Y Scales (Use full name for positioning)
        const x = d3.scaleBand()
            .range([0, width])
            .domain(transformedData.map(d => (d.type)))
            .padding(0.2);
        const y = d3.scaleLinear()
            .domain([0, d3.max(transformedData, d => d.value)])
            .range([height, 0]);
        //3. Draw Components using sub-functions
        drawAxes(svg, transformedData, width, height, x, y, formatXLabel);
        drawBars(svg, transformedData, x, y, height, color, tooltip);
    })
    .catch(function(error) {
        console.error("Error fetching data from API:", error);
        chartTitle.text("Error: Could not load data.");
        dataValidityElement.text(""); 
    });
}

//Clear all thing on screen.
function clearAndPrepareSvg(dashboardContainer, width, height, margin) {
    dashboardContainer.html(""); //Clear existing SVG content
    const svg = dashboardContainer
      .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
      .append("g")
        .attr("transform", `translate(${margin.left},${margin.top})`);
    return svg;
}

//Draw Axes (Scales and Rotation)
function drawAxes(svg, transformedData, width, height, x, y, formatXLabel) {
    //Draw x-axis, using tickFormat for label truncation
    const xAxisGroup = svg.append("g")
        .attr("class", "x-axis") 
        .attr("transform", `translate(0,${height})`)
        .call(d3.axisBottom(x).tickFormat(formatXLabel)); 
    //Rotate and style the x-axis labels
    xAxisGroup.selectAll("text")
        .style("text-anchor", "end")
        .style("font-size", "14px")
        .attr("dx", "-.8em")
        .attr("dy", ".15em")
        .attr("transform", "rotate(-65)");
    //Draw and style the y-axis
    svg.append("g")
        .call(d3.axisLeft(y).tickSizeOuter(0))
        .selectAll("text")
        .style("font-size", "14px");
}

//Draw bars and tooltips
function drawBars(svg, transformedData, x, y, height, color, tooltip) {
    //Attach event handlers (mouseover, mousemove, mouseout) to the bars
    svg.selectAll("mybar")
        .data(transformedData)
        .enter()
        .append("rect")
            .attr("x", d => x(d.type))
            .attr("y", d => y(d.value))
            .attr("width", x.bandwidth())
            .attr("height", d => height - y(d.value))
            .attr("fill", color)
            //Mouse Over
            .on("mouseover", function(event, d) {
                d3.select(this).style("opacity", 0.7); 
                tooltip.transition().duration(200).style("opacity", 0.9);
                tooltip.html(`<b>Name:</b> ${d.type}<br><b>Count:</b> ${d.value}`)
                        .style("left", (event.pageX + 10) + "px")
                        .style("top", (event.pageY - 28) + "px");
            })
            //Mouse Move
            .on("mousemove", function(event) {
                tooltip
                    .style("left", (event.pageX + 10) + "px")
                    .style("top", (event.pageY - 28) + "px");
            })
            //Mouse Out
            .on("mouseout", function() {
                d3.select(this).style("opacity", 1); 
                tooltip.transition().duration(500).style("opacity", 0); 
            });
}