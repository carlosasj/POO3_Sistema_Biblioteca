package Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static java.lang.System.out;

public class TimeMachine {

	private static TimeMachine timeMachine;
	private static GregorianCalendar currentDate;

	// Singleton
	public static TimeMachine getInstance(){
		if (timeMachine == null){
			timeMachine = new TimeMachine();
		}
		return timeMachine;
	}
	private TimeMachine() {
		setDate();
	}

	public void setDate() {
		Scanner scan = new Scanner(System.in);

		String d;
		do {
			out.println("Data [dd/mm/aaaa]: ");
			d = scan.nextLine();
		} while (!validate(d));

		//Armazena em um calendar a data digitada pelo usuario
		currentDate = strToCalendar(d);
	}

	public void setDate(GregorianCalendar date){
		currentDate = date;
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
						 + " Mes: " + (date.get(Calendar.MONTH)-1)
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
									 Integer.parseInt(split_date[1])-1,
									 Integer.parseInt(split_date[0]));
	}

	public static String CalendarToStr (GregorianCalendar date) {
		return date.get(Calendar.DAY_OF_MONTH) + "/"
				+(date.get(Calendar.MONTH)+1) + "/"
				+date.get(Calendar.YEAR);
	}

	public static boolean validate (String date){ return validate(date, "dd/MM/aaaa"); }
	public static boolean validate (String date, String format){

		if (date == null || format == null) return false;

		SimpleDateFormat form = new SimpleDateFormat(format);
		form.setLenient(false);

		try {
			form.parse(date);
		} catch (ParseException e){
			return false;
		}

		return true;
	}

}
