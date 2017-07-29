package MYSHOWS;

import Parsers.*;

import com.google.gson.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * Created by symph on 09.07.2017.
 */
public class Shows {
    static StringEntity params;
    static HttpResponse response;
    static String json;
    static String url = "https://api.myshows.me/v2/rpc/";
    static HttpClient client = HttpClientBuilder.create().build();
    static HttpPost request = new HttpPost(url);
    public static String title, poster, filename, responseCode, seasonNumber, episodeNumber, episodeName;

    public static void main(String[] args){
        new Shows().searchByFile("");
    }

    public static void getById(){

    }

    public static void search(){

    }

    public String searchByFile(String filename){
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(json);
        JsonObject jobject = new JsonParser().parse(json).getAsJsonObject();
        if(jobject.has("result")){
            jobject = jobject.getAsJsonObject("result").getAsJsonObject("show");
            title = jobject.get("title").toString().replace("\"", "");
            poster = jobject.get("image").toString().replace("\"", "");
            jobject = jobject.getAsJsonObject("episodes").getAsJsonObject();
            Object[] episodeId = jobject.keySet().toArray();
            jobject = jobject.get(episodeId[0].toString()).getAsJsonObject();
            seasonNumber = jobject.get("seasonNumber").toString().replace("\"", "");
            episodeNumber = jobject.get("episodeNumber").toString().replace("\"", "");
            episodeName = jobject.get("title").toString().replace("\"", "");
            return "ok";
        } else{
            /*
            responseCode = jobject.getAsJsonObject("error").get("code").toString();
            System.out.println("Сериал не найден "+responseCode);
            System.out.println(filename);*/
            return "error";
        }
    }

}
