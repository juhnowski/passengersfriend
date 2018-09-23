package com.passengersfriend;
import java.io.*;
import java.util.Properties;

public class Session {
    public long currentId;
    public long count;
    public long maxId;
    public int idx = 0;
    public int state = State.STARTED;

    public Session() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("states.properties");

            prop.load(input);
            currentId = Long.parseLong(prop.getProperty("currentId"));
            count = Long.parseLong(prop.getProperty("count"));
            maxId = Long.parseLong(prop.getProperty("maxId"));
            idx = Integer.parseInt(prop.getProperty("idx"));
            state = Integer.parseInt(prop.getProperty("state"));
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

    public void save(){
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("states.properties");

            prop.setProperty("currentId", ""+currentId);
            prop.setProperty("count", ""+count);
            prop.setProperty("maxId", ""+maxId);
            prop.setProperty("idx", ""+idx);
            prop.setProperty("state", ""+state);

            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
