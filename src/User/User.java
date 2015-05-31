package User;

import Database.Loans;
import Time.TimeMachine;

import java.util.GregorianCalendar;

import static java.lang.System.out;

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

	public boolean canLoan() {
		if (!this.AllowedAt.before(TimeMachine.CurrentDate())){
			out.println("Usuario bloqueado por possuir faltas de devolucao.");
			return false;
		}

		if (!(Loans.getInstance().CountLoansUser(this.ID) < this.getMaxLoans())){
			out.println("Numero maximo de emprestimos efetuados.");
			return false;
		}

		return true;
	}

	public void Print(){
		out.printf("Tipo:\t%s\nID:\t\t%d\nNome:\t%s\n", Type, ID, Name);
	}
}
