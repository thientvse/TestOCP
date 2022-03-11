public class ImageScanner implements AutoCloseable{
    public void close () throws Exception {
        System.out.print ("Scanner closed.");
    }
    public void scanImage () throws Exception {
        System.out.print ("Scan.");
        throw new Exception("Unable to scan.");
    }
}
