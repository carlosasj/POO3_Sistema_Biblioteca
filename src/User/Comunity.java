package User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import Time.TimeMachine;

public class Comunity extends User {

    public Comunity (int id, String name) {

        this.Type = "Com";
        this.ID = id;
        this.Name = name;
        this.MaxDays = 15;
        this.MaxLoans = 2;
        this.AllowedAt =  (GregorianCalendar) TimeMachine.CurrentDate().clone();
        this.AllowedAt.add(Calendar.DAY_OF_MONTH, -1);
    }
}
