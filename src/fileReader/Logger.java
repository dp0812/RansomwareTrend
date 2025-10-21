package fileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Logger {

    public void writeVisualizationData(ArrayList<WritableObject> objectToWriteList, String[] categoryLine, String fileName){
        WritingToFileAction<ArrayList<WritableObject>> writeLambda = createLambdaForWritingInfo();
        String cat = Arrays.toString(categoryLine);
        String catS = cat.substring(1, cat.length()-1);
        writeInfoToFile(objectToWriteList, writeLambda, fileName, catS);
    }

    public void appendVisualizationData(ArrayList<WritableObject> objectToWriteList, String[] categoryLine, String fileName){
        WritingToFileAction<ArrayList<WritableObject>> writeLambda = createLambdaForWritingInfo();
        String cat = Arrays.toString(categoryLine);
        String catS = cat.substring(1, cat.length()-1);
        appendInfoToFile(objectToWriteList, writeLambda, fileName, catS);
    }
    /**
     * This lambda does not need information when you create it. In fact, those info will be pass when it is run in updateInfoToCsvWithGeneric. 
     * @return a lambda writing info from ArrayList of objects that their class must implement WritableObject (must have getInfoArr in these objects) 
     */
    private WritingToFileAction<ArrayList<WritableObject>> createLambdaForWritingInfo(){
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
     * @param fileName what file to write. 
     * @param fileHeader first line of the file (typically category line)
     */
    private <T> void writeInfoToFile(T infoArrList, WritingToFileAction<T> lambdaExpression, String fileName, String fileHeader){
        //createFile(fileName);
        try(FileWriter System = new FileWriter(fileName, false);
            BufferedWriter dot = new BufferedWriter(System);
            PrintWriter out = new PrintWriter(dot))
        {
            out.println(fileHeader);
            lambdaExpression.writeWithLambda(out, infoArrList);
            
        }catch (IOException e) {
            System.out.println("Fail to write to userInfoCsv file due to: " + e);

        }
    }

    /**
     * This is the core of simplifying everything that has to do with redundant declaration of writer. 
     * This append info to file, not rewrite. 
     * @param <T> must be ArrayList < some user defined data type > 
     * @param infoArrList must be ArrayList < some user defined data type > 
     * @param lambdaExpression the generic of the lambda must also be ArrayList < some user defined data type > 
     * @param fileName what file to write. 
     * @param fileHeader first line of the file (typically category line)
     */
    private <T> void appendInfoToFile(T infoArrList, WritingToFileAction<T> lambdaExpression, String fileName, String fileHeader){
        //createFile(fileName);
        try(FileWriter System = new FileWriter(fileName, true);
            BufferedWriter dot = new BufferedWriter(System);
            PrintWriter out = new PrintWriter(dot))
        {
            out.println(fileHeader);
            lambdaExpression.writeWithLambda(out, infoArrList);
            
        }catch (IOException e) {
            System.out.println("Fail to append to userInfoCsv file due to: " + e);

        }
    }
    //Not use yet. 
    public void createFile(String fileName){
        File file = new File("src/" + fileName);
        file.getParentFile().mkdirs(); // Creates the directory structure if it doesn't exist
        try{
            file.createNewFile(); // Creates the file
        } catch (IOException errIO){
            System.out.println("Failed to create file: " + errIO);
        }
    }

}
