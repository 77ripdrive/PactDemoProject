package utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class Utils {

    public  JSONObject readJsonFile (String path){

        InputStream is = Utils.class.getResourceAsStream(path);
        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + path);
        }
        JSONTokener tokener = new JSONTokener(is);
        return new JSONObject(tokener);
    }
}
