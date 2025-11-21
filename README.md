## Usage Instructions

### 0. Clone and Set up the Repo 
The following software packages must be available on your system's path. Please ensure you meet the minimum specified version requirements - if listed. If not listed, then any version work.

0. **Prerequisites packages:**
- 1. GIT

    git: Essential for cloning this repository to your local machine. You can check your installation by running this command in your terminal.
    ```bash
    git --version
    ```

- 2. JDK 

    Java Development Kit (JDK) with minimum version: 25.0.1. This is required for compiling and running the Java source file included in the demo. You can check your version with this command:
    ```bash
    java --version
    ```

- 3. Node.js and npm (Node Package Manager)

    Node.js and npm: Needed for setting up and running the visual components (e.g., frontend dependencies, build scripts). Check versions with these 2 command: 
    ```bash
    node -v
    npm -v
    ```
- 4. Python3 (Optional Backup Tool)

    Python 3: Included as a backup utility. If your browser fails to correctly display the index.html file (e.g., due to local file restrictions), Python 3 can be used to quickly start a local web server (e.g., using python3 -m http.server - it is specified in this document at the end, but hopefully none of you will have to read that). Check with this command: 
    ```bash
    python3 --version.
    ```

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

### 1. Updating and Processing Data (Java Backend)

Follow these steps to update the input data and run the Java processing application (if you want it to process a new CSV file from MalwareBazaar):

1.  **Update Data File:** Place your new $\text{CSV}$ file into the `src/datasets/` folder.
2.  **Edit Driver File:** Open the $\text{App.java}$ file in the `src/` directory.
3.  **Change Argument:** Locate the section where the dataset file name is passed as an argument and **update the file name** to match your new $\text{CSV}$ file. Press ctrl + S to save the file. 
4.  **Run Java Application:** Compile and run the $\text{App.java}$ file using your preferred Java IDE or the command line.
    > *This step will process your data and prepare it for visualization - optional if you do section 2A.*

-----

### 2. Visualizing the Data (Node.js/Express Frontend)

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

### 3. Viewing documents of the code
1. From project root please go to the doc folder.
2. Run the html file: 
- For windows user, right click on the index.html file should gives an option to reveal in file explorer. Click that, then open that file in the browser of your choice. 
- For linux user, right click on the index.html file should gives an option to reveal in containing folder. Click that, then open that file in the browser of your choice.
    - However, there might be a case that doing just that result in an error, something amongs the line of file not found, when clicking on the link. If so, ensure that your terminal is in the doc folder, then run command:
        ```bash 
        python3 -m http.server 8000
        ```
    - Then open the file link that is the output of this command, typically the output will be: Serving HTTP on 0.0.0.0 port 8000 (http://0.0.0.0:8000/) ...
    - Then your link is: http://0.0.0.0:8000/
    - Going to this link will automatically gives you the docs. http://0.0.0.0:8000/