package User;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Student extends User {

    public Student (int id, String name, GregorianCalendar curDate) {

        this.Type = "Stu";
        this.ID = id;
        this.Name = name;
        this.MaxDays = 15;
        this.MaxLoans = 4;
        this.AllowedAt =  (GregorianCalendar) curDate.clone();
        this.AllowedAt.add(Calendar.DAY_OF_MONTH, -1);
    }
}
