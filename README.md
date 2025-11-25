## Set Up Instructions

**These are the prerequisites of the project, check them out: [Click here](Prereq.md)**

### I don't want to read version:
1. Run these commands, in the exact order that they are listed:

    ```bash
    git clone https://github.com/dp0812/RansomwareTrend.git Analyzer
    cd Analyzer
    ./run_project.sh
    ```

2. Then copy the highlight link (which will be output in your terminal), paste that to your browser, and enjoy. If you ran into errors, you will have to read the full instructions. 

    >**IMPORTANT:** **Do not close this terminal window**

----

### 0. Clone and Set up the Repo 

Open the terminal of your choice, and go to a directory where it is easy to find for you. If you use windows you can also go to a folder of your choice, right click and select open in terminal.

1. **Clone the repo:** Run the following command to clone this repository:
    ```bash
    git clone https://github.com/dp0812/RansomwareTrend.git Analyzer
    ```
2. **Move into the project root directory:** The root directory is the folder where you can see others folder (such as src, outputs, README.md, and most important run_project.sh listed)
    ```bash
    cd Analyzer
    ```
    If you do ls (Linux) or dir (Windows) you should see the all the important folders available. You are now good to proceed. 

-----

### 1. Visualizing the Data (Node.js/Express Frontend)

There are two methods to set up and start the visualization server:

#### A. Express Setup (Recommended)

Use the provided setup script to quickly initialize the project and install dependencies.
1. **Go to project root:** Ensure that you are in the project root folder. It should be (`..\Analyzer`)
    - Example: ``` C:RS\Analyzer```
2.  **Execute the Setup Script:** Run your script (`run_project.sh`). This will initialize the Node.js project, install the necessary dependencies (`express` and `csv-parser`), and start the visualization server.
    ```bash
    ./run_project.sh
    ```
    If there is a Permission denied error when running this .sh file: 
    - For linux system please use command:
        ```bash
        sudo chmod +x run_project.sh 
        ```
        Then rerun command: 
        ```bash
        ./run_project.sh
        ```
    - For windows system: open the terminal as administrator, and rerun the command. 
        ```bash
        ./run_project.sh
        ```

3.  **View Visualization:** The server will output a URL (usually `http://localhost:<port_number>`). In our case, this will be `http://localhost:8080` **Copy this link and paste it into your web browser's address bar** to view the data visualization.
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
4.  **View Visualization:** The server will output a URL (usually `http://localhost:<port_number>`). In our case, this will be `http://localhost:8080` **Copy this link and paste it into your web browser's address bar** to view the data visualization.

### 2. Viewing documents of the code
**Instructions of viewing the documentation of the project (if you are not familiar with javadocs): [Click here](DocsInstructions.md)**
