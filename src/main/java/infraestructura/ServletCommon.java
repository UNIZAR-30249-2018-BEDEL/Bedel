package infraestructura;

import java.lang.StringBuffer;
import java.io.BufferedReader;

import net.sf.json.JSONObject;

public class ServletCommon {

    private ServletCommon() {}

    public static JSONObject readJSON(BufferedReader bf) throws Exception {
        StringBuffer jb = new StringBuffer();
        String line;

        while ((line = bf.readLine()) != null) {
            jb.append(line);
        }

        return JSONObject.fromObject(jb.toString());
    }

}