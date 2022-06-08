public class Feedback {
    
    private static String error;
    
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
            error = e.getClass().getCanonicalName();
            System.out.print(error);
          }
    }
    
    // public static String findmsg(String e) {
    //     String msg = "unidentified error - go to office hours :/";
    //     look up e in map, ret corresponding msg
    //     return msg;
    // }
    
}