package User;

import java.util.Calendar;
import java.util.GregorianCalendar;

import Database.History;
import Time.TimeMachine;

public class Student extends User {

	public Student (int id, String name) {
		Type = "Stu";
		ID = id;
		Name = name;
		MaxDays = 15;
		MaxLoans = 4;
		AllowedAt = TimeMachine.CurrentDate();
		AllowedAt.add(Calendar.DAY_OF_MONTH, -1);

		History.getInstance().logAdd(this);
	}
}
