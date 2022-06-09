
public class Error {
   private String err;
   private String msg; 

   public Error() {

   }
   
   public Error(String err, String msg) {
        this.err = err;
        this.msg = msg;
   }

   public void setErr(String err) {
       this.err = err;
   }

   public void setMsg(String msg) {
    this.msg = msg;
   }

   public String getErr() {
       return err;
   }

   public String getMsg() {
       return msg;
   }
}
