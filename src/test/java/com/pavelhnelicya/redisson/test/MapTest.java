package com.pavelhnelicya.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

public class MapTest extends BaseTest {

    @Test
    public void mapTest1() {
        RMapReactive<String, String> map =
                this.client.getMap("user:1", StringCodec.INSTANCE);
        Mono<String> name = map.put("name", "same");
        Mono<String> age = map.put("age", "10");
        Mono<String> city = map.put("city", "atlanta");
        StepVerifier.create(name
                        .concatWith(age)
                        .concatWith(city)
                        .then())
                .verifyComplete();
    }

    @Test
    public void mapTest2() {
        RMapReactive<String, String> mapReactive =
                this.client.getMap("user:2", StringCodec.INSTANCE);
        Map<String, String> mapJava = Map.of(
                "name", "jake",
                "age", "30",
                "city", "miami");

        StepVerifier.create(mapReactive.putAll(mapJava).then())
                .verifyComplete();
    }
}
