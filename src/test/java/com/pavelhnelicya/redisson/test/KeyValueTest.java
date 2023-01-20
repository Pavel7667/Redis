package com.pavelhnelicya.redisson.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

@Slf4j
public class KeyValueTest extends BaseTest {

    /**
     * Create Key and value for Redis
     */
    @Test
    public void keyValueAccessTest() {
        RBucketReactive<String> bucket =
                this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam");
        Mono<Void> get = bucket.get()
                .doOnNext(log::info)
                .then();

        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
    }

    /**
     * Create Key and value for Redis with lifetime in 10 sec
     */
    @Test
    public void keyValueExpiryTest() {
        RBucketReactive<String> bucket =
                this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(log::info)
                .then();

        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
    }

    /**
     * Create Key and value for Redis with lifetime in 10 sec
     * And then prolong the life of the element
     */
    @Test
    public void keyValueExtendExpireTest() {
        RBucketReactive<String> bucket =
                this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("sam", 10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get()
                .doOnNext(log::info)
                .then();

        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
        //extend
        sleep(5000);
        Mono<Boolean> mono = bucket.expire(60, TimeUnit.SECONDS);
        StepVerifier.create(mono)
                .expectNext(true)
                .verifyComplete();
        //access expiration time
        Mono<Void> ttl = bucket.remainTimeToLive()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(ttl)
                .verifyComplete();
    }
}
