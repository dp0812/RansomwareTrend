package fileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import utilities.Parser;

public class Logger {
    /** Default output directory if not specified by user. */
    private static final String defaultDirectory = "outputs";
    
    /**
     * Write the info to the file specified, create new file (and directory) if file does not exist. 
     * Will deleted all content in the old file before writing. 
     * @param objectToWriteList ArrayList<WritableObject> that contains info to be written.
     * @param categoryLine String array representation of the category line, typically the 1st line. 
     * @param fileName name of the file. Default directory to be written to is in field defaultDirectory.
     */
    public static void writeVisualizationData(ArrayList<WritableObject> objectToWriteList, String[] categoryLine, String fileName){
        WritingToFileAction<ArrayList<WritableObject>> writeLambda = createLambdaForWritingInfo();
        String catS = String.join(",",categoryLine);
        String defaultFilePath = defaultDirectory + File.separator + fileName;
        String[] tempDir = Parser.simpleParser(fileName, File.separatorChar);
        if (tempDir.length > 1) defaultFilePath = fileName;
        writeInfoToFile(objectToWriteList, writeLambda, defaultFilePath, catS);
    }

    /**
     * Append the info to the file specified, create new file (and directory) if file does not exist. 
     * Will append to the content in the old file - no deletion. 
     * @param objectToWriteList ArrayList<WritableObject> that contains info to be written.
     * @param categoryLine String array representation of the category line, typically the 1st line. 
     * @param fileName name of the file. Default directory to be written to is in field defaultDirectory.
     */
    public static void appendVisualizationData(ArrayList<WritableObject> objectToWriteList, String[] categoryLine, String fileName){
        WritingToFileAction<ArrayList<WritableObject>> writeLambda = createLambdaForWritingInfo();
        String catS = String.join(",",categoryLine);
        String defaultFilePath = defaultDirectory + File.separator + fileName;
        String[] tempDir = Parser.simpleParser(fileName, File.separatorChar);
        if (tempDir.length > 1) defaultFilePath = fileName;
        appendInfoToFile(objectToWriteList, writeLambda, defaultFilePath, catS);
    }

    /**
     * This is the core of simplifying everything that has to do with redundant declaration of writer. 
     * This rewrite info to file, not append. 
     * @param <T> must be ArrayList < some user defined data type > 
     * @param WritableObjectsArrList must be ArrayList <WritableObject> 
     * @param lambdaExpression the generic of the lambda must also be ArrayList <WritableObject> 
     * @param filePath what file to write, including the directory.
     * @param fileHeader first line of the file (typically category line)
     */
    private static <T> void writeInfoToFile(T WritableObjectsArrList, WritingToFileAction<T> lambdaExpression, String filePath, String fileHeader){
        createFileIfNotExists(filePath);
        try(FileWriter System = new FileWriter(filePath, false);
            BufferedWriter dot = new BufferedWriter(System);
            PrintWriter out = new PrintWriter(dot))
        {
            out.println(fileHeader);
            lambdaExpression.writeWithLambda(out, WritableObjectsArrList);
            
        }catch (IOException e) {
            System.out.println("Fail to write to " + filePath + " file due to: " + e);
        }
    }

    /**
     * This is the core of simplifying everything that has to do with redundant declaration of writer. 
     * This append info to file, not rewrite. 
     * @param <T> must be ArrayList < some user defined data type > 
     * @param WritableObjectsArrList must be ArrayList <WritableObject> 
     * @param lambdaExpression the generic of the lambda must also be ArrayList <WritableObject> 
     * @param filePath what file to write, including the directory.
     * @param fileHeader first line of the file (typically category line)
     */
    private static <T> void appendInfoToFile(T WritableObjectsArrList, WritingToFileAction<T> lambdaExpression, String filePath, String fileHeader){
        createFileIfNotExists(filePath);
        try(FileWriter System = new FileWriter(filePath, true);
            BufferedWriter dot = new BufferedWriter(System);
            PrintWriter out = new PrintWriter(dot))
        {
            out.println(fileHeader);
            lambdaExpression.writeWithLambda(out, WritableObjectsArrList);
            
        }catch (IOException e) {
            System.out.println("Fail to append to " + filePath + " file due to: " + e);
        }
    }

    /**
     * Helper for the appendInfoToFile and writeInfoToFile method in Logger.java 
     * @param filePath the filePath (including the folder) to be written to. 
     */
    private static void createFileIfNotExists(String filePath){
        Path path = Path.of(filePath);
        Path directoryPath = path.getParent();
        if (directoryPath == null) return;
        try {
            // Creates the directory and any nonexistent parent directories
            Files.createDirectories(directoryPath); 
        } catch (IOException e) {
            System.out.println("Fail to create directory '" + directoryPath + "' due to: " + e);
            return; 
        }
    }

    /**
     * This lambda does not need information when you create it. In fact, those info will be pass when it is run in updateInfoToCsvWithGeneric. 
     * @return a lambda writing info from ArrayList of objects that their class must implement WritableObject (must have getInfoArr in these objects) 
     */
    private static WritingToFileAction<ArrayList<WritableObject>> createLambdaForWritingInfo(){
        WritingToFileAction<ArrayList<WritableObject>> writeLambda = (out,content)-> {
            for (WritableObject someUserDefinedWritableObject : content){
                String[] userInfoArr = someUserDefinedWritableObject.getInfoArr();
                for (String info: userInfoArr){
                    out.print(info +",");
                }
                out.println();
            }
        }; 
        return writeLambda;
    }

}
