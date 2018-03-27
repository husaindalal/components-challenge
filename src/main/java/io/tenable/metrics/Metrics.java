package io.tenable.metrics;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;

import java.util.Set;

@Log4j2
public class Metrics {
    @Async
    public static void add(String metricName, Long count, Set<String> tags) {
        try {
            //Connects to a real metric capture system
            log.info("{} metric captured with count {}, and tags {}", metricName, count, tags);
        } catch (Exception e) {
            log.error("Error capturing metric", e);
        }
    }
}
