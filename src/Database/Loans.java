package Database;

import Book.Book;
import Loan.Loan;
import Time.TimeMachine;
import User.User;

import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Loans extends Database {

    private static Loans loansDB;
    private List<Loan> loans;
    private static GregorianCalendar curDate;


    // Singleton
    public static Loans getInstance() { return loansDB; }
    protected static Loans getInstance(String filename, GregorianCalendar curDate){
        if (loansDB == null){
            loansDB = new Loans(filename, curDate);
        }
        return loansDB;
    }

    public Loans (String filename, GregorianCalendar curDate) {
        this.curDate = curDate;
        this.nextID = 0;
        this.path = "loans.csv";
        this.loans = new LinkedList<Loan>();
        this.OpenFile(filename);
        this.ReadFile();

    }

    public void RegisterLoan(){
        out.println("--- Novo Emprestimo ---");
        out.println("Primeiro, selecione o usuario.");
        User user = Users.getInstance().Search();

        // Verifica se o usuario nao esta bloqueado para emprestimo
        if (user.VerifyUser(this.curDate) == false) {
            out.println("Usuário bloqueado por possuir faltas de devolução.");
            return;
        }

        // Verifica se o usuario tem menos emprestimos do que maximo permitido
        if(user.getMaxLoans() <= this.CountLoans(user.getID())) {
            out.println("Número máximo de empréstimos efetuados.");
            return;
        }

        out.println("Agora selecione o livro");
        Book book = Books.getInstance().Search();
        
        // Verifica se existe o livro para ser emprestado
        if (book == null) {
            out.println("Livro não cadastrado no sistema");
            return;
        }

        GregorianCalendar date = (GregorianCalendar) TimeMachine.CurrentDate().clone();
        GregorianCalendar expirationdate = (GregorianCalendar) TimeMachine.CurrentDate().clone();
        expirationdate.add(Calendar.DAY_OF_MONTH, user.getMaxDays());

        this.AddLoan(book.getID(), user.getID(), date, expirationdate);
    }

    // Utilizado no ReadFile
    private void AddLoan(int loanid, int bookid, int userid, String date, String expirationdate) {
        String[] split_date = date.split("/");
        String[] split_expiration = expirationdate.split("/");

        GregorianCalendar cal_date =
                new GregorianCalendar(Integer.parseInt(split_date[2]),
                        Integer.parseInt(split_date[1]),
                        Integer.parseInt(split_date[0]));

        GregorianCalendar cal_expiration =
                new GregorianCalendar(Integer.parseInt(split_expiration[2]),
                        Integer.parseInt(split_expiration[1]),
                        Integer.parseInt(split_expiration[0]));

        this.AddLoan(loanid, bookid, userid, cal_date, cal_expiration);
    }

    private void AddLoan(int loanid, int bookid, int userid, GregorianCalendar date, GregorianCalendar expirationdate) {
        Loan l = new Loan(loanid, bookid, userid, date, expirationdate);
        this.loans.add(l);
        Books.getInstance().FindByID(bookid).goLoan();
    }

    protected void AddLoan(int bookid, int userid, GregorianCalendar date, GregorianCalendar expirationdate) {
        this.AddLoan(this.nextID, bookid, userid, date, expirationdate);
        this.nextID++;
    }

    protected long CountLoans(int userId) {

        Stream<Loan> filter = loans.stream();

        filter.filter(l -> l.getUserID() == userId);

        return filter.count();

    }

    protected void ReturnLoan (int loanId) {


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
