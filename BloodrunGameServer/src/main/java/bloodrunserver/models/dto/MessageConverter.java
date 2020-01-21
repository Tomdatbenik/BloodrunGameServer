package bloodrunserver.models.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

public class MessageConverter {
    public static Object FromGsonToObject(Object object, String gsonString){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();

        object = gson.fromJson(gsonString, object.getClass());

        return object;
    }

    public static String FromObjectToString(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();

        return gson.toJson(object);
    }

    public static String getToken(String message){
        JSONObject jsonObject = new JSONObject(message);

        return jsonObject.getString("Token");
    }
}
