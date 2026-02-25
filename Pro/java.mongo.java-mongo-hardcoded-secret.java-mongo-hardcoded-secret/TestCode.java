package services;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class A {
    protected DB() {
        // ruleid: java-mongo-hardcoded-secret
        new MongoClient(new MongoClientURI("mongodb://user:password@a.mongolab.com:37601/goparty"))
        // ok: java-mongo-hardcoded-secret
        new MongoClient(new MongoClientURI("mongodb://<user>:password@a.mongolab.com:37601/goparty"))     
        // ok: java-mongo-hardcoded-secret
        new MongoClient(new MongoClientURI("mongodb://<user>:<password>@a.a.com:37601/goparty"))    
        // ok: java-mongo-hardcoded-secret
        new MongoClient(new MongoClientURI("mongodb://a.mongolab.com:37601/goparty"))      

    }
}