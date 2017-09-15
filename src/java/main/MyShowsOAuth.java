package main;

import com.google.api.client.auth.oauth2.PasswordTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
<<<<<<< HEAD
import java.net.UnknownHostException;
=======
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
>>>>>>> 96ab0c16ddf16fb353fdad30981cfcddb24255d9

/**
 * Created by symph on 09.07.2017.
 */
public class MyShowsOAuth {

    private static final String token = "https://myshows.me/oauth/token";
    private static String responseToken, error, errorResponse;
    private static Logger logger = Logger.getLogger(MyShowsOAuth.class.getName());
    private static FileHandler fh;

    public static String getToken(String username, String password)throws UnknownHostException{
        String clientId = new GetPropetries().getMyShowsClientId();
        String secret = new GetPropetries().getMyShowsSecret();
        String responceState=null;
        try {
            TokenResponse response =
                    new PasswordTokenRequest(new NetHttpTransport(), new JacksonFactory(),
                            new GenericUrl(token), username, password)
                            .setClientAuthentication(
                                    new BasicAuthentication(clientId, secret)).execute();
            System.out.println("Access token granted");
            responceState="ok";
            responseToken = response.getAccessToken();
        } catch (TokenResponseException e) {
            if (e.getDetails() != null) {
                System.err.println("Error: " + e.getDetails().getError());
                error = e.getDetails().getError();
                logger(logger,"error", error);
                if (e.getDetails().getErrorDescription() != null) {
                    System.err.println("Description: "+e.getDetails().getErrorDescription());
                    error = e.getDetails().getErrorDescription();
                    logger(logger,"error", error);
                }
                if (e.getDetails().getErrorUri() != null) {
                    System.err.println("ErrorURI "+e.getDetails().getErrorUri());
                    error = e.getDetails().getErrorUri();
                    logger(logger,"error", error);
                }
            } else {
                System.err.println("GetMessage: "+e.getMessage());
                error = e.getMessage();
                logger(logger,"error", error);
            }
            responceState = "fail";
        } catch (IOException e) {
            responceState = "fail";
            e.printStackTrace();
            logger(logger,"error", error);
        }
        return responceState;
    }

    public static String getResponse(){
        return responseToken;
    }

    public String getResponseNew(){
        return this.responseToken;
    }

    public static String errorHandle(){
        if (error != null) {
            switch (error) {
                case "401 Unauthorized":
                    errorResponse = "Неверный логин или пароль";
                    break;
                case "Missing parameters: \"username\" and \"password\" required":
                    errorResponse = "Неверный логин или пароль";
                    break;
/*                case "":
                    errorResponse = "OK";
                    break;*/
                default:
                    errorResponse = "Ошибка";
                    break;
            }
        } else {return errorResponse = "OK";}

        return errorResponse;
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
}
