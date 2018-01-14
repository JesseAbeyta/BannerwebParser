import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    String pageSource;

    Parser(String pageSource) {
        this.pageSource = pageSource;
    }

    public String[][] parsePlainText() {
        pageSource = pageSource.substring(pageSource.indexOf("Instructor") + 12);
        pageSource = pageSource.substring(0, pageSource.indexOf("Total"));
        pageSource.replaceAll("\n", "");
        String[] words = pageSource.split("\t");
        for (String word : words) word.trim();
        String[][] schedule = new String[(int) Math.ceil(words.length / 12)][12];
        ArrayList<String> all = new ArrayList<String>(Arrays.asList(words));
        for (int row = 0; row < (int) Math.ceil(words.length / 12); row++) {
            for (int col = 0; col < 12; col++) {
                schedule[row][col] = (all.get(0).trim());
                all.remove(0);
            }
        }
        for (int row = 0; row < schedule.length; row++) {
            for (int col = 0; col < 12; col++) {
                if (schedule[row][col].trim().equals("")) {
                    schedule[row][col] = schedule[row - 1][col];
                }
            }
        }
        return schedule;
    }
}


