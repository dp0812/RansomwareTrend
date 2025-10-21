package fileReader;
/** 
 * Create as a arbitrary superclass for object to be write to file. 
 * An attempt to apply the Dependency Inversion Principle: we know we will write something, but this something is ought to change (newer type, etc) 
 */
public interface WritableObject {
    /**
     * Standard for the object which info we want to write to file. 
     * @return a view of the object info, in array form. 
     */
    public abstract String[] getInfoArr();
}
