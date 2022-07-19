import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MyUtil {

    private static String dblStrDefault = "0.0";
    private static Logger myLogger;

    // retorno o primeiro objeto, caso ele não seja nulo
    public static <T> T coalesce(T one, T two)
    {
        return one != null ? one : two;
    }

    public static Double parseDbl(String numStr){
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            Number number = format.parse(coalesce(numStr, dblStrDefault));
            return number.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void printMsg(String msg, Level level, boolean useLog) {
        if(myLogger == null) {
            myLogger = Logger.getLogger("myUtil");
        }
        if(useLog) {
            msg = "\n" + msg;
            myLogger.log(level, msg);
        } else {
            System.out.println(msg); //NOSONAR
        }
    }

    private MyUtil(){}
}
