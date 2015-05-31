package User;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Teacher extends User {

    public Teacher (int id, String name, GregorianCalendar curDate) {

        this.Type = "Tea";
        this.ID = id;
        this.Name = name;
        this.MaxDays = 60;
        this.MaxLoans = 6;
        this.AllowedAt =  new GregorianCalendar(curDate.get(Calendar.YEAR) ,curDate.get(Calendar.MONTH), curDate.get(Calendar.DAY_OF_MONTH));
        this.AllowedAt.add(Calendar.DAY_OF_MONTH, -1);

    }
}
