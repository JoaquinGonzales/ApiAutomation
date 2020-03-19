import Entities.Crendetials;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.testng.AssertJUnit.assertEquals;

public class DeleteAndPost extends BaseClass {

    @BeforeMethod
    public void setUp()
    {
        client = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void clseResources() throws IOException {
        client.close();
        response.close();
    }

    @Test
    public void deleteRepository() throws IOException {
        HttpDelete deleteRequest = new HttpDelete("https://api.github.com/repos/JoaquinGonzales/removeme");
        deleteRequest.setHeader(HttpHeaders.AUTHORIZATION,"token "+ Crendetials.TOKEN);
        response = client.execute(deleteRequest);


        int actualStatusCode = response.getStatusLine().getStatusCode();

        assertEquals(actualStatusCode,204);
    }

    @Test
    public void createNewRepoStatus201() throws IOException {
        //Create an Http Post Entity
        HttpPost postRequest = new HttpPost("https://api.github.com/user/repos");

        //Set the Basic Credentials Auth header
        String auth = Crendetials.LOGIN.concat(":").concat(Crendetials.PASSWORD);
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);
        postRequest.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        //Define Json to Post and set as Entity

        String json = "{\"name\":\"removeme\"}";
        postRequest.setEntity(new StringEntity(json,ContentType.APPLICATION_JSON));

        response = client.execute(postRequest);
        int actualStatusCode = response.getStatusLine().getStatusCode();
        assertEquals(actualStatusCode,201);
    }

    @Test
    public void getTokenFromTodoLy() throws IOException {
        HttpGet get = new HttpGet("https://todo.ly/api/authentication/token.json");
        String auth = "joaquinjqn2@gmail.com:Pass1234";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);
        get.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        response = client.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();

        Assert.assertEquals(statusCode,200);
    }
}
