abstract class User {
    
	private String Name;
	public Loan[] Loans;
	private int TotalLoans;
	private int MaxLoans;

	public String getName(){
		return Name;
	}

	public int getTotalLoans(){
		return TotalLoans;
	}

	public void newLoan(Loan l){

	}

	public void GiveBackLoan(Book b){

	}

	public void GiveBackLoan(String Title){

	}

}
