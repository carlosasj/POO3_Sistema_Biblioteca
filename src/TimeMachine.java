import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static java.lang.System.out;

public class TimeMachine {

    public static GregorianCalendar currentDate;

    public TimeMachine() {
        this.setDate();
    }

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
        currentDate = new GregorianCalendar(year, month, day);

    }

    public static void printDate() {

        out.println("Dia: " + currentDate.get(Calendar.DAY_OF_MONTH) + " Mês: " + currentDate.get(Calendar.MONTH) + " Ano: " + currentDate.get(Calendar.YEAR));

    }

}
