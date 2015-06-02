package User;

import Database.Loans;
import Time.TimeMachine;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.lang.System.out;

abstract public class User {

	protected String Type;
	protected int ID;
	protected String Name;
	protected int MaxDays;  // Numero maximo de dias por emprestimo
	protected int MaxLoans; // Numero maximo de emprestimos por usuario
	protected GregorianCalendar AllowedAt;  // Proxima data permitida de emprestimo

	public String getType(){ return Type; }
	public int getID(){ return ID; }
	public String getName(){ return Name; }
	public int getMaxDays() { return MaxDays; }
	public int getMaxLoans() { return MaxLoans; }
	public GregorianCalendar getAllowedAt() { return AllowedAt; }

	public boolean canLoan() {
		if (!AllowedAt.before(TimeMachine.CurrentDate())){
			out.println("Usuario bloqueado por possuir faltas de devolucao.");
			return false;
		}

		if (!(Loans.getInstance().CountLoansUser(ID) < getMaxLoans())){
			out.println("Numero maximo de emprestimos efetuados.");
			return false;
		}

		return true;
	}

	public void setAllowedAt (int fine) {

		AllowedAt.add(Calendar.DAY_OF_MONTH, - fine);

	}

	public void Print(){
		out.printf("Tipo:\t%s\nID:\t\t%d\nNome:\t%s\n", Type, ID, Name);
	}
}
