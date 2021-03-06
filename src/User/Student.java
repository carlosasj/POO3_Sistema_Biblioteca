package User;

import java.util.Calendar;
import Time.TimeMachine;

public class Student extends User {

	public Student (int id, String name) {
		Type = "student";
		ID = id;
		Name = name;
		MaxDays = 15;
		MaxLoans = 4;
		AllowedAt = TimeMachine.CurrentDate();
		AllowedAt.add(Calendar.DAY_OF_MONTH, -1);
	}
}
