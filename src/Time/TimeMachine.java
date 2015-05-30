package Time;

import java.util.GregorianCalendar;
import java.util.Scanner;

import static java.lang.System.out;

public class TimeMachine {

    private static GregorianCalendar currentDate;

    public static void setDate() {

        Scanner scan = new Scanner(System.in);
        String splitBy = "/";

        out.println("Data[dd/mm/aaaa]: ");
        String d = scan.nextLine();

        //Mudanca da data de string para Calendar
        String[] date = d.split(splitBy);
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        //Armazena em um calendar a data digitada pelo usuario
        GregorianCalendar loanDate = new GregorianCalendar(year, month, day);
    }

    public static GregorianCalendar CurrentDate(){
        return currentDate;
    }

}
