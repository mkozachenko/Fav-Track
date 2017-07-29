package Parsers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import main.GetPropetries;

import java.io.IOException;

/**
 * Created by symph on 13.07.2017.
 */
public class VLC {
    static String VLC_host, VLC_port, VLC_password, VLC_login;
    private static String title, seasonNumber, episodeNumber, filename;
    private static int length, time;
    public static DomElement DOMfilename, DOMtime, DOMlength, DOMepisode, DOMseason, DOMshow, DOMtitle;

    public static void getData (){
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
            } catch (NullPointerException e){/*System.err.println(e);*/}
            /*try {
                DOMshow = page.getFirstByXPath("//information/category[@name='meta']/info[@name='title']");
                title = DOMshow.getTextContent();
            } catch (NullPointerException e){/*System.err.println(e);}*/
            try {
                DOMseason = page.getFirstByXPath("//information/category[@name='meta']/info[@name='seasonNumber']");
                seasonNumber = DOMseason.getTextContent();
            } catch (NullPointerException e){/*System.err.println(e);*/}
            try {
                DOMtitle = page.getFirstByXPath("//information/category[@name='meta']/info[@name='showName']");
                title = DOMtitle.getTextContent();
            } catch (NullPointerException e){/*System.err.println(e);*/}
        } catch (IOException e){System.err.println(e);}
    }

    public int getPosition(){
        return this.time;
    }

    public int getDuration(){
        return this.length;
    }

    public String getFilename(){
        return this.filename;
    }
    public String getShowname(){
        return this.title;
    }
    public String getEpisode(){
        return this.episodeNumber;
    }
    public String getSeason(){
        return this.seasonNumber;
    }
}
