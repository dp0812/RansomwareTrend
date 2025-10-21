package fileReader;
import java.io.PrintWriter;
/**
 * This is specifically to make all the redundant writer to become one (because the declaration and set up of writers are the same)
 * also the interface use generics :3 to be able to put multiple user defined data type. 
 */
@FunctionalInterface
public interface WritingToFileAction<T> {
    public abstract void writeWithLambda(PrintWriter out, T content);
}
