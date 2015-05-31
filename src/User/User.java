package User;

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

    public boolean VerifyUser(GregorianCalendar curDate) {

        if (this.AllowedAt.before(curDate)) {
            return true;
        }
        return false;
    }

	public void Print(){
		System.out.printf("Type:\t%s\nID:\t%d\nName:\t%s\n", Type, ID, Name);
	}
}
