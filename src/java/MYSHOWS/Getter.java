package MYSHOWS;

import java.io.IOException;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Getter {
    static StringEntity params;
    static HttpResponse response;
    static String json;
    static String url = "https://api.myshows.me/v2/rpc/";
    static HttpClient client = HttpClientBuilder.create().build();
    static HttpPost request = new HttpPost(url);
    static String nameQuery;
    public static String title;
    public static String poster;

    public static void main (String[] args) throws IOException {
        PostJSON();
    }

    public static String getTitle() throws ClientProtocolException, IOException {
        PostJSON();
        return Getter.title;
    }
    public static String getPoster() throws ClientProtocolException, IOException {
        PostJSON();
        return Getter.poster;
    }

    public static void PostJSON() throws ClientProtocolException, IOException {

		/*
		 * params = new
		 * StringEntity("{\"jsonrpc\": \"2.0\",\"method\": \"shows.GetById\",\"params\": {"
		 * + "\"showId\": 395,\"withEpisodes\": true},\"id\": 1}");
		 */
        nameQuery = "Doctor Who";
        //nameQuery = NameParse.getName();
        System.out.println(nameQuery);
        params = new StringEntity("{\"jsonrpc\": \"2.0\",\"method\": \"shows.Search\",\"params\": {\"query\": \""
                + nameQuery + "\"},\"id\": 1}");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        System.out.println(request.toString());
        response = client.execute(request);
        json = EntityUtils.toString(response.getEntity());
        System.out.println(json);
        ///
        // JsonObject jsonObject = new
        /// JsonParser().parse(json).getAsJsonObject();
        // System.out.println(jsonObject.get("jsonrpc").getAsString()); //John
        //
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jobject = jelement.getAsJsonObject();
        // jobject = jobject.getAsJsonObject("result");
        JsonArray jarray = jobject.getAsJsonArray("result");
        jobject = jarray.get(0).getAsJsonObject();
        title = jobject.get("title").toString().replace("\"", "");
        poster = jobject.get("image").toString().replace("\"", "");
        System.out.println(title);
        ///
    }

    public static void postUser() throws ClientProtocolException, IOException {

		/*
		 * params = new
		 * StringEntity("{\"jsonrpc\": \"2.0\",\"method\": \"shows.GetById\",\"params\": {"
		 * + "\"showId\": 395,\"withEpisodes\": true},\"id\": 1}");
		 */
        nameQuery = "Doctor Who";
        //nameQuery = NameParse.getName();
        System.out.println(nameQuery);
        params = new StringEntity("{\"jsonrpc\": \"2.0\",\"method\": \"profile.Achievements\",\"params\": {\"\"},\"id\": 1}");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        response = client.execute(request);
        json = EntityUtils.toString(response.getEntity());
        System.out.println(json);
        ///
        // JsonObject jsonObject = new
        /// JsonParser().parse(json).getAsJsonObject();
        // System.out.println(jsonObject.get("jsonrpc").getAsString()); //John
        //
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jobject = jelement.getAsJsonObject();
        // jobject = jobject.getAsJsonObject("result");
        JsonArray jarray = jobject.getAsJsonArray("result");
        jobject = jarray.get(0).getAsJsonObject();
        title = jobject.get("title").toString().replace("\"", "");
        //poster = jobject.get("image").toString().replace("\"", "");
        System.out.println(title);
        ///
    }





}