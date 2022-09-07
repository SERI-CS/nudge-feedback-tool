import com.fasterxml.jackson.databind.*;
import java.nio.file.*;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FeedbackTestCase {
    
    public static void main(String[] args) {
        run(args);
    }

    //runs tests in specified file
    public static void run(String[] arguments) { 
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            try { 
                Class<Test> testObj = CLASSNAME.class;
                CLASSNAME test = new CLASSNAME();
                for (Method method : testObj.getMethods()) {
                    method.invoke(test, new Object[0]);
                }
            } catch (Exception e) {
                e.getCause().printStackTrace();
                System.out.println("******************************\n" 
                + findmsg(e.getCause()) + 
                "\n*******************************");
            }
        });

        try {
            future.get(8, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
            System.out.println("******************************\n"
                    + findmsg(e.getCause()) +
                    "\n*******************************");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("******************************\n"
                    + findmsg(e) +
                    "\n*******************************");
        } finally {
            executor.shutdownNow();
            System.exit(0);
        }
    }
    
    public static String findmsg(Throwable input) {
        String content = "";
        Error[] errors;
        try {
            content = new String(Files.readAllBytes(Paths.get("ErrorMap.json")));
            ObjectMapper mapper = new ObjectMapper();
            errors = mapper.readValue(content, Error[].class);
        
            String errorName = input.getClass().getCanonicalName();
            for (Error error : errors) {
                if (errorName.contains(error.getErr())) {
                    return error.getMsg();
                }
            }
        } catch(Exception e) {
            System.out.println("Exception");
        }
        return null;
    }
    
}