package User;

import Time.TimeMachine;

import static java.lang.System.out;
import java.util.GregorianCalendar;

abstract public class User {

	protected String Type;
	protected int ID;
	protected String Name;
	protected int MaxDays;
	protected int MaxLoans;
	protected GregorianCalendar AllowedAt;

	public String getType(){ return this.Type; }
	public int getID(){ return this.ID; }
	public String getName(){ return this.Name; }
	public int getMaxDays() { return this.MaxDays; }
	public int getMaxLoans() { return this.MaxLoans; }
	public GregorianCalendar getAllowedAt() { return this.AllowedAt; }

	public boolean VerifyUser() {
		return this.AllowedAt.before(TimeMachine.CurrentDate());
	}

	public void Print(){
		out.printf("Tipo:\t%s\nID:\t%d\nNome:\t%s\n", Type, ID, Name);
	}
}
