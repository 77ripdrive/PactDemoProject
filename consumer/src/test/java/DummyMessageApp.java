import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class DummyMessageApp {
    private String url;

    public DummyMessageApp(String url) {
        this.url = url;
    }

    public String getClients(String path) throws IOException {
        var response = getResponse(path);
        return getEntityAsString(response);
    }

    private String getEntityAsString(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    public HttpResponse getResponse(String path) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        var request = new HttpGet(url + path);
        return client.execute(request);
    }

    public HttpResponse postResponseAddNewUser(String path, String fullName, String userName) throws IOException {
        var user = new JSONObject();
        user.put("fullName", fullName);
        user.put("userName", userName);

        CloseableHttpClient client = HttpClientBuilder.create().build();
        var request = new HttpPost(url + path);
        request.setEntity(new StringEntity(user.toString(), ContentType.APPLICATION_JSON));
        return client.execute(request);
    }

}
