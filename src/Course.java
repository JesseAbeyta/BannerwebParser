public class Course {
    private String CRN;
    private String CID;
    private String COURSE_NAME;
    private String CAMPUS;
    private String CREDITS;
    private String LEVEL;
    private Date START_DATE;
    private Date END_DATE;
    private char[] CLASS_DAYS;
    private Block CLASS_TIME;
    private String LOCATION;
    private String PROFESSOR;

    public String getCRN() {
        return CRN;
    }

    public void setCRN(String CRN) {
        this.CRN = CRN;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getCOURSE_NAME() {
        return COURSE_NAME;
    }

    public void setCOURSE_NAME(String COURSE_NAME) {
        this.COURSE_NAME = COURSE_NAME;
    }

    public String getCAMPUS() {
        return CAMPUS;
    }

    public void setCAMPUS(String CAMPUS) {
        this.CAMPUS = CAMPUS;
    }

    public String getCREDITS() {
        return CREDITS;
    }

    public void setCREDITS(String CREDITS) {
        this.CREDITS = CREDITS;
    }

    public String getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public Date getSTART_DATE() {
        return START_DATE;
    }

    public void setSTART_DATE(Date START_DATE) {
        this.START_DATE = START_DATE;
    }

    public Date getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(Date END_DATE) {
        this.END_DATE = END_DATE;
    }

    public char[] getCLASS_DAYS() {
        return CLASS_DAYS;
    }

    public void setCLASS_DAYS(char[] CLASS_DAYS) {
        this.CLASS_DAYS = CLASS_DAYS;
    }

    public Block getCLASS_TIME() {
        return CLASS_TIME;
    }

    public void setCLASS_TIME(Block CLASS_TIME) {
        this.CLASS_TIME = CLASS_TIME;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getPROFESSOR() {
        return PROFESSOR;
    }

    public void setPROFESSOR(String PROFESSOR) {
        this.PROFESSOR = PROFESSOR;
    }


    Course(String[] data) {
        this.CRN = data[0];
        this.CID = data[1];
        this.COURSE_NAME = data[2];

        this.CAMPUS = data[3];
        this.CREDITS = data[4];
        this.LEVEL = data[5];
        this.START_DATE = new Date(data[6]);
        this.END_DATE = new Date(data[7]);
        this.CLASS_DAYS = data[8].toCharArray();
        this.CLASS_TIME = new Block(data[9]);
        this.LOCATION = data[10];
        this.PROFESSOR = data[11];
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(this.COURSE_NAME);
        b.append("\t");
        b.append(this.START_DATE.toString());
        b.append("\t");
        b.append(this.END_DATE.toString());
        b.append("\t");
        b.append(this.CLASS_DAYS);
        b.append("\t");
        b.append(this.CLASS_TIME.toString());
        b.append("\t");
        b.append(this.LOCATION);
        b.append("\t");
        b.append(this.PROFESSOR);
        b.append("\t");

        return b.toString();

    }
public String getICSDays(){
        String a = "";
        for(Character c: CLASS_DAYS){
            if(c == 'M'){
                a+="MO,";
            }
            if(c == 'T'){
                a+="TU,";
            }
            if(c == 'W'){
                a+="WE,";
            }
            if(c == 'R'){
                a+="TH,";
            }
            if(c == 'F'){
                a+="FR,";
            }

        }
    if(a.charAt(a.length()-1)==','){
        a = a.substring(0,a.length()-1);
    }
        return a;

}
}

class Block {
    String startTime;
    String endTime;
    String sTime;
    String eTime;

    int startSeconds = 0;
    int startMinutes = 0;
    int startHours = 0;
    int endSeconds = 0;
    int endMinutes = 0;
    int endHours = 0;


    Block(String data) {
        data = data.replaceAll(":", "");
        startTime = data.split(" - ")[0];
        endTime = data.split(" - ")[1];

        startSeconds = 0;
        startMinutes = 0;
        if (startTime.contains("pm")) {
            startHours = Integer.parseInt(startTime.substring(0, startTime.length() - 3));
            startHours += 1200;
        } else {
            startHours = Integer.parseInt("0" + startTime.substring(0, startTime.length() - 3));
        }
        endSeconds = 0;
        endMinutes = 00;
        if (endTime.contains("pm")) {
            endHours = Integer.parseInt(endTime.substring(0, endTime.length() - 3));
            endHours += 1200;
        } else {
            endHours = Integer.parseInt(endTime.substring(0, endTime.length() - 3));
        }
    }

    public String getICSStartTime() {
        if (("T" + startHours + "" + startMinutes + "" + startSeconds).length() == 6) {
            return "T0" + startHours + "" + startMinutes + "" + startSeconds;
        } else {
            return "T" + startHours + "" + startMinutes + "" + startSeconds;
        }
    }

    public String getICSEndTime() {
        if (("T" + endHours + "" + endMinutes + "" + endSeconds).length() == 6) {
            return "T0" + endHours + "" + endMinutes + "" + endSeconds;
        } else {
            return "T" + endHours + "" + endMinutes + "" + endSeconds;
        }
    }

    public String toString() {
        return (startTime + " - " + endTime);
    }

    ;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

class Date {
    int year;
    String month;
    int day;

    Date(String text) {
        text = text.replaceAll(",", "");
        String[] data = text.split(" ");
        this.year = Integer.parseInt(data[2]);
        this.day = Integer.parseInt(data[1]);
        this.month = getMonth(data[0]);
    }

    public String getICSDate() {

        String date = "";
        date += this.year + "";
        if ((this.month + "").length() == 1) {
            date += ("0" + this.month);
        } else {
            date += this.month + "";
        }
        if ((this.day + "").length() == 1) {
            date += "0" + this.day;
        } else {
            date += this.day + "";
        }
        return date;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private String getMonth(String s) {
        switch (s) {
            case "Jan":
                return "01";
            case "Feb":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
            default:
                return "FUCK";
        }

    }

    public String toString() {
        return (this.year + " " + this.month + " " + this.day);
    }
}