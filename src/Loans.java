import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Loans extends Database implements FileInterface {

    public List<Loan> loans;

    public Loans (String filename) {

        this.path = "loans.csv";
        this.loans = new LinkedList<Loan>();
        this.OpenFile(filename);
    }

    public void AddLoan(int id, int bookid, int userid, String date, String expirationdate) {

        Loan l = new Loan(id, bookid, userid, date, expirationdate);
    }

    public void ReadFile() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(this.path));
        String line = null;
        String splitBy = ",";

        if ((line = br.readLine()) != null) {
            this.nextID = Integer.parseInt(line);
            br.readLine();
        }

        while ((line = br.readLine()) != null) {

            String[] loanData = line.split(splitBy);
            int id = Integer.parseInt(loanData[0]);
            int bookid = Integer.parseInt(loanData[1]);
            int userid = Integer.parseInt(loanData[2]);
            String date = loanData[3];
            String expirationdate = loanData[4];

            this.AddLoan(id, bookid, userid, date, expirationdate);

        }

    }
    public void WriteFile() throws IOException {

    }
}
