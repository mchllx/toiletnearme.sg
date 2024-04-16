package vttp.batch4.csf.toiletnearme.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import jakarta.json.JsonObject;
import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.models.Coordinates;

public class CoordsRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public static String MONGO_COLLECTION = "coordinates";

    public List<Coordinates> get(Integer limit, Integer offset) {
        Query query = new Query();
            query.skip(limit * offset);
            query.limit(limit);

        //returns a list<doc> without stream
        return mongoTemplate.find(query, Document.class
            , MONGO_COLLECTION)
            .stream()
            .map(t -> Utils.fromJSON(t))
            .toList(); 
    }

    public long updateCoords(JsonObject results) { 
        Update update = new Update()
            .set("id", results.getString("id"))
            .set("title", results.getString("title"))
            .set("lat", results.getJsonNumber("lat").doubleValue())
            .set("lng", results.getJsonNumber("lng").doubleValue())
            .set("content", results.getString("content"));

        UpdateResult updateResult = mongoTemplate
            .updateFirst(new Query(), update, Document.class, MONGO_COLLECTION);

        System.out.printf(">>>Documents updated: %d\n", updateResult.getModifiedCount());
        return updateResult.getModifiedCount();
    }

    public long deleteCoords(String id) {
        Query query = new Query()
            .addCriteria(Criteria.where("id")
            .is(id)
        );

        DeleteResult deleteResults = mongoTemplate.remove(query, Document.class, MONGO_COLLECTION);
        return deleteResults.getDeletedCount();
    }
    
}
