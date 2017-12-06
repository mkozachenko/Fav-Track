package Parsers;
/**
 * Created by symph on 13.07.2017.
 */

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import main.GetPropetries;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.Integer.parseInt;

public class MPC {
    private static String MPC_host, MPC_port, filename;
    private static int position, duration;
    private static Logger logger = Logger.getLogger(MPC.class.getName());
    private static FileHandler fh;

    private void getData(){
        MPC_host = new GetPropetries().getMPC_host();
        MPC_port = new GetPropetries().getMPC_port();
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("http://"+MPC_host+":"+MPC_port+"/variables.html");
            filename = page.getElementById("file").getTextContent();
            position = parseInt((page.getElementById("position").getTextContent().substring(0,
                                page.getElementById("position").getTextContent().length()-3)));
            duration = parseInt((page.getElementById("duration").getTextContent().substring(0,
                    page.getElementById("duration").getTextContent().length()-3)));
        } catch (IOException e){System.err.println(e); logger(logger,"error", e.getMessage());}
    }

    public static void logger(Logger logger, String level, String message) {
        //Logger logger = Logger.getLogger();
        try {
            fh = new FileHandler("./logging.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (level) {
            case "info":
                logger.info("\n"+message+"\n");
                fh.close();
                break;
            case "error":
                logger.severe("\n"+message+"\n");
                fh.close();
                break;
        }
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
