package com.example.zipkinservice1;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.Tracer.SpanInScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class ZipkinService1Controller {

    @Value("${app.rest.client.url:}")
    private String restClientUrl;

    private final RestTemplate restTemplate;

    private final Tracer tracer;

    public ZipkinService1Controller(RestTemplate restTemplate, Tracer tracer) {
        this.restTemplate = restTemplate;
        this.tracer = tracer;
    }

    @GetMapping(value="/zipkin")
    public String zipkinService1() {
        log.info("Inside zipkinService 1..");

        doDelay();

        log.info("app.rest.client.url={}", restClientUrl);
        if (restClientUrl != null && restClientUrl.length() > 0) {
            String response = restTemplate.exchange(restClientUrl, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
            }).getBody();
        }
        return "Hi...";
    }

    private void doDelay() {
        long randomDuration = new Random().nextInt(10) * 1000;
        if (randomDuration > 0) {
            Span newSpan = tracer.nextSpan().name("delay").start();
            try (SpanInScope ws = tracer.withSpan(newSpan.start())) {
                log.info("Now let's create some intentional delay of {}ms", randomDuration);
                try {
                    Thread.sleep(randomDuration);
                }
                catch (InterruptedException e) {
                    newSpan.error(e);
                    e.printStackTrace();
                }
                log.info("returning after delay..");
            } finally {
                newSpan.end();
            }
        }
    }

}
