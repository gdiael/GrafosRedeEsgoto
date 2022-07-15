import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MyUtil {

    private static String dblStrDefault = "0.0";

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

    private MyUtil(){}
}
