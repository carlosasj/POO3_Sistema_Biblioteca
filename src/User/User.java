package User;

import static java.lang.System.out;

abstract public class User {

    protected String Type;
    protected int ID;
	protected String Name;
    protected int MaxDays;
	protected int MaxLoans;

	public String getType(){ return this.Type; }
	public int getID(){ return this.ID; }
	public String getName(){ return this.Name; }
	public int getMaxDays() { return this.MaxDays; }
	public int getMaxLoans() { return this.MaxLoans; }

	public void Print(){
		out.printf("Tipo:\t%s\nID:\t%d\nNome:\t%s\n", Type, ID, Name);
	}
}
