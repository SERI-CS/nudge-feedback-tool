import com.fasterxml.jackson.databind.*;
import java.nio.file.*;
import java.util.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FeedbackRecursion {
    
    public static void main(String args[]) {
        run(args);
    }
    
    //runs specified test
    public static void run(String[] arguments) { 
        ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<?> future = executor.submit(() -> {
            try { 
                FingerExercisesTests test = new FingerExercisesTests();
                //test.TESTCASENAME()
            	test.TestSumBetweenNegFiveFive();
            } 
			catch (Exception e) {
                e.printStackTrace();
                System.out.println("******************************\n" 
                + findmsg(e) + 
                "\n*******************************");
            }
			});

			try {
                Object returnValue = future.get(4, TimeUnit.SECONDS);             
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("******************************\n" 
                + findmsg(e) + 
                "\n*******************************");       
            } finally {
                executor.shutdownNow();
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
