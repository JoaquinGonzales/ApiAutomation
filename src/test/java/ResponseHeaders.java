import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class ResponseHeaders extends BaseClass{

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
    public void test401StatusUser() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);

        Header header = response.getEntity().getContentType();
//        int actualResul = response.getStatusLine().getStatusCode();

        assertEquals(header.getValue(), "application/json; charset=utf-8");

        ContentType ct = ContentType.APPLICATION_ATOM_XML.getOrDefault(response.getEntity());

        assertEquals(ct.getMimeType(), "application/json");
    }



    private String getHeaderUsingJava8(CloseableHttpResponse response, String headerName)
    {
       List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());

       Header matchedHeader = httpHeaders.stream().filter(header -> headerName.equalsIgnoreCase(header.getName()))
               .findFirst().orElseThrow(()->new RuntimeException("Didn't find the header provided"));
       return matchedHeader.getValue();
    }

    @Test
    public void serverIsGithub() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);

        String headerValue =ResponseUtils.getHeaderString(response, "Server");
        assertEquals(headerValue, "GitHub.com");
    }

    @Test
    public void srayHeader()throws IOException
    {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);

        String headerValue = getHeaderUsingJava8(response, "X-RateLimit-Limit");
        assertEquals(headerValue, "60");
    }

    @Test
    public void eTagIsPresent() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);
        response = client.execute(get);
        boolean tagPresent = ResponseUtils.getHeader(response,"ETag");

        Assert.assertTrue(tagPresent);
    }
}
