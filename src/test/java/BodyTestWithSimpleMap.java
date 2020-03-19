import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static Entities.User.ID;
import static Entities.User.LOGIN;

public class BodyTestWithSimpleMap extends BaseClass {
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
    public void getBodyforGetUserLogin() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/users/JoaquinGonzales");
        response = client.execute(get);

        String jsonBody = EntityUtils.toString(response.getEntity());

        JSONObject jsonObject = new JSONObject(jsonBody);
        String actualLoginValue = (String)getValueFor(jsonObject, LOGIN);
        Assert.assertEquals(actualLoginValue,"JoaquinGonzales");
    }


    @Test
    public void getBodyforGetUserId() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT+"/users/JoaquinGonzales");
        response = client.execute(get);

        String jsonBody = EntityUtils.toString(response.getEntity());

        JSONObject jsonObject = new JSONObject(jsonBody);
        Integer actualLoginValue = (Integer)getValueFor(jsonObject, ID);
        Assert.assertEquals(actualLoginValue,Integer.valueOf(13631159));
    }
    private Object getValueFor(JSONObject jsonObject, String login) {
        return jsonObject.get(login);
    }


}
