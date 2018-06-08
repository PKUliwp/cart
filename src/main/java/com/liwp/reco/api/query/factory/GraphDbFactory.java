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
    private static String dbPath = "C:\\Users\\dell\\Desktop\\graphdb-lucene-ultimate";

    private GraphDbFactory() {

    }

    public static GraphDatabaseService builder() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(new File(dbPath));
    }

    public static int refNum = 0;
    public static void main(String args[]) {
        GraphDatabaseService db = builder();
        try(Transaction tx = db.beginTx()) {
            System.out.println(db.getAllNodes().stream().count());
            db.getAllRelationshipTypes().stream().forEach(relationshipType -> {
                System.out.println(relationshipType);
            });
            db.getAllRelationships().stream().forEach(relationship -> {
                if(relationship.getType().equals(RelationshipType.withName("docRef"))) {
                    refNum++;
                    if(MapperUtils.checkNodeLabel(relationship.getEndNode(), "Interface")) {
                        //System.out.println(relationship.getEndNode().getProperty("params"));

                    }
                }
            });

            tx.success();
        }
        System.out.println(refNum);
    }

}
