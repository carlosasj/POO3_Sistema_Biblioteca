package User;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Comunity extends User {

    public Comunity (int id, String name, GregorianCalendar curDate) {

        this.Type = "Com";
        this.ID = id;
        this.Name = name;
        this.MaxDays = 15;
        this.MaxLoans = 2;
        this.AllowedAt =  new GregorianCalendar(curDate.get(Calendar.YEAR) ,curDate.get(Calendar.MONTH), curDate.get(Calendar.DAY_OF_MONTH));
        this.AllowedAt.add(Calendar.DAY_OF_MONTH, -1);
    }
}
