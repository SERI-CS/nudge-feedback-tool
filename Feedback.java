import com.fasterxml.jackson.databind.ObjectMapper;

import javax.tools.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.*;

public class Feedback {
    
    public static void main(String[] args) {
        compile();
        run(args);
    }

    public static void compile() {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> dc = new DiagnosticCollector<>();

            try (StandardJavaFileManager fm = compiler.getStandardFileManager(dc, null, null)) {
                File file = new File("Test.java");
                Iterable<? extends JavaFileObject> sources = fm.getJavaFileObjectsFromFiles(Arrays.asList(file));
                JavaCompiler.CompilationTask task = compiler.getTask(null, fm, dc, null, null, sources);
                task.call();
            }
            if (!dc.getDiagnostics().isEmpty()) {
                for (Diagnostic <? extends JavaFileObject> d: dc.getDiagnostics()) {
                    System.out.format("Line: %d, %s in %s \n",
                            d.getLineNumber(),
                            d.getMessage(null),
                            d.getSource().getName());
                }
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //runs student's main function with specified arguments
    public static void run(String[] arguments) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(() -> {
            try {
                Method mainMethod = Class.forName("Test").getMethod("main", String[].class);
                mainMethod.invoke(null, (Object) arguments);
            } catch (InvocationTargetException e) {
                e.getCause().printStackTrace();
                System.out.println("******************************\n" +
                        findmsg(e.getCause()) +
                        "\n*******************************");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("******************************\n"
                        + findmsg(e) +
                        "\n*******************************");
            }
        });

        try {
            future.get(10, TimeUnit.SECONDS);
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
        } catch (Exception e) {
            System.out.println("Exception");
        }
        return null;
    }
}