import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static java.lang.System.out;

public class Loans extends Database implements FileInterface {

    public List<Loan> loans;

    public Loans (String filename) {

        this.nextID = 0;
        this.path = "loans.csv";
        this.loans = new LinkedList<Loan>();
        this.OpenFile(filename);
    }

    private void AddLoan(int id, int bookid, int userid, String date, String expirationdate) {

        //Loan l = new Loan(id, bookid, userid, date, expirationdate);

        //this.loans.add(l);
    }

    public void AddLoan(int bookid, int userid, GregorianCalendar date, GregorianCalendar expirationdate) {

        Loan l = new Loan(this.nextID, bookid, userid, date, expirationdate);

        Book b = null;

        this.loans.add(l);

        this.nextID++;
        this.loans.add(l);

    }

    public void ReadFile(){

        this.OpenReader();

        String line;
        String splitBy = ",";

        try {
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
        } catch (IOException e){
            out.println("Erro na leitura do arquivo.");
            e.printStackTrace();
        }
    }

    public void WriteFile(){
        OpenWriter();
        String SEPARATOR = ",";
        String ENDLINE = "\n";
        String HEADER = "ID,BookID,UserID,Date,ExpirarionDate";

        try {
            fw.append(Integer.valueOf(this.nextID).toString());
            fw.append(ENDLINE);
            fw.flush();

            fw.append(HEADER);
            fw.append(ENDLINE);
            fw.flush();

            for (Loan l : loans) {
                fw.append(Integer.valueOf(l.getID()).toString());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(l.getBookID()).toString());
                fw.append(SEPARATOR);

                fw.append(Integer.valueOf(l.getUserID()).toString());
                fw.append(SEPARATOR);

                fw.append(l.getDate().toString());
                fw.append(SEPARATOR);

                fw.append(l.getExpirationDate().toString());
                fw.append(ENDLINE);
                fw.flush();
            }
        } catch (IOException e){
            out.println("Erro na escrita do arquivo.");
            e.printStackTrace();
        }
    }
}
