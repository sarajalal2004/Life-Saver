package ga.jdb.FirefighterSorter.FirefighterSorter.service;

import ga.jdb.FirefighterSorter.FirefighterSorter.model.response.TomTomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.JsonNode;

@Service
public class TomTomService {

    private RestClient restClient;

    @Autowired
    public void setRestClient(RestClient restClient){
        this.restClient = restClient;
    }

    @Value("${tomtom.api.key}")
    private String key;

    @Value("${tomtom.api.base-url}")
    private String baseUrl;

    public JsonNode RoutingInfo(double latitude1, double longitude1, double latitude2,
                                double longitude2, boolean traffic){
        String routeString = latitude1 + "," + longitude1 + ":" + latitude2 + "," + longitude2;

        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/routing/1/calculateRoute/" + routeString + "/json")
                .queryParam("traffic", traffic)
                .queryParam("key", key)
                .build()
                .toUriString();

        return restClient.get().uri(url).retrieve().body(JsonNode.class);
    }

    public TomTomResponse getTomTomResponse(JsonNode response){
        JsonNode summary = response.path("routes").get(0).path("summary");
        return new TomTomResponse(
                summary.path("lengthInMeters").asDouble(),
                summary.path("travelTimeInSeconds").asDouble()
        );
    }
}
