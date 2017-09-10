package main;

import Parsers.*;
import MYSHOWS.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by symph on 17.07.2017.
 */
public class Getter {
    private String showTitle, poster, seasonNumber, episodeNumber, episodeName, filename;
    private int duration, position;

    public static void main (String[] args){
        /*new VLC().getData();
        duration = new VLC().getDuration();
        System.out.println(duration);
        new MPC().getData();
        duration = new MPC().getDuration();
        System.out.println(duration);*/
        /*new Getter().getData();
        showTitle = new Shows().getShowname();
        episodeNumber = new Getter().getEpisode();
        System.out.println(showTitle);
        System.out.println(episodeNumber);*/
    }

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

    private void getData(){
        String prefPlayer = new GetPropetries().getUserPref_Player();
        switch(prefPlayer){
            case "MPC":
                getDataMPC();
                break;
            case "VLC":
                getDataVLC();
                break;
        }
    }

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
