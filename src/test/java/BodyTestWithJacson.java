import Entities.NotFound;
import Entities.RateLimit;
import Entities.Token;
import Entities.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.testng.AssertJUnit.assertEquals;


public class BodyTestWithJacson extends BaseClass{
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
    public void returnCorrectLogin() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/users/JoaquinGonzales");
        response = client.execute(get);

        User user = ResponseUtils.unmarshallGeneric(response, User.class);
        assertEquals(user.getLogin(), "JoaquinGonzales");
    }

    @Test
    public void returnCorrectId() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/users/JoaquinGonzales");
        response = client.execute(get);

        User user = ResponseUtils.unmarshallGeneric(response, User.class);
        assertEquals(user.getId(), 13631159);
    }


    @Test
    public void notFound() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/notexistingendoint");
        response = client.execute(get);

        NotFound notfound = ResponseUtils.unmarshallGeneric(response, NotFound.class);
        assertEquals(notfound.getMessage(), "Not Found");
    }

    @Test
    public void correctRateLimitsAreSet() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/rate_limit");
        response = client.execute(get);

        RateLimit rateLimit = ResponseUtils.unmarshallGeneric(response,RateLimit.class);
        assertEquals(rateLimit.getCoreLimit(),60);
        assertEquals(rateLimit.getSearchLimit(),"10");
    }

    @Test
    public void getTokenFromTodoLy() throws IOException {
        HttpGet get = new HttpGet("https://todo.ly/api/authentication/token.json");
        String auth = "joaquinjqn2@gmail.com:Pass1234";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);
        get.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

        response = client.execute(get);
        String jsonBody = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = new JSONObject(jsonBody);

        String token = (String) jsonObject.get("TokenString");

        assertEquals(token,"2bf0ce81d1ad4d6e8b24f11849b6329b");

        int statusCode = response.getStatusLine().getStatusCode();

        Assert.assertEquals(statusCode,200);
    }
}
