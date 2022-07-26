package com.example.book.data.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Unit test implementation for {@link MongoConfig}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 26.7.2022
 */
public class MongoConfigTest {

    private MongoConfig mongoConfig;

    @BeforeEach
    public void setUp() {

        mongoConfig = new MongoConfig();
        mongoConfig.setConnString("conn-string");
        mongoConfig.setDatabase("database");
    }

    @Test
    public void testCreateMongoClient() {

    }
}
