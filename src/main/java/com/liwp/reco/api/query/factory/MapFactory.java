package com.liwp.reco.api.query.factory;

import com.liwp.reco.api.query.entity.Entity;
import com.liwp.reco.api.query.entity.entities.ClassEntity;
import com.liwp.reco.api.query.entity.entities.InterfaceEntity;
import com.liwp.reco.api.query.entity.entities.MethodEntity;
import com.liwp.reco.api.query.mapper.mappers.ClassMapper;
import com.liwp.reco.api.query.mapper.mappers.InterfaceMapper;
import com.liwp.reco.api.query.mapper.mappers.MethodMapper;
import com.liwp.reco.api.query.mapper.mappers.WordToRefMapper;
import org.neo4j.graphdb.GraphDatabaseService;

import java.util.Map;

public class MapFactory {

    private MapFactory() {

    }

    public static Map<String, Map<Entity, Integer>> buildAndGetWordToRefMap() {
        GraphDatabaseService graphDb = GraphDbFactory.builder();
        WordToRefMapper wordToRefMapper = new WordToRefMapper(graphDb);
        wordToRefMapper.build();
        Map<String, Map<Entity, Integer>> wordToMethodMap = wordToRefMapper.getWordToMethodMap();
        wordToRefMapper.close();
        return wordToMethodMap;
    }

    public static Map<MethodEntity, Integer> buildAndGetMethodMap() {
        GraphDatabaseService graphDb = GraphDbFactory.builder();
        MethodMapper methodMapper = new MethodMapper(graphDb);
        methodMapper.build();
        Map<MethodEntity, Integer> methodMap = methodMapper.getMethodMap();
        methodMapper.close();
        return methodMap;
    }

    public static Map<InterfaceEntity, Integer> buildAndGetInterfaceMap() {
        GraphDatabaseService graphDb = GraphDbFactory.builder();
        InterfaceMapper interfaceMapper = new InterfaceMapper(graphDb);
        interfaceMapper.build();
        Map<InterfaceEntity, Integer> interfaceMap = interfaceMapper.getInterfaceMap();
        interfaceMapper.close();
        return interfaceMap;
    }

    public static Map<ClassEntity, Integer> buildAndGetClassMap() {
        GraphDatabaseService graphDb = GraphDbFactory.builder();
        ClassMapper classMapper = new ClassMapper(graphDb);
        classMapper.build();
        Map<ClassEntity, Integer> classMap = classMapper.getClassMap();
        classMapper.close();
        return classMap;
    }
}