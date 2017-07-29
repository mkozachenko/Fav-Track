package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;


/**
 * Created by symph on 20.07.2017.
 */
public class GetPropetries {

    private String MyShowsClientId, MyShowsSecret;
    private String user_pref_player;
    private String MPC_host, MPC_port;
    private String VLC_host, VLC_port, VLC_password, VLC_login;

    InputStream inputSecretStream;

    private void getSecretValues() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";
            inputSecretStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputSecretStream != null) {
                prop.load(inputSecretStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            //Extract secret credentials
            MyShowsClientId = prop.getProperty("MyShowsClientId");
            MyShowsSecret = prop.getProperty("MyShowsSecret");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputSecretStream.close();
        }
    }

    private void getUserValues() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "user.properties";
            inputSecretStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputSecretStream != null) {
                prop.load(inputSecretStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            //Extract user preferences
            user_pref_player = prop.getProperty("user_pref_player");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputSecretStream.close();
        }
    }

    private void getPlayerValues() throws IOException {
        try {
            getUserValues();
            Properties prop = new Properties();
            String propFileName = "user.properties";
            inputSecretStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputSecretStream != null) {
                prop.load(inputSecretStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            //Extract player setup
            switch (user_pref_player){
                case "MPC":
                    MPC_host = prop.getProperty("MPC_host");
                    MPC_port = prop.getProperty("MPC_port");
                    break;
                case "VLC":
                    VLC_host = prop.getProperty("VLC_host");
                    VLC_port = prop.getProperty("VLC_port");
                    VLC_password = prop.getProperty("VLC_password");
                    VLC_login = prop.getProperty("VLC_login");

                    break;
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputSecretStream.close();
        }
    }
//Secret getters
    public String getMyShowsClientId(){
        try {getSecretValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.MyShowsClientId;
    }
    public String getMyShowsSecret(){
        try {getSecretValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.MyShowsSecret;
    }
    /****/

    //User prefs getters
    public String getUserPref_Player(){
        try {getUserValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.user_pref_player;
    }

    //Players setup getters
    public String getMPC_host(){
        try {getPlayerValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.MPC_host;
    }
    public String getMPC_port(){
        try {getPlayerValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.MPC_port;
    }
    public String getVLC_host(){
        try {getPlayerValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.VLC_host;
    }
    public String getVLC_port(){
        try {getPlayerValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.VLC_port;
    }
    public String getVLC_password(){
        try {getPlayerValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.VLC_password;
    }
    public String getVLC_login(){
        try {getPlayerValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.VLC_login;
    }

}
