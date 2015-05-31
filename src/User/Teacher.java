package User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import Time.TimeMachine;

public class Teacher extends User {

    public Teacher (int id, String name) {

        this.Type = "Tea";
        this.ID = id;
        this.Name = name;
        this.MaxDays = 60;
        this.MaxLoans = 6;
        this.AllowedAt =  (GregorianCalendar) TimeMachine.CurrentDate().clone();
        this.AllowedAt.add(Calendar.DAY_OF_MONTH, -1);

    }
}
