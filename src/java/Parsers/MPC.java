package Parsers;
/**
 * Created by symph on 13.07.2017.
 */

import main.*;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import static java.lang.Integer.*;

public class MPC {
    private static String MPC_host, MPC_port, filename;
    private static int position, duration;

    public void getData(){
        MPC_host = new GetPropetries().getMPC_host();
        MPC_port = new GetPropetries().getMPC_port();
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("http://"+MPC_host+":"+MPC_port+"/variables.html");
            filename = page.getElementById("file").getTextContent();
            position = parseInt((page.getElementById("position").getTextContent().substring(0,
                                page.getElementById("position").getTextContent().length()-3)));
            duration = parseInt((page.getElementById("duration").getTextContent().substring(0,
                    page.getElementById("duration").getTextContent().length()-3)));
        } catch (IOException e){System.err.println(e);}
    }

    public int getPosition(){
        getData();
        return this.position;
    }
    public int getDuration(){
        getData();
        return this.duration;
    }
    public String getFilename(){
        getData();
        return this.filename;
    }
}
