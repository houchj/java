import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static void main(String[] args) {

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse("2015/12/31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = new SimpleDateFormat("YYYY/MM/dd").format(date);   //Noncompliant; yields '2016/12/31'
        System.out.println(result);


    }
}
