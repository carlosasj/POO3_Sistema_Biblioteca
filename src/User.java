abstract class User {

    protected String Type;
    protected int ID;
	protected String Name;
    protected int MaxDays;
	protected int MaxLoans;

	public String getType(){ return this.Type; }
	public int getID(){ return this.ID; }
	public String getName(){ return this.Name; }

	public void Print(){
		System.out.printf("Type:\t%s\nID:\t%d\nName:\t%s\n", Type, ID, Name);
	}
}
