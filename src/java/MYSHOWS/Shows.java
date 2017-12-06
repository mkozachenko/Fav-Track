package MYSHOWS;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//import sun.misc.Request;
//import javax.xml.ws.Response;

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
    private static Logger logger = Logger.getLogger(Shows.class.getName());
    private static FileHandler fh;

    public static void main(String[] args){
/*        showId = "DOctor.who.s01e05";
        new Shows().searchByFile(showId);*/
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
        //System.out.println(json);
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
        try {
            httpclient.start();
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
        //System.out.println(json);
        JsonObject jobject = new JsonParser().parse(json).getAsJsonObject();
        //System.out.println(jobject);
        if(jobject.has("result")){
            jobject = jobject.getAsJsonObject("result").getAsJsonObject("show");
            //System.out.println(jobject);
            title = jobject.get("title").toString().replace("\"", "");
            showId = jobject.get("id").toString();
            //poster = jobject.get("image").toString().replace("\"", "");
            jobject = jobject.getAsJsonObject("episodes").getAsJsonObject();
            Object[] episodeIdArray = jobject.keySet().toArray();
            jobject = jobject.get(episodeIdArray[0].toString()).getAsJsonObject();
            seasonNumber = jobject.get("seasonNumber").toString();
            episodeNumber = jobject.get("episodeNumber").toString();
            episodeId = jobject.get("id").toString();
            //logger(logger,"info",new Shows().getShowname()+"->"+new Shows().getSeason()+"->"+new Shows().getEpisode());
            return "ok";
        } else{
            responseCode = jobject.getAsJsonObject("error").get("code").toString();
            logger(logger,"error","Сериал не найден "+responseCode);
            return "error";
        }
    }

    public static void logger(Logger logger, String level, String message) {
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
    public String getShowId(){
        return this.showId;
    }
    public String getDescription(){
        return this.description;
    }
}
