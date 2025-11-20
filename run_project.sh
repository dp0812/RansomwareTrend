# Config
JAVA_SOURCE_DIR="src"
JAVA_MAIN_FILE="App.java"
JAVA_CLASS_NAME="App"         # Class in the default package, so no package prefix here
JAVA_BIN_DIR="bin"            # The standard output directory for compiled classes
SERVER_DIR="visualserver"
SERVER_FILE="server.js"
# Build
echo "Building and Running Java Application"
mkdir -p "$JAVA_BIN_DIR" 
echo "Ensuring output directory $JAVA_BIN_DIR exists."
# Compile using -sourcepath
# The compiler treats 'src' as the root, finds 'simulation/Simulation.java', 
# and places the compiled classes (App.class and simulation/Simulation.class) in 'bin'.
echo "Compiling $JAVA_MAIN_FILE and dependencies to $JAVA_BIN_DIR/..."
if javac -d "$JAVA_BIN_DIR" -sourcepath "$JAVA_SOURCE_DIR" "$JAVA_SOURCE_DIR/$JAVA_MAIN_FILE"; then
    echo "Compilation successful. Running Java application..."
    if java -cp "$JAVA_BIN_DIR" "$JAVA_CLASS_NAME"; then
        echo "Java application finished processing data."
    else
        echo "ERROR: Java application failed to run."
        exit 1
    fi
else
    echo "ERROR: Java compilation failed. Check your source files and imports."
    exit 1
fi

# Server
echo "Setting up and Launching Visualization Server"
cd "$SERVER_DIR" || { echo "ERROR: Cannot change directory to $SERVER_DIR. Aborting."; exit 1; }
# npm init and install
if [ ! -f "package.json" ]; then
    echo "Initializing npm..."
    npm init -y > /dev/null #suppress print
fi

echo "Installing Node.js dependencies (express, csv-parser)..."
npm install express csv-parser --silent
echo "Starting Node.js server ($SERVER_FILE)..."
node "$SERVER_FILE" & 
SERVER_PID=$!
SERVER_URL="http://localhost:8080" #this url is fix in the server.js file. 

echo "Server started successfully (PID: $SERVER_PID)."
echo "Visualization URL: " 
echo -e "\e[1;35m $SERVER_URL \e[0m"
echo "To stop the server and complete the script, press [ENTER] now."

# Wait for user input to stop the server
read -r -p ""
kill "$SERVER_PID" 2>/dev/null
echo "Server (PID $SERVER_PID) has been stopped."
cd ..
echo "Automation complete."