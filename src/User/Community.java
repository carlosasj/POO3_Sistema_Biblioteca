package User;

import java.util.Calendar;
import Time.TimeMachine;

public class Community extends User {

	public Community(int id, String name) {
		Type = "community";
		ID = id;
		Name = name;
		MaxDays = 15;
		MaxLoans = 2;
		AllowedAt = TimeMachine.CurrentDate();
		AllowedAt.add(Calendar.DAY_OF_MONTH, -1);
	}
}
