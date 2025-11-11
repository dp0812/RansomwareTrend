# Data Processing and Visualization Project

This project combines a **Java backend** for data processing and a **Node.js/Express frontend** for data visualization.

---

## Getting Started

### Prerequisites

To run this project, you need the following software installed on your system:

* **Java Development Kit (JDK):** Version **25.0.1** or later.
* **Node.js:** A runtime environment that includes **npm** (Node Package Manager).

---

## Project Structure

The key components of the project are located in the following folders:

* `src/`: Contains the core Java application logic.
    * `src/App.java`: The main driver file for data processing.
    * `src/datasets/`: **This is where you place your raw CSV data files.**
* `visualserver/`: Contains the Node.js server and client-side visualization code.
    * `visualserver/server.js`: The backend server file.
* `visual/`: (Intended for frontend/client-side assets, not explicitly used in the instructions, but kept for a standard structure.)

---

## Usage Instructions

### 1. Updating and Processing Data

Follow these steps to update the input data and run the Java processing application (if you want it to process a new csv file from MalwareBazaar):

1.  **Update Data File:** Place your new $\text{CSV}$ file into the `src/datasets/` folder.
2.  **Edit Driver File:** Open the $\text{App.java}$ file in the `src/` directory.
3.  **Change Argument:** Locate the section where the dataset file name is passed as an argument and **update the file name** to match your new $\text{CSV}$ file.
4.  **Run Java Application:** Compile and run the $\text{App.java}$ file using your preferred Java IDE or the command line.
    > *This step will process your data and prepare it for visualization.*

### 2. Visualizing the Data

To start the visualization server and view the results in your web browser:

1.  **Navigate to the Server Directory:** Open your terminal or command prompt and change the directory to the visualization server folder:
    ```bash
    cd visualserver
    ```
2.  **Initialize Project and Install Dependencies:** Run the following commands to set up the Node.js environment and install required packages (`express` for the server and `csv-parser` for reading data):
    ```bash
    npm init -y
    npm install express csv-parser
    ```
3.  **Start the Visualization Server:** Execute the server file using Node.js:
    ```bash
    node server.js
    ```
    > **IMPORTANT:** **Do not close this terminal window** while you are viewing the visualization, as it is running the server process.
4.  **View Visualization:** The server will output a URL (usually `http://localhost:<port_number>`). **Copy this link and paste it into your web browser's address bar** to view the data visualization.
