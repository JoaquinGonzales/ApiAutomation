import Entities.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ResponseUtils {

    public static boolean getHeader(HttpResponse response, String headerName)
    {
        List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());
        return httpHeaders.stream().anyMatch(header -> header.getName().equalsIgnoreCase(headerName));
    }

    public static String getHeaderString(HttpResponse response, String headerName)
    {
        List<Header> httpheaders = Arrays.asList(response.getAllHeaders());
        String headerString ="";
        for (Header header: httpheaders)
        {
            if (header.getName().equalsIgnoreCase(headerName))
            {
                headerString = header.getValue();
            }

        }
        if (headerString.isEmpty())
        {
            throw new RuntimeException("Didn't finde the header "+headerName);
        }
        return headerString;
    }

    private static String getHeader(CloseableHttpResponse closeableHttpResponse, String headerName)
    {
        Header[] headers = closeableHttpResponse.getAllHeaders();
        List<Header> httpHeaders = Arrays.asList(headers);
        String returnHeader="";

        for (Header header:httpHeaders)
        {
            if (headerName.equalsIgnoreCase(header.getName()))
            {
                returnHeader = header.getValue();
            }
        }
        if (returnHeader.isEmpty())
        {
            throw new RuntimeException("Didn't finde the header "+headerName);
        }
        return returnHeader;
    }
    public static User unmarshall(CloseableHttpResponse response, Class<User> userClass) throws IOException {
        String jsonBody = EntityUtils.toString(response.getEntity());
        return new ObjectMapper().
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).
                readValue(jsonBody,userClass);
    }

    public static <T> T unmarshallGeneric(CloseableHttpResponse response, Class<T> userClass) throws IOException {
        String jsonBody = EntityUtils.toString(response.getEntity());
        return new ObjectMapper().
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).
                readValue(jsonBody,userClass);
    }
}
