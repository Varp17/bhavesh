package administratorpkg;

import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

public class dategiver {
    public static String getdate() {
        String day = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        String year = new SimpleDateFormat("YYYY", Locale.getDefault()).format(new Date());
        int monthInt = Integer.parseInt(month);

        String monthName; // Variable to hold the month name abbreviation
        String date;

        switch (monthInt) {
            case 1:
                // January
                monthName = "Jan";
                break;
            case 2:
                // February
                monthName = "Feb";
                break;
            case 3:
                // March
                monthName = "Mar";
                break;
            case 4:
                // April
                monthName = "Apr";
                break;
            case 5:
                // May
                monthName = "May";
                break;
            case 6:
                // June
                monthName = "Jun";
                break;
            case 7:
                // July
                monthName = "Jul";
                break;
            case 8:
                // August
                monthName = "Aug";
                break;
            case 9:
                // September
                monthName = "Sep";
                break;
            case 10:
                // October
                monthName = "Oct";
                break;
            case 11:
                // November
                monthName = "Nov";
                break;
            case 12:
                // December
                monthName = "Dec";
                break;
            default:
                // Default case, if the month is not in the range 1-12
                // You can add code here for handling unexpected cases
                monthName = ""; // or any default value indicating an error
                break;
        }
        return date=day+" "+monthName+" "+year;


    }
}
