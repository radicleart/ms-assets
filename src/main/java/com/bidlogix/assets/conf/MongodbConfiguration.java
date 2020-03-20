package com.bidlogix.assets.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.bidlogix.assets.ApplicationSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@EnableMongoRepositories(basePackages = "com.bidlogix.assets")

@Configuration
public class MongodbConfiguration extends AbstractMongoClientConfiguration {

	@Autowired private ApplicationSettings applicationSettings;

	@Override
	protected String getDatabaseName() {
		return applicationSettings.getMongoDbName();
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.bidlogix";
	}

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(applicationSettings.getMongoIp() + ":" + applicationSettings.getMongoPort());
    }

}
