package MYSHOWS;

import main.MyShowsOAuth;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    static HttpPost request = new HttpPost(url);


    public static void setShowStatus(String status) throws IOException {
        params = new StringEntity("{\"jsonrpc\": \"2.0\",\"method\": \"manage.SetShowStatus\",\"params\": {\"id\": 3882,\"status\": \""+status+"\"},\"id\": 1}");
        request.addHeader("content-type", "application/json");
        //request.addHeader("Accept", "application/json");
        request.addHeader("Authorization", "Bearer "+ MyShowsOAuth.getResponse());
        request.setEntity(params);
        response = client.execute(request);
        System.out.println(response.getStatusLine());
        json = EntityUtils.toString(response.getEntity());
        System.out.println(json);
/*
        JsonObject jsonObject = new
        JsonParser().parse(json).getAsJsonObject();
        System.out.println(jsonObject.get("jsonrpc").getAsString()); //John
        //
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jobject = jelement.getAsJsonObject();
        // jobject = jobject.getAsJsonObject("result");
        JsonArray jarray = jobject.getAsJsonArray("result");
        jobject = jarray.get(0).getAsJsonObject();
        title = jobject.get("title").toString().replace("\"", "");
        //poster = jobject.get("image").toString().replace("\"", "");
        System.out.println(title);*/
        ///
    }

    public static void rateShow(){

    }

    public static void checkEpisode(){

    }

    public static void unCheckEpisode(){

    }

    public static void rateEpisode(){

    }



}
