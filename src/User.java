import java.util.Scanner;

abstract class User {

    public String Type;
    public int ID;
	public String Name;
	public int MaxLoans;

    public void RegisterUser () {

        Scanner scan = new Scanner(System.in);

        System.out.println("Name: ");
        String Name = scan.nextLine();
    }

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
