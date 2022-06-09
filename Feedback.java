import com.fasterxml.jackson.databind.*;
import java.nio.file.*;

public class Feedback {
    
    //private static String errorName;
    
    public static void main(String args[]) {
        //Test m = new Test(); // Creating an instance from our class
        String[] arguments = new String[] {};
        //Test.main(arguments);
        run(arguments);
    }
    
    //runs student's main function with arguments
    public static void run(String[] arguments) { 
        try {
            Test.main(arguments);
          }
          catch(Exception e) {
            e.printStackTrace();
            System.out.println("******************************\n" + findmsg(e) + "\n*******************************");
          }
    }
    
    public static String findmsg(Exception input) {
        String content = "";
        Error[] errors;
        try{
            Path filePath = Path.of("ErrorMap.json");
            content = Files.readString(filePath);
            ObjectMapper mapper = new ObjectMapper();
            errors = mapper.readValue(content, Error[].class);
        
            String errorName = input.getClass().getCanonicalName();
            for (Error error : errors) {
                if (errorName.contains(error.getErr())) {
                    return error.getMsg();
                }
            }
        }
        catch(Exception e) {
            System.out.println("Exception");
        }
        return null;
    }
    
}