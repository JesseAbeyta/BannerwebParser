import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;
import java.util.Scanner;

import static java.nio.file.Paths.get;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter your WPI email address");
        String WPIUsername = JOptionPane.showInputDialog("Enter your WPI Email Address");
        System.out.println("Enter your WPI password");
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String password = "";
        if (okCxl == JOptionPane.OK_OPTION) {
            password = new String(pf.getPassword());
        }
        String WPIPassword = password;
        System.setProperty("webdriver.chrome.driver", "C:/Users/hmarc/Desktop/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        String url = "https://bannerweb.wpi.edu/pls/prod/twbkwbis.P_WWWLogin";
        driver.get(url);
        driver.findElement(By.id("UserID")).sendKeys(WPIUsername);
        //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        driver.findElement(By.name("PIN")).sendKeys(WPIPassword);
        driver.findElement(By.xpath("/html/body/div[3]/form/table[1]/tbody/tr[3]/td/input")).click();
        WebDriverWait wait1 = new WebDriverWait(driver, 3);
        url = "https://bannerweb.wpi.edu/pls/prod/twbkwbis.P_GenMenu?name=bmenu.P_StuMainMnu";
        driver.get(url);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/span/map/p/table/tbody/tr[1]/td/table/tbody/tr/td[3]/a")).click();
        driver.findElement(By.xpath(" /html/body/div[3]/table[1]/tbody/tr[2]/td[2]/a")).click();
        url = " https://bannerweb.wpi.edu/pls/prod/twbkwbis.P_GenMenu?name=bmenu.P_RegMnu";
        driver.get(url);
        driver.findElement(By.xpath("/html/body/div[3]/table[1]/tbody/tr[10]/td[2]/a")).click();
        driver.findElement(By.xpath("/html/body/div[3]/form/input")).click();
        try {
            Files.delete(get("C://css/schedule.html"));
        } catch (NoSuchFileException e) {
        }
        FileWriter fw = new FileWriter("C:/css/schedule.html");
        fw.write(driver.getPageSource().toString());
        fw.close();
        driver.quit();
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage schedulePage = client.getPage("file://C://css/schedule.html");
        String pageContent = schedulePage.asText().replaceAll("\n", "\t");
        Parser p = new Parser(pageContent);
        String[][] matrix = p.parsePlainText();
        printMatrix(matrix);
        LinkedList<Course> courses = new LinkedList<Course>();
        for (int i = 0; i < matrix.length; i++)
            courses.add(new Course(matrix[i]));
        for (Course c : courses) {
            System.out.println(c.getSTART_DATE().getMonth());
            System.out.println(c.getSTART_DATE().getDay());
            System.out.println(c.getSTART_DATE().getICSDate());

        }

        createEvent e = new createEvent(courses);
        e.write();

    }

    public static void printMatrix(String[][] matrix) {

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.printf("%s", matrix[row][col] + ",\t");
            }
            System.out.println();

        }
    }

}