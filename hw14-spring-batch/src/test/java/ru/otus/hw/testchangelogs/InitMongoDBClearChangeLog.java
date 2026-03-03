package ru.otus.hw.testchangelogs;

import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;


public class InitMongoDBClearChangeLog {
    @ChangeSet(order = "000", id = "dropDb", author = "stepanov", runAlways = true)
    public void dropDb(MongoDatabase database) {
        database.drop();
    }
}
