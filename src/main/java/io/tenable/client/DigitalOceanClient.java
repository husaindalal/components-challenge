package io.tenable.client;

import io.tenable.client.dto.DOComponent;
import io.tenable.client.dto.DOPageComponents;
import io.tenable.metrics.Metrics;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class DigitalOceanClient {

    //TODO should be in application.yml, could be different per environment
    private static final String URL = "https://s2k7tnzlhrpw.statuspage.io/api/v1/components.json";

    private RestTemplate restTemplate;

    @Autowired
    public DigitalOceanClient() {
        this.restTemplate = new RestTemplate();

    }

    /**
     * Main client method to retrieve components from Digital Ocean.
     * TODO surround with circuit breaker: https://cloud.spring.io/spring-cloud-netflix/multi/multi__circuit_breaker_hystrix_clients.html
     * TODO limit the results so future expansion does not affect performance
     *
     * @return
     */
    public List<DOComponent> getDoComponents() {

        log.info("Calling DigitalOcean url {} ", URL);
        DOPageComponents pageComponents = restTemplate.getForObject(URL, DOPageComponents.class);

        List<DOComponent> components = new ArrayList<>();
        if (pageComponents != null) {
            components = pageComponents.getComponents();
        }
        Metrics.add("getDOComponents", (long) components.size(), null);
        log.info("Received {} components from DO url {} ", components.size(), URL);
        return components;
    }


}
