package MYSHOWS;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import main.MyShowsOAuth;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.Arrays;

/**
 * Created by symph on 09.07.2017.
 */
public class Manage {
    static StringEntity params;
    static HttpResponse response;
    static String json;
    static String url = "https://api.myshows.me/v2/rpc/";
    static HttpClient client = HttpClientBuilder.create().build();
    static CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
    static HttpPost request = new HttpPost(url);
    public static String title, poster, filename, responseCode, seasonNumber, episodeNumber, showId, titleOriginal, titleRu,
            description;

    public static void main(String[] args){
        MyShowsOAuth.getToken("","");
        new Manage().rateEpisode("16177086","4");
        new Manage().rateEpisode("16177087","1");
        new Manage().rateEpisode("16177088","2");
    }


    public static void setShowStatus(){

    }

    public static void rateShow(){

    }

    public static void checkEpisode(){

    }

    public static void unCheckEpisode(){

    }

    public String rateEpisode(String episodeId, String rating){
        httpclient.start();
        try {
            params = new StringEntity("{\"jsonrpc\": \"2.0\"," +
                    "  \"method\": \"manage.RateEpisode\"," +
                    "  \"params\": {" +
                    "    \"id\": "+episodeId+"," +
                    "    \"rating\": "+rating +
                    "  }, \"id\": 1}");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.addHeader("content-type", "application/json");
        request.addHeader("Authorization", "Bearer "+ new MyShowsOAuth().getResponseNew());
        request.setEntity(params);

        try {
            response = client.execute(request);
            json = EntityUtils.toString(response.getEntity());
            request.removeHeaders("Authorization");
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
        if(jobject.get("result").toString()=="true"){
            return "true";
        }else{
            return "false";
        }
    }



}
