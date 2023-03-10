package com.pavelhnelicya.redisson.test;

import com.pavelhnelicya.redisson.test.config.RedissonConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.redisson.api.RedissonReactiveClient;

/**
 * Create connection with Server BeforeAll all Tests
 * And shutdown connection after Tests
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    private final RedissonConfig redissonConfig = new RedissonConfig();

    protected RedissonReactiveClient client;

    @BeforeAll
    public void setClient() {
        this.client = this.redissonConfig.getReactiveClient();
    }

    @AfterAll
    public void shutdown() {
        this.client.shutdown();
    }

    /**
     * Add Time delay method for Testing
     */
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
