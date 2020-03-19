package Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.regex.Matcher;

public class RateLimit {

    private int coreLimit;
    private String searchLimit;

    public int getCoreLimit() {
        return coreLimit;
    }

    public String getSearchLimit() {
        return searchLimit;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("resources")
    private void unmarshallNester(Map<String, Object> resourses)
    {
        Map<String,Integer> core = (Map<String, Integer>) resourses.get("core");
        coreLimit = core.get("limit");

        Map<String,String> search = (Map<String, String>) resourses.get("search");
        searchLimit = String.valueOf(search.get("limit"));
    }
}
