package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by symph on 17.07.2017.
 */
public class Parser {
    private static String showName, season, episode;
/*
    public void main (String[] args){
        MPC.getData();
        parseFilename(MPC.getFilename());
    }
*/
    public void parseFilename(String filename){
        Pattern patternSeason = Pattern.compile("(?i)s\\d+",Pattern.CASE_INSENSITIVE);
        Pattern patternEpisode = Pattern.compile("(?i)e\\d+",Pattern.CASE_INSENSITIVE);
        Matcher matchSeason = patternSeason.matcher(filename);
        Matcher matchEpisode = patternEpisode.matcher(filename);
        while (matchSeason.find()) {
            season = matchSeason.group().toLowerCase().replace("s", "");
            //System.out.println(season);
        }
        while (matchEpisode.find()) {
            episode = matchEpisode.group().toLowerCase().replace("e", "");
            //System.out.println(episode);
        }
        Pattern patternName = Pattern.compile("(?i)(s\\d+e\\d+.+)",Pattern.CASE_INSENSITIVE);
        Matcher matchName = patternName.matcher(filename);
        while (matchName.find()) {
            showName = filename.replace(matchName.group(), "");
            showName = showName.toLowerCase().replace("the", "");
            showName = showName.replace(".", " ").trim();
            //System.out.println(showName);
        }
    }

    public String getShowName() {
        return this.showName;
    }
    public String getSeason() {
        return this.season;
    }
    public String getEpisode() {
        return this.episode;
    }
}
