package Parsers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import java.io.IOException;

/**
 * Created by symph on 13.07.2017.
 */
public class VLC {
    public static String VLC_host, VLC_port, VLC_password;
    public static DomElement filename, time, length, episodeNumber, seasonNumber, showName, title;

    public static void getData (){
        VLC_host = "localhost";
        VLC_port = "8080";
        VLC_password = "1";

        try (final WebClient webClient = new WebClient()) {
            final XmlPage page = webClient.getPage("http://:"+VLC_password+"@"+VLC_host+":"+VLC_host+"/requests/status.xml");
            time = page.getFirstByXPath("//time");
            length = page.getFirstByXPath("//length");
            try {
                episodeNumber = page.getFirstByXPath("//information/category[@name='meta']/info[@name='episodeNumber']");
                System.out.println(episodeNumber.getTextContent());
            } catch (NullPointerException e){/*System.err.println(e);*/}
            try {
                showName = page.getFirstByXPath("//information/category[@name='meta']/info[@name='showName']");
                System.out.println(showName.getTextContent());
            } catch (NullPointerException e){/*System.err.println(e);*/}
            try {
                seasonNumber = page.getFirstByXPath("//information/category[@name='meta']/info[@name='seasonNumber']");
                System.out.println(seasonNumber.getTextContent());
            } catch (NullPointerException e){/*System.err.println(e);*/}
            try {
                title = page.getFirstByXPath("//information/category[@name='meta']/info[@name='title']");
                System.out.println(title.getTextContent());
            } catch (NullPointerException e){/*System.err.println(e);*/}
            try {
                filename = page.getFirstByXPath("//information/category[@name='meta']/info[@name='filename']");
                System.out.println(filename.getTextContent());
            } catch (NullPointerException e){/*System.err.println(e);*/}

            if (episodeNumber==null){System.out.println("no episode number");}
            System.out.println(time.getTextContent());
            System.out.println(length.getTextContent());
        } catch (IOException e){System.err.println(e);}
    }
}
