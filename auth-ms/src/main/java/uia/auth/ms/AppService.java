package uia.auth.ms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AppService {
	
	protected final Gson gson;

	protected AppService() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .create();
    }

	protected String apiPath(String path) {
    	return "/api/v1" + path;
    }
    
}
