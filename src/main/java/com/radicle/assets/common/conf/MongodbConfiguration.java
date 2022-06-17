package com.radicle.assets.common.conf;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.radicle.assets")
@Configuration
public class MongodbConfiguration extends AbstractMongoClientConfiguration {

    @Value("${radicle.mongo.mongoIp}")
    String mongoIp;
    @Value("${radicle.mongo.mongoPort}")
    String mongoPort;
    @Value("${radicle.mongo.mongoDbName}")
    String mongoDbName;

    @Override
    protected String getDatabaseName() {
        return mongoDbName;
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(mongoIp + ":" + mongoPort + "/" + mongoDbName);
    }

}
