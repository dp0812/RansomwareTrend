const express = require('express');
const fs = require('fs');
const path = require('path');
const csv = require('csv-parser');
const app = express();
const PORT = 8080;

// Allow the browser to access files in the 'visual' folder (your HTML/JS)
app.use(express.static(path.join(__dirname,'..', 'visual'))); 

// Start the server
app.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});

// Function to read the full malwareTest.csv and extract the full timestamp range
function getCsvDateRange(filePath, callback) {
    let earliestDate = null;
    let latestDate = null;
    
    fs.createReadStream(filePath)
        .pipe(csv())
        .on('data', (data) => {
            // Capture the entire timestamp string, including time
            const currentDate = data.first_seen_utc; 
            
            // CSV is sorted descending: the first data row is latest, the last is earliest.
            if (latestDate === null) {
                latestDate = currentDate; 
            }
            earliestDate = currentDate; 
        })
        .on('end', () => {
            callback(earliestDate, latestDate);
        })
        .on('error', (err) => {
            console.error("Error reading CSV for date range:", err);
            callback(null, null);
        });
}

// Renamed and updated function to process the *report* CSV
function processReportData(filePath, callback) {
    const results = [];
    fs.createReadStream(filePath)
        .pipe(csv())
        .on('data', (data) => results.push(data)) 
        .on('end', () => {
            if (results.length > 0) {
                const rawValues = results[0];
                let transformedData = Object.entries(rawValues)
                    .map(([type, value]) => ({ 
                        type: type, 
                        value: parseInt(value, 10)
                    }));
                transformedData  = transformedData.slice(0,10);
                callback(transformedData);
            } else {
                callback([]);
            }
        });
}

// Function to read and process the description CSV for HTML display
function processDescriptionCsv(filePath, callback) {
    fs.readFile(filePath, { encoding: 'utf8' }, (err, data) => {
        if (err) {
           console.error("Error reading description file:", err);
           return callback(null);
        }
        
        const allLines = data.trim().split('\n');
        
        // Skip the first line (the header row: "name,description") 
        // and filter out any remaining empty lines
        const lines = allLines.slice(1).filter(line => line.length > 0); 
        
        let htmlContent = '';
        lines.forEach(line => {
            // Split by the first comma and trim whitespace
            const parts = line.split(',');
            const signature = parts[0].trim();
            const description = parts.slice(1).join(',').trim(); 

            // Format as bold name and plain description
            htmlContent += `<p style="margin-bottom: 5px;"><strong>${signature}:</strong> ${description}</p>`;
        });

        callback(htmlContent);
    });
}

// Common logic for all API endpoints to include date range
function handleReportApi(reportPath, fullCsvPath, res) {
    processReportData(reportPath, (reportData) => {
        getCsvDateRange(fullCsvPath, (earliestDate, latestDate) => {
            // Send both the report data and the date range back
            res.json({
                data: reportData,
                dateRange: { earliest: earliestDate, latest: latestDate }
            });
        });
    });
}


// The full CSV file to get the date range from 
const fullCsvPath = path.join(__dirname,'..', 'outputs', 'formattedMalwareInfo.csv');

// API endpoint for the first chart
app.get('/api/data1', (req, res) => {
    const reportPath = path.join(__dirname,'..', 'outputs', 'signatureReport.csv');
    handleReportApi(reportPath, fullCsvPath, res);
});

// API endpoint for the second chart
app.get('/api/data2', (req, res) => {
    const reportPath = path.join(__dirname,'..', 'outputs', 'fileTypeReport.csv');
    handleReportApi(reportPath, fullCsvPath, res);
});

// API endpoint for the second chart
app.get('/api/data3', (req, res) => {
    const reportPath = path.join(__dirname,'..', 'outputs', 'mimeTypeReport.csv');
    handleReportApi(reportPath, fullCsvPath, res);
});


// Description APIs
app.get('/api/description1', (req, res) => {
    const filePath = path.join(__dirname,'..', 'descriptions', 'description1.csv');
    processDescriptionCsv(filePath, (htmlContent) => {
        if (htmlContent) {
            res.send(htmlContent);
        } else {
            res.status(500).send("Error loading description data.");
        }
    });
});

app.get('/api/description2', (req, res) => {
    const filePath = path.join(__dirname,'..', 'descriptions', 'description2.csv');
    processDescriptionCsv(filePath, (htmlContent) => {
        if (htmlContent) {
            res.send(htmlContent);
        } else {
            res.status(500).send("Error loading description data.");
        }
    });
});

app.get('/api/description3', (req, res) => {
    const filePath = path.join(__dirname,'..', 'descriptions', 'description3.csv');
    processDescriptionCsv(filePath, (htmlContent) => {
        if (htmlContent) {
            res.send(htmlContent);
        } else {
            res.status(500).send("Error loading description data.");
        }
    });
});