package com.pavelhnelicya.redisson.test;

import com.pavelhnelicya.redisson.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
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

    @Test
    public void mapNotOnlyStringTest() {
        //Map<Integer,Student>
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapReactive<Integer, Student> mapReactive = this.client.getMap("users", codec);

        Student student1 = new Student("sam", 10, "atlanta", List.of(1,2,3));
        Student student2 = new Student("jake", 14, "miami", List.of(11,22,33));
        Mono<Student> mono1 = mapReactive.put(1,student1);
        Mono<Student> mono2 = mapReactive.put(2,student2);

        StepVerifier.create(mono1.concatWith(mono2)).verifyComplete();
    }
}
