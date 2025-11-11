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

// Function to read and process the CSV (due to the unique single-row format)
function processReportData(filePath, callback) {
    const results = [];
    fs.createReadStream(filePath)
        .pipe(csv())
        .on('data', (data) => results.push(data)) // Reads the single data row
        .on('end', () => {
            if (results.length > 0) {
                // Transform the wide-format CSV into the long-format D3 prefers
                const rawValues = results[0];
                let transformedData = Object.entries(rawValues)
                    .map(([type, value]) => ({ 
                        type: type, 
                        value: parseInt(value, 10) // Convert string to number on the server!
                    }));

                //Impose an arbitrary limit - top 10 entries only. 
                transformedData  = transformedData.slice(0,10);
                callback(transformedData);
            } else {
                callback([]);
            }
        });
}

// Function to read the full CSV and extract date range
function getCsvDateRange(filePath, callback) {
    let earliestDate = null;
    let latestDate = null;
    
    fs.createReadStream(filePath)
        .pipe(csv()) //auto ignore category rows. 
        .on('data', (data) => {
            //Extract full date time. 
            const currentDate = data.first_seen_utc; 
            //CSV already sorted in descending order by date:
            if (latestDate === null) {
                latestDate = currentDate; // The first data row has the latest date
            }
            earliestDate = currentDate; // The last data row processed will have the earliest date
        })
        .on('end', () => {
            callback(earliestDate, latestDate);
        })
        .on('error', (err) => {
            console.error("Error reading CSV for date range:", err);
            callback(null, null);
        });
}

// Add this common handler function before your app.get routes
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
// The full CSV file to get the date range from (malwareTest.csv)
const fullCsvPath = path.join(__dirname,'..', 'outputs', 'malwareTest.csv');

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
