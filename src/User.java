import java.util.Scanner;

abstract class User {

    public String Type;
    public int ID;
	public String Name;
    public int MaxDays;
	public int MaxLoans;

	public String getName(){
		return Name;
	}

	public void newLoan(Loan l){

	}

	public void GiveBackLoan(Book b){

	}

	public void GiveBackLoan(String Title){

	}

    public boolean SetDataUser(int ID) {
        return false;
    }
}
