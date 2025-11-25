# How to View the Code Documentation
The project includes generated documentation by Javadocs that you can view in your web browser.

----

## 1. Go to the Docs Folder
From your project root directory (`Analyzer`), navigate to the documentation folder:

```bash
cd doc
```

## 2. Open the index.html file

- Windows: 
    - Right click on the index.html file should gives an option to reveal in file explorer. 
    - Click that, then open that file in the browser of your choice. 
- Linux:
    - Right click on the index.html file should gives an option to reveal in containing folder. 
    - Click that, then open that file in the browser of your choice.
    - However, there might be a case that doing just that result in an error, something amongs the line of file not found, when clicking on the link. If so, ensure that your terminal is in the doc folder, then run command:
        ```bash 
        python3 -m http.server 8000
        ```
    - Then open the file link that is the output of this command, typically the output will be: Serving HTTP on 0.0.0.0 port 8000 (http://0.0.0.0:8000/) ...
    - Then your link is: http://0.0.0.0:8000/
    - Going to this link will automatically gives you the docs. http://0.0.0.0:8000/
    - DO NOT close the terminal when you are looking at the docs. 