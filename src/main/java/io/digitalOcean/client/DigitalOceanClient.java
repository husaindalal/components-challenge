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

        //TODO surround with circuit breaker: https://cloud.spring.io/spring-cloud-netflix/multi/multi__circuit_breaker_hystrix_clients.html
        DOPageComponents pageComponents = restTemplate.getForObject(URL, DOPageComponents.class);

        //Need not check pageComponents null
        return pageComponents.getComponents();
    }


//    static class Mapper {
//        static Component toDto(DOComponent doComponent) {
//            Component component = new Component();
//            component.setName();
//        }
//
//        static List<Component> toDto(List<DOComponent> doComponents) {
//            if(doComponents == null || doComponents.isEmpty()) {
//                return new ArrayList<>();
//            }
//
//        }
//    }
}
