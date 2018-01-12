import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Fetcher {
    URL url;
    Fetcher(URL _url){
        this.url = _url;
    }
    public void login();
    public String getSite(){
        String content = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(url));
            String str;
            while ((str = in.readLine()) != null) {
                content +=str;
            }
            in.close();
        } catch (IOException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
