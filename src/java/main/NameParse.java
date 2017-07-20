package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

public class NameParse {
    static public String fileName = "";
    static public String series = "";
    static public String season = "";
    static public String episode = "";
    static public String WTextFinal = "";
    static public String[] names;
    static public String name = "";
    final static User32 user32 = User32.INSTANCE;


    public static String getName() { //геттер
        FindWatcher();
        return NameParse.series;
    }
    public static String getEpisode() { //геттер
        FindWatcher();
        return NameParse.episode;
    }
    public static String getSeason() { //геттер
        FindWatcher();
        return NameParse.season;
    }
    //////
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        interface WNDENUMPROC extends StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg);
        }

        boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer arg);

        int GetWindowTextA(Pointer hWnd, byte[] lpString, int nMaxCount);
    }
    //////
    //public static void main(String[] args){
    public static void FindWatcher() {
		/**/
        user32.EnumWindows(new User32.WNDENUMPROC() {
            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                /*
                Pattern patternPlayer = Pattern.compile(".+vlc",Pattern.CASE_INSENSITIVE);
        		Matcher matchPlayer = patternPlayer.matcher(wText);
        		while (matchPlayer.find()) {
        			WTextFinal = matchWtext.group();
        		    System.out.println("Text: "+WTextFinal);
        		}
                */
                Pattern patternWtext = Pattern.compile(".+\\.avi|.+\\.mkv|.+\\.mov",Pattern.CASE_INSENSITIVE);
                Matcher matchWtext = patternWtext.matcher(wText);
                while (matchWtext.find()) {
                    WTextFinal = matchWtext.group();
                    System.out.println("Text: "+WTextFinal);
                }
                return true;
            }
        }, null);
        //
		/**/

        Pattern patternSeason = Pattern.compile("(?i)s\\d+",Pattern.CASE_INSENSITIVE);
        Pattern patternEpisode = Pattern.compile("(?i)e\\d+",Pattern.CASE_INSENSITIVE);
        Matcher matchSeason = patternSeason.matcher(WTextFinal);
        Matcher matchEpisode = patternEpisode.matcher(WTextFinal);
        while (matchSeason.find()) {
            season = matchSeason.group().toLowerCase().replace("s", "");
            System.out.println(season);
        }
        while (matchEpisode.find()) {
            episode = matchEpisode.group().toLowerCase().replace("e", "");
            System.out.println(episode);
        }

        Pattern patternName = Pattern.compile("(?i)(s\\d+e\\d+.+)",Pattern.CASE_INSENSITIVE);
        Matcher matchName = patternName.matcher(WTextFinal);
        while (matchName.find()) {
            series = WTextFinal.replace(matchName.group(), "");
            series = series.toLowerCase().replace("the", "");
            series = series.replace(".", " ");
            System.out.println(series);
        }

    }
}
