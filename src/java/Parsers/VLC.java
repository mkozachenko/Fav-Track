package Parsers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import main.GetPropetries;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by symph on 13.07.2017.
 */
public class VLC {
    private static String VLC_host, VLC_port, VLC_password, VLC_login;
    private static String title, seasonNumber, episodeNumber, filename;
    private static int length, time;
    private static DomElement DOMfilename, DOMtime, DOMlength, DOMepisode, DOMseason, DOMtitle;
    private static Logger logger = Logger.getLogger(MPC.class.getName());
    private static FileHandler fh;

    private void getData (){
        VLC_host = new GetPropetries().getVLC_host();
        VLC_port = new GetPropetries().getVLC_port();
        VLC_password = new GetPropetries().getVLC_password();
        VLC_login = new GetPropetries().getVLC_login();

        try (final WebClient webClient = new WebClient()) {
            final XmlPage page = webClient.getPage("http://"+VLC_login+":"+VLC_password+"@"+VLC_host+":"+VLC_port+"/requests/status.xml");
            DOMtime = page.getFirstByXPath("//time");
            DOMlength = page.getFirstByXPath("//length");
            DOMfilename = page.getFirstByXPath("//information/category[@name='meta']/info[@name='filename']");
            filename = DOMfilename.getTextContent();
            time = Integer.parseInt(DOMtime.getTextContent());
            length = Integer.parseInt(DOMlength.getTextContent());
            try {
                DOMepisode = page.getFirstByXPath("//information/category[@name='meta']/info[@name='episodeNumber']");
                episodeNumber = DOMepisode.getTextContent();
            } catch (NullPointerException e){System.err.println(e);logger(logger,"error", e.getMessage());}
            try {
                DOMseason = page.getFirstByXPath("//information/category[@name='meta']/info[@name='seasonNumber']");
                seasonNumber = DOMseason.getTextContent();
            } catch (NullPointerException e){System.err.println(e);logger(logger,"error", e.getMessage());}
            try {
                DOMtitle = page.getFirstByXPath("//information/category[@name='meta']/info[@name='showName']");
                title = DOMtitle.getTextContent();
            } catch (NullPointerException e){System.err.println(e);logger(logger,"error", e.getMessage());}
        } catch (IOException e){System.err.println(e);logger(logger,"error", e.getMessage());}
    }

    private static void logger(Logger logger, String level, String message) {
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
        return this.time;
    }
    public int getDuration(){
        getData();
        return this.length;
    }
    public String getFilename(){
        getData();
        return this.filename;
    }
    public String getShowname(){
        getData();
        return this.title;
    }
    public String getEpisode(){
        getData();
        return this.episodeNumber;
    }
    public String getSeason(){
        getData();
        return this.seasonNumber;
    }
}
