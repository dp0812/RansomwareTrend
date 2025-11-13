## Usage Instructions

### 1. Updating and Processing Data (Java Backend)

Follow these steps to update the input data and run the Java processing application (if you want it to process a new CSV file from MalwareBazaar):

1.  **Update Data File:** Place your new $\text{CSV}$ file into the `src/datasets/` folder.
2.  **Edit Driver File:** Open the $\text{App.java}$ file in the `src/` directory.
3.  **Change Argument:** Locate the section where the dataset file name is passed as an argument and **update the file name** to match your new $\text{CSV}$ file.
4.  **Run Java Application:** Compile and run the $\text{App.java}$ file using your preferred Java IDE or the command line.
    > *This step will process your data and prepare it for visualization.*

-----

### 2. Visualizing the Data (Node.js/Express Frontend)

There are two methods to set up and start the visualization server:

#### A. Express Setup (Recommended)

Use the provided setup script to quickly initialize the project and install dependencies.
1. **Go to project root:** Ensure that you are in the project root folder. It should be (`..\RansomwareTrend`)
    - Example: ``` C:RS\RansomwareTrend```
2.  **Execute the Setup Script:** Run your script (`run_project.sh`). This will initialize the Node.js project, install the necessary dependencies (`express` and `csv-parser`), and start the visualization server.
    ```bash
    ./run_project.sh
    ```
3.  **View Visualization:** The server will output a URL (usually `http://localhost:<port_number>`). **Copy this link and paste it into your web browser's address bar** to view the data visualization.
    > **IMPORTANT:** **Do not close this terminal window** while you are viewing the visualization, as it is running the server process.

-----

#### B. Manual Setup

If you prefer to run the commands individually:

1.  **Navigate to the Server Directory:**
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