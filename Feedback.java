import com.fasterxml.jackson.databind.*;
import java.nio.file.*;

public class Feedback {
    
    public static void main(String args[]) {
        String[] arguments = new String[] {};
        run(arguments);
    }
    
    //runs student's main function with specified arguments
    public static void run(String[] arguments) { 
        try {
            Test.main(arguments);
          }
          catch(Exception e) {
            e.printStackTrace();
            System.out.println("******************************\n" 
            + findmsg(e) + 
            "\n*******************************");
          }
    }
    
    public static String findmsg(Exception input) {
        String content = "";
        Error[] errors;
        try{
            content = new String(Files.readAllBytes(Paths.get("ErrorMap.json")));
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