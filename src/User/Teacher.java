package User;

import java.util.Calendar;
import java.util.GregorianCalendar;

import Database.History;
import Time.TimeMachine;

public class Teacher extends User {

	public Teacher (int id, String name) {
		Type = "teacher";
		ID = id;
		Name = name;
		MaxDays = 60;
		MaxLoans = 6;
		AllowedAt = TimeMachine.CurrentDate();
		AllowedAt.add(Calendar.DAY_OF_MONTH, -1);
	}
}
