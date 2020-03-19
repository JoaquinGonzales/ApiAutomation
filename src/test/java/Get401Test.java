import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class Get401Test  extends BaseClass{



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
    @DataProvider
    private Object[][] endpoints()
    {
        return new Object[][]{
                {"/user"},
                {"/notifications"},
                {"/user/followers"}
        };
    }
    @Test(dataProvider = "endpoints")
    public void test401StatusUser(String endpoint) throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+endpoint);
        response = client.execute(get);

        int actualResul = response.getStatusLine().getStatusCode();

        assertEquals(actualResul, 401);
    }

}
