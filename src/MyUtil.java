public class MyUtil {
    
    public static <T> T coalesce(T one, T two)
    {
        return one != null ? one : two;
    }

    private MyUtil(){}
}
