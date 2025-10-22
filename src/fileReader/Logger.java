package fileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Logger {

    public static void writeVisualizationData(ArrayList<WritableObject> objectToWriteList, String[] categoryLine, String fileName){
        WritingToFileAction<ArrayList<WritableObject>> writeLambda = createLambdaForWritingInfo();
        String catS = String.join(",",categoryLine);
        writeInfoToFile(objectToWriteList, writeLambda, fileName, catS);
    }

    public static void appendVisualizationData(ArrayList<WritableObject> objectToWriteList, String[] categoryLine, String fileName){
        WritingToFileAction<ArrayList<WritableObject>> writeLambda = createLambdaForWritingInfo();
        String catS = String.join(",",categoryLine);
        appendInfoToFile(objectToWriteList, writeLambda, fileName, catS);
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

    /**
     * This is the core of simplifying everything that has to do with redundant declaration of writer. 
     * This rewrite info to file, not append. 
     * @param <T> must be ArrayList < some user defined data type > 
     * @param infoArrList must be ArrayList < some user defined data type > 
     * @param lambdaExpression the generic of the lambda must also be ArrayList < some user defined data type > 
     * @param filePath what file to write. 
     * @param fileHeader first line of the file (typically category line)
     */
    private static <T> void writeInfoToFile(T infoArrList, WritingToFileAction<T> lambdaExpression, String filePath, String fileHeader){
        createFileIfNotExists(filePath);
        try(FileWriter System = new FileWriter(filePath, false);
            BufferedWriter dot = new BufferedWriter(System);
            PrintWriter out = new PrintWriter(dot))
        {
            out.println(fileHeader);
            lambdaExpression.writeWithLambda(out, infoArrList);
            
        }catch (IOException e) {
            System.out.println("Fail to write to " + filePath + " file due to: " + e);
        }
    }

    /**
     * This is the core of simplifying everything that has to do with redundant declaration of writer. 
     * This append info to file, not rewrite. 
     * @param <T> must be ArrayList < some user defined data type > 
     * @param infoArrList must be ArrayList < some user defined data type > 
     * @param lambdaExpression the generic of the lambda must also be ArrayList < some user defined data type > 
     * @param filePath what file to write. 
     * @param fileHeader first line of the file (typically category line)
     */
    private static <T> void appendInfoToFile(T infoArrList, WritingToFileAction<T> lambdaExpression, String filePath, String fileHeader){
        createFileIfNotExists(filePath);
        try(FileWriter System = new FileWriter(filePath, true);
            BufferedWriter dot = new BufferedWriter(System);
            PrintWriter out = new PrintWriter(dot))
        {
            out.println(fileHeader);
            lambdaExpression.writeWithLambda(out, infoArrList);
            
        }catch (IOException e) {
            System.out.println("Fail to append to " + filePath + " file due to: " + e);
        }
    }

    /**
     * 
     * @param filePath the filePath (including the folder) to be written to. 
     */
    private static void createFileIfNotExists(String filePath){
        Path path = Paths.get(filePath);
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

}
