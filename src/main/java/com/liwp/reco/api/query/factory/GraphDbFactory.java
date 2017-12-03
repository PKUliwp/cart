package com.liwp.reco.api.query.factory;

import com.liwp.reco.api.query.mapper.utils.MapperUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

/**
 * Created by liwp on 2017/5/2.
 */
public class GraphDbFactory {
    private static String dbPath = "/Users/liwp/Desktop/lucene/graphdb-lucene-ultimate";

    private GraphDbFactory() {

    }

    public static GraphDatabaseService builder() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(new File(dbPath));
    }

    public static int refNum = 0;
    public static void main(String args[]) {
        GraphDatabaseService db = builder();
        try(Transaction tx = db.beginTx()) {
            db.getAllRelationships().stream().forEach(relationship -> {
                if(relationship.getType().equals(RelationshipType.withName("docRef"))) {
                    if(MapperUtils.checkNodeLabel(relationship.getEndNode(), "Interface")) {
                        //System.out.println(relationship.getEndNode().getProperty("params"));
                        refNum++;
                    }
                }
            });

            tx.success();
        }
        //System.out.println(refNum);
    }

}
