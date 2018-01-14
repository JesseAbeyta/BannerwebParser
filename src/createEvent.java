import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;

public class createEvent {

    private String version = "VERSION:2.0\r\n";
    private String prodid = "PRODID://Elara/lofy/tanare/delp/314sum2015// \r\n";
    private String calBegin = "BEGIN:VCALENDAR \r\n";
    private String calEnd = "END:VCALENDAR \r\n";
    private String eventBegin = "BEGIN:VEVENT \r\n";
    private String eventEnd = "END:VEVENT \r\n";
    private String location = "LOCATION:VLOCATION \r\n";

    private LinkedList<Course> courses;

    public createEvent(LinkedList<Course> courses) {
        this.courses = courses;
    }

    public String getCN() {
        return "John Doe";
    }

    public String getGmail() {
        return "johndoe@gmail.com";
    }

    public String getStartTime() {
        return "19970714T170000Z";
    }

    public String getEndTime() {
        return "19970715T035959Z";
    }

    public String getCourseName() {
        return "Bastille Day";
    }

    public String getLocation() {
        return "France";
    }

    public void write() {


        try {
            PrintWriter writer = new PrintWriter("schedule.ics", "UTF-8");

            writer.println("BEGIN:VCALENDAR");
            writer.println("PRODID:-//Google Inc//Google Calendar 70.9054//EN");
            writer.println("VERSION:2.0");

            //writer.println("USER:" );
            for (Course c : courses) {
                writer.println("BEGIN:VEVENT");
                writer.println("UID:uid" + new Random().nextInt(10000000) + "@example" + new Random().nextInt(100000000) + ".com");
                writer.println("LOCATION:" + c.getLOCATION());
                writer.println("DTSTAMP:20180101T120000Z");
                writer.println("RRULE:FREQ=WEEKLY;UNTIL="+c.getEND_DATE().getICSDate()+";BYDAY="+c.getICSDays());
                writer.println("DTSTART:" + c.getSTART_DATE().getICSDate() + "" + c.getCLASS_TIME().getICSStartTime());
                writer.println("DTEND:" + c.getSTART_DATE().getICSDate() + "" + c.getCLASS_TIME().getICSEndTime());
                writer.println("SUMMARY:" + c.getCOURSE_NAME());
                writer.println("END:VEVENT");
            }
            writer.println("END:VCALENDAR");

            writer.close();
        } catch (Exception e) {
            System.out.println("Everything is FINE.");
            e.printStackTrace();
        }
    }
}