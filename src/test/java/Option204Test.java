import org.apache.http.client.methods.HttpOptions;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class Option204Test extends BaseClass {

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
    public void optionReturnsCorrectMethodLis() throws IOException {
        String header = "Access-Control-Allow-Methods";
        String expectedReply = "GET, POST, PATCH, PUT, DELETE";

        HttpOptions request = new HttpOptions(BASE_ENDPOINT);
        response = client.execute(request);

        String actualResult = ResponseUtils.getHeaderString(response,header);
        assertEquals(actualResult, expectedReply);
    }
}
