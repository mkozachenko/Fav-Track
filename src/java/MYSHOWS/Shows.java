package MYSHOWS;

import Parsers.*;

import com.google.gson.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import sun.misc.Request;

import javax.xml.ws.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by symph on 09.07.2017.
 */
public class Shows {
    static StringEntity params;
    static HttpResponse response;
    static String json;
    static String url = "https://api.myshows.me/v2/rpc/";
    static HttpClient client = HttpClientBuilder.create().build();
    static CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
    static HttpPost request = new HttpPost(url);
    public static String title, poster, filename, responseCode, seasonNumber, episodeNumber, showId, titleOriginal, titleRu, episodeId,
            description;

    public static void main(String[] args){
        showId = "Game.of.Thrones.S07E04.rus.LostFilm.TV";
        new Shows().searchByFile(showId);
    }

    public String getById(String id){
        httpclient.start();
        try {
            params = new StringEntity("{\"jsonrpc\": \"2.0\"," +
                    "  \"method\": \"shows.GetById\"," +
                    "  \"params\": {" +
                    "    \"showId\": "+id+"," +
                    "    \"withEpisodes\": true" +
                    "  }, \"id\": 1}");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        try {
            response = client.execute(request);
            json = EntityUtils.toString(response.getEntity());
            request.removeHeaders("content-type");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(json);
        JsonObject jobject = new JsonParser().parse(json).getAsJsonObject();
        if(jobject.has("result")){
            jobject = jobject.getAsJsonObject("result");
            titleOriginal = jobject.get("titleOriginal").toString();
            description = jobject.get("description").toString();
            poster = jobject.get("image").toString().replace("\\", "").replace("\"", "");
            return "ok";
        } else{
            return "error";
        }
    }
    public String searchByFile(String filename){
        httpclient.start();
        try {
            params = new StringEntity("{\"jsonrpc\": \"2.0\",\"method\": \"shows.SearchByFile\",\"params\": " +
                                        "{\"file\": \""+ filename +
                                        "\"},\"id\": 1}");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        try {
            response = client.execute(request);
            json = EntityUtils.toString(response.getEntity());
            request.removeHeaders("content-type");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(json);
        JsonObject jobject = new JsonParser().parse(json).getAsJsonObject();
        if(jobject.has("result")){
            jobject = jobject.getAsJsonObject("result").getAsJsonObject("show");
            title = jobject.get("title").toString().replace("\"", "");
            showId = jobject.get("id").toString();
            //poster = jobject.get("image").toString().replace("\"", "");
            jobject = jobject.getAsJsonObject("episodes").getAsJsonObject();
            Object[] episodeIdArray = jobject.keySet().toArray();
            jobject = jobject.get(episodeIdArray[0].toString()).getAsJsonObject();
            seasonNumber = jobject.get("seasonNumber").toString();
            episodeNumber = jobject.get("episodeNumber").toString();
            episodeId = jobject.get("id").toString();
            //System.out.println(new Shows().getShowname()+"->"+new Shows().getSeason()+"->"+new Shows().getEpisode());
            return "ok";
        } else{
            /*
            responseCode = jobject.getAsJsonObject("error").get("code").toString();
            System.out.println("Сериал не найден "+responseCode);
            System.out.println(filename);
            */
            return "error";
        }
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
    public String getPoster(){
        return this.poster;
    }
    public String getEpisodeId(){
        return this.episodeId;
    }
    public String getDescription(){
        return this.description;
    }
}
