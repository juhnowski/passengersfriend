package com.passengersfriend;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {
   public String userName;
   public String url;
   public String password;

    public Settings(){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("settings.properties");

            prop.load(input);
            userName = prop.getProperty("userName");
            url = prop.getProperty("url");
            password = prop.getProperty("password");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
