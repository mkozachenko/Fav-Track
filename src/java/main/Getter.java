package main;

import MYSHOWS.Shows;
import Parsers.MPC;
import Parsers.VLC;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by symph on 17.07.2017.
 */
public class Getter {
    private String showTitle, poster, seasonNumber, episodeNumber, episodeName, filename;
    private int duration, position;
    final static User32 user32 = User32.INSTANCE;
    final static Shell taskbarProgress = Shell.INSTANCE;
    public static String WTextFinal, wText, WTextPlayer;
    public static final String playerRemoveRegex = " - PotPlayer| - PotPlayer64",
                        formatRemoveRegex = ".avi|.mkv|.mov";

    public static void main (String[] args){
        new Getter().getDataNoAPI();
    }

    /*Charging up wht Windows tools*/
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
        interface WNDENUMPROC extends StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg);
        }
        boolean EnumWindows(User32.WNDENUMPROC lpEnumFunc, Pointer arg);
        int GetWindowTextA(Pointer hWnd, byte[] lpString, int nMaxCount);
    }

    public interface Shell extends StdCallLibrary {
        Shell INSTANCE = (Shell) Native.loadLibrary("user32", Shell.class);
        interface WNDENUMPROC extends StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg);
        }


    }
    /*Retrieve data from web-interfaces*/
    private void getDataMPC(){
        //new MPC().getData();
        filename = new MPC().getFilename();
        if(filename!=null) {
            switch (new Shows().searchByFile(filename)) {
                case "ok":
                    showTitle = Shows.title;
                    seasonNumber = Shows.seasonNumber;
                    episodeNumber = Shows.episodeNumber;
                    break;
                case "error":
                    new Parser().parseFilename(filename);
                    seasonNumber = new Parser().getSeason();
                    episodeNumber = new Parser().getEpisode();
                    showTitle = new Parser().getShowName();
                    break;
            }
            duration = new MPC().getDuration();
            position = new MPC().getPosition();
        }
    }
    /*SAME*/
    private void getDataVLC(){
        //new VLC().getData();
        filename = new VLC().getFilename();
        if(filename!=null) {
            switch (new Shows().searchByFile(filename)) {
                case "ok":
                    showTitle = Shows.title;
                    seasonNumber = Shows.seasonNumber;
                    episodeNumber = Shows.episodeNumber;
                    break;
                case "error":
                    if (new VLC().getEpisode() != null) {
                        showTitle = new VLC().getShowname();
                        episodeNumber = new VLC().getEpisode();
                        seasonNumber = new VLC().getSeason();
                    } else {
                        new Parser().parseFilename(filename);
                        showTitle = new Parser().getShowName();
                        episodeNumber = new Parser().getEpisode();
                        seasonNumber = new Parser().getSeason();
                    }
                    break;
            }
            duration = new VLC().getDuration();
            position = new VLC().getPosition();
        }
    }

    /*When no web-interface provided*/
    private void getDataNoAPI(){
        user32.EnumWindows(new User32.WNDENUMPROC() {
            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                wText = Native.toString(windowText);
                Pattern patternPlayer = Pattern.compile(".+ - PotPlayer|.+ - PotPlayer64",Pattern.CASE_INSENSITIVE);
                Matcher matchPlayer = patternPlayer.matcher(wText);
                while (matchPlayer.find()) {
                    WTextPlayer = matchPlayer.group();
                    System.out.println("Text: "+WTextPlayer);
                    WTextFinal = WTextPlayer.replaceAll(playerRemoveRegex,"").replaceAll(formatRemoveRegex,"");
                    System.out.println("SHOW: "+WTextFinal);
                }
                //IF UNKOWN
                /*Pattern patternWtext = Pattern.compile(".+\\.avi|.+\\.mkv|.+\\.mov",Pattern.CASE_INSENSITIVE);
                Matcher matchWtext = patternWtext.matcher(wText);
                while (matchWtext.find()) {
                    WTextFinal = matchWtext.group();
                    System.out.println("SHOW: "+WTextFinal);
                }*/
                return true;
            }
        }, null);

        new Parser().parseFilename(WTextFinal);
        seasonNumber = new Parser().getSeason();
        episodeNumber = new Parser().getEpisode();
        showTitle = new Parser().getShowName();
        //System.out.println("SHOW: "+showTitle+" "+seasonNumber+" "+episodeNumber);
        //POTplayer - total time: ctrl+shift+c /// current time: ctrl+shift+x


    }

    /*Define what method to use*/
    private void getData(){
        String prefPlayer = new GetPropetries().getUserPref_Player();
        switch(prefPlayer){
            case "MPC":
                getDataMPC();
                break;
            case "VLC":
                getDataVLC();
                break;
            case "POT":
                getDataNoAPI();
                break;
        }
    }

    /*Getting data*/
    public String getShowname(){
        getData();
        return this.showTitle;
    }
    public String getEpisode(){
        getData(); return this.episodeNumber;
    }
    public String getSeason(){
        getData(); return this.seasonNumber;
    }
    public int getDuration(){
        getData(); return this.duration;
    }
    public int getPosition(){
        getData(); return this.position;
    }
}
