package com.achadaga.portfoliotracker.test;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoIterable;

public class App {

  public static void main(String[] args) {
    ConnectionString connectionString = new ConnectionString("mongodb+srv://spuser:stockport"
        + "@spapp.eai5e.mongodb.net/SPApp?retryWrites=true&w=majority");
    MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString).build();
    MongoClient mongoClient = MongoClients.create(settings);
    MongoIterable<String> strings = mongoClient.listDatabaseNames();
    for (String string : strings) {
      System.out.println(string);
    }
  }
}
