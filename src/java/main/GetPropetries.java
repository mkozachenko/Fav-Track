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
    InputStream inputStream;

    private void getPropValues() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            MyShowsClientId = prop.getProperty("MyShowsClientId");
            MyShowsSecret = prop.getProperty("MyShowsSecret");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
    }

    public String getMyShowsClientId(){
        try {getPropValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.MyShowsClientId;
    }

    public String getMyShowsSecret(){
        try {getPropValues();}
        catch (IOException e) {e.printStackTrace();}
        return this.MyShowsSecret;
    }

}
