package User;

import java.util.Calendar;
import java.util.GregorianCalendar;

import Database.History;
import Time.TimeMachine;

public class Comunity extends User {

	public Comunity (int id, String name) {
		Type = "community";
		ID = id;
		Name = name;
		MaxDays = 15;
		MaxLoans = 2;
		AllowedAt = TimeMachine.CurrentDate();
		AllowedAt.add(Calendar.DAY_OF_MONTH, -1);
	}
}
