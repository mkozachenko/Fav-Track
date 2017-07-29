package main;

import Parsers.*;
import MYSHOWS.*;

/**
 * Created by symph on 17.07.2017.
 */
public class Getter {
    public String showTitle, poster, seasonNumber, episodeNumber, episodeName, filename;

    void getDataMPC(){
        MPC.getData();
        filename = new MPC().getFilename();
        switch(new Shows().searchByFile(filename)){
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
    }

    void getDataVLC(){
        VLC.getData();
        filename = new VLC().getFilename();
        if(new Shows().searchByFile(filename)=="ok") {
            showTitle = Shows.title;
            seasonNumber = Shows.seasonNumber;
            episodeNumber = Shows.episodeNumber;
        } else {
            if(new VLC().getEpisode()!=null){
                showTitle = new VLC().getShowname();
                episodeNumber = new VLC().getEpisode();
                seasonNumber = new VLC().getSeason();
            } else {
                new Parser().parseFilename(filename);
                seasonNumber = new Parser().getSeason();
                episodeNumber = new Parser().getEpisode();
                showTitle = new Parser().getShowName();
            }
        }
    }

    void getByMPC(){

    }

    public void getData(){
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
        return this.showTitle;
    }
    public String getEpisode(){
        return this.episodeNumber;
    }
    public String getSeason(){
        return this.seasonNumber;
    }

}
