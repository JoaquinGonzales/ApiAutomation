import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class Get200StatusTest extends BaseClass {

    CloseableHttpClient client;
    CloseableHttpResponse response;
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



    @Test(dataProvider = "endpoints")
    public void test200Status() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);

        int actualResul = response.getStatusLine().getStatusCode();

        assertEquals(actualResul, 200);
    }

    @Test
    public void test200StatusRateLimit() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/rate_limit");
        response = client.execute(get);

        int actualResul = response.getStatusLine().getStatusCode();

        assertEquals(actualResul, 200);
    }

    @Test
    public void test200StatusSearchRepo() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/search/repositories?q=java");
        response = client.execute(get);

        int actualResul = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualResul, 200);
    }
}
