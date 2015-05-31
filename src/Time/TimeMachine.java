package Time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static java.lang.System.out;

public class TimeMachine {

    private static GregorianCalendar currentDate;

    public TimeMachine() {
        this.setDate();
    }

    public void setDate() {

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

    public static void printDate(String format) {
        printDate(currentDate, format);
    }

    public static void printDate(GregorianCalendar date, String format) {
        switch (format){
            case "dd/mm/aaaa":
                out.println(date.get(Calendar.DAY_OF_MONTH)
                    + "/" + date.get(Calendar.MONTH)
                    + "/" + date.get(Calendar.YEAR));
                break;

            case "d:m:a":
                out.println("Dia: " + date.get(Calendar.DAY_OF_MONTH)
                         + " Mes: " + date.get(Calendar.MONTH)
                         + " Ano: " + date.get(Calendar.YEAR));
                break;
        }
    }

    public static GregorianCalendar CurrentDate(){
        return (GregorianCalendar) currentDate.clone();
    }

    public static GregorianCalendar strToCalendar (String date) {
        String[] split_date = date.split("/");

        return new GregorianCalendar(Integer.parseInt(split_date[2]),
                                     Integer.parseInt(split_date[1]),
                                     Integer.parseInt(split_date[0]));
    }

}
