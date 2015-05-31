package User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import Time.TimeMachine;

public class Student extends User {

	public Student (int id, String name) {

		this.Type = "Stu";
		this.ID = id;
		this.Name = name;
		this.MaxDays = 15;
		this.MaxLoans = 4;
		this.AllowedAt = TimeMachine.CurrentDate();
		this.AllowedAt.add(Calendar.DAY_OF_MONTH, -1);
	}
}
