import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

import static java.nio.file.Paths.get;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String WPIUsername = JOptionPane.showInputDialog("Enter your WPI Email Address");
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String password = "";
        if (okCxl == JOptionPane.OK_OPTION) {
            password = new String(pf.getPassword());
        }
        String WPIPassword = password;
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println(s);
        System.out.println("Current relative path is: " + s);
        System.out.println("Loading Chrome");
        System.setProperty("webdriver.chrome.driver", s + "/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        System.out.println("Loaded Chrome");
        driver.manage().window().setSize(new Dimension(1024, 768));
        driver.manage().window().setPosition(new Point(0, 0));
        System.out.println("Navigating to WPI BannerWeb Home");
        String url = "https://bannerweb.wpi.edu/pls/prod/twbkwbis.P_WWWLogin";
        driver.get(url);
        System.out.println("Entering WPI Email");
        driver.findElement(By.id("UserID")).sendKeys(WPIUsername);
        System.out.println("Entering WPI Password");
        driver.findElement(By.name("PIN")).sendKeys(WPIPassword);
        System.out.println("Clicking Login");
        driver.findElement(By.xpath("/html/body/div[3]/form/table[1]/tbody/tr[3]/td/input")).click();
        System.out.println("Navigating to WPI BannerWeb Main Menu");
        url = "https://bannerweb.wpi.edu/pls/prod/twbkwbis.P_GenMenu?name=bmenu.P_StuMainMnu";
        driver.get(url);
        System.out.println("Clicking Student Services & Financial Aid");
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/span/map/p/table/tbody/tr[1]/td/table/tbody/tr/td[3]/a")).click();
        System.out.println("Clicking Registration");
        driver.findElement(By.xpath(" /html/body/div[3]/table[1]/tbody/tr[2]/td[2]/a")).click();
        System.out.println("Navigating to WPI BannerWeb Registration Menu");
        url = " https://bannerweb.wpi.edu/pls/prod/twbkwbis.P_GenMenu?name=bmenu.P_RegMnu";
        driver.get(url);
        System.out.println("Clicking Concise Student Schedule");
        driver.findElement(By.xpath("/html/body/div[3]/table[1]/tbody/tr[10]/td[2]/a")).click();
        System.out.println("Clicking Enter");
        driver.findElement(By.xpath("/html/body/div[3]/form/input")).click();
        try {
            Files.delete(get(s + "/schedule.html"));
        } catch (NoSuchFileException e) {
        }
        System.out.println("Downloading Page to schedule.html");
        FileWriter fw = new FileWriter(s + "/schedule.html");
        fw.write(driver.getPageSource().toString());
        fw.close();
        System.out.println("Closing Chrome");
        driver.quit();
        System.out.println("Parsing schedule.html to Plain Text");
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        HtmlPage schedulePage = client.getPage("file:/" + s + "/schedule.html");
        String pageContent = schedulePage.asText().replaceAll("\n", "\t");

        Parser p = new Parser(pageContent);
        System.out.println("Loading Parser");
        String[][] matrix = p.parsePlainText();
        System.out.println("Creating Course Matrix");
        System.out.println("Done Parsing!");
        System.out.println("Adding Courses to Matrix");
        LinkedList<Course> courses = new LinkedList<Course>();
        for (int i = 0; i < matrix.length; i++)
            courses.add(new Course(matrix[i]));
        System.out.println("Writing .ics file to "+s);
        createEvent e = new createEvent(courses);
        e.write();
        printMatrix(matrix);
        System.out.println("Done!");

    }
//used for debug.
    public static void printMatrix(String[][] matrix) {

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.printf("%s", matrix[row][col] + ",\t");
            }
            System.out.println();

        }
    }

}