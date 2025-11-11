import { drawChart } from './metricChart.js';

//Global Setup
const margin = {top: 20, right: 30, bottom: 200, left: 90},
      width = 460 - margin.left - margin.right,
      height = 400 - margin.top - margin.bottom;

const tooltip = d3.select("body").append("div")
    .attr("class", "tooltip")
    .style("opacity", 0)
    .style("position", "absolute")
    .style("background-color", "white")
    .style("border", "1px solid #ccc")
    .style("padding", "8px")
    .style("border-radius", "4px")
    .style("pointer-events", "none"); 

const dashboardContainer = d3.select("#dashboard-container");
const chartTitle = d3.select("#chart-title");
const dataValidityElement = d3.select("#data-validity");
const descriptionTitleElement = d3.select("#description-title"); // ADDED MISSING GLOBAL SELECTION

//Trim long labels (mostly x axis problem)
function formatXLabel(label) {
    const maxLength = 20; 
    if (label.length > maxLength) {
        return label.substring(0, maxLength) + '...';
    }
    return label;
}

// NEW REFACTORED FUNCTION: Load data for the description box
function loadDescriptionBox(endpoint) { 
    d3.select("#description-content").html("Loading descriptions...");
    // Fetch the HTML content from the dynamic API endpoint
    d3.text(endpoint).then(function(htmlContent) { 
        if (htmlContent) {
            d3.select("#description-content").html(htmlContent);
        } else {
            d3.select("#description-content").text("No descriptions available.");
        }
    })
    .catch(function(error) {
        console.error("Error fetching description data:", error);
        d3.select("#description-content").text("Error: Could not load descriptions.");
    });
}

//Button Handler Functions (Updated to set descriptionTitleElement) ---

function drawSignatureOccurrencesChart() {
    drawChart("/api/data1", "Malware Signature Occurrences", "#69b3a2", 
        dashboardContainer, chartTitle, dataValidityElement, tooltip, margin, width, height, formatXLabel);
    // Set header and load descriptions for Signature
    descriptionTitleElement.text("Signature Descriptions");
    loadDescriptionBox("/api/description1");
}

function drawFileTypeOccurrencesChart() {
    drawChart("/api/data2", "File Type Occurrences", "#ff8c00",
        dashboardContainer, chartTitle, dataValidityElement, tooltip, margin, width, height, formatXLabel);
    // Set header and load descriptions for File Type
    descriptionTitleElement.text("File Type Descriptions");
    loadDescriptionBox("/api/description2");
}

function drawMimeTypeOccurrencesChart(){
    drawChart("/api/data3", "Mime Type Occurrences", "#32a420ff",
        dashboardContainer, chartTitle, dataValidityElement, tooltip, margin, width, height, formatXLabel);
    // Set header and load descriptions for MIME Type
    descriptionTitleElement.text("MIME Type Descriptions");
    loadDescriptionBox("/api/description3");
}

//💡 Event Listeners for Buttons (Unchanged)
d3.select("#btn-chart1").on("click", function() {
    d3.selectAll(".dashboard-controls button").classed("active", false); 
    d3.select(this).classed("active", true); 
    drawSignatureOccurrencesChart();
});

d3.select("#btn-chart2").on("click", function() {
    d3.selectAll(".dashboard-controls button").classed("active", false);
    d3.select(this).classed("active", true);
    drawFileTypeOccurrencesChart();
});

d3.select("#btn-chart3").on("click", function() {
    d3.selectAll(".dashboard-controls button").classed("active", false);
    d3.select(this).classed("active", true);
    drawMimeTypeOccurrencesChart();
});

//Initialize the dashboard with the first chart when the page loads
drawSignatureOccurrencesChart();