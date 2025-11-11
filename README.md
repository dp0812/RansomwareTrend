
# Malware Trend Dashboard 

## 1. A description of the project: 
Visualize data from a malware dataset with certain filtering in a somewhat organized manners: 
- Filter 1: Top 10 of the following xyz type. 
- Filter 2: Range of date Added.
## 2. Reflection:
- Why you selected this project (the need): Because a normal dataset has absolutely no meaningful way of letting a typical normal user a way of looking at it in a quick glance (only has csv, json, json schema and print view... which is basically larger csv).  
- Why/how it matters in cybersecurity: This should be able to help keep track of some of the newer, recent spread of malware, and possibly provide some guidance to normal users.  
- What others have done in this area: ransomware.live has a dedicated visualization for the recent trend also, however it has an overflow of information... and I have yet to find a reliable way to get a dataset from what is being displayed. Let hope that I can do something this good? 
- What prior knowledge or skills you bring: java, software architecture, optimization (refers to binding) and a certain more.  
- What new knowledge or skills you will need to build to complete the project: bash (to command different language at once), js, d3.  
- What you hope to learn by working on it: to effectively put together different coding language that works together without having to run each separately (which, I have only test with very, very trivial product, thus this might be a huge failure).   
## 3. Timeline:  
- October 12th: class diagram for the logic and architecture (DONE).  
- October 19th: rough draft of the logic implementation (DONE) 
- November 2nd: a refinement of said draft, which should produce at least a dataset of the current activity (DONE).  
- November 16th: visualization of the produced outputs (MOSTLY DONE).  
- December 7th: visualization of all the sets.  
## 4. Architecture: 
The overall structure, on a very high level overview is as follows:  
- Let us have a certain business logic code, denoted as A.java; a certain GUI or a certain visual, denoted as C.js; a certain intermediate file, denoted as B, and a certain script to activate all these files, denoted as D.sh. 
- D.sh is responsible for running the A.java, which then output to B. Then C.js shall reads from this intermediate file B, and produce a web output (perhaps, index.html) or whatever form that visualization is.  