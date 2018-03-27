package io.digitalOcean.client;

import io.digitalOcean.client.dto.DOComponent;
import io.digitalOcean.client.dto.DOPageComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DigitalOceanClient {

    private static final String URL = "https://s2k7tnzlhrpw.statuspage.io/api/v1/components.json";

    private RestTemplate restTemplate;

    @Autowired
    public DigitalOceanClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public List<DOComponent> getDoComponents() {

        DOPageComponents pageComponents = restTemplate.getForObject(URL, DOPageComponents.class);

        //Need not check pageComponents null
        return pageComponents.getComponents();
    }


}
