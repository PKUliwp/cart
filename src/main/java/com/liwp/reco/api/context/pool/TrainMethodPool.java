package com.liwp.reco.api.context.pool;

import com.liwp.reco.api.context.info.KeyPairInfo;
import com.liwp.reco.api.query.entity.entities.MethodEntity;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by liwp on 2017/6/20.
 */
public class TrainMethodPool {
    public static List<List<MethodEntity>> methodBlocks = new ArrayList<>();
    public Map<MethodEntity, Integer> methods = new HashMap<>();
    public Map<KeyPairInfo, Integer> methodDistributions = new HashMap<>();
    public int num = 1;

    public void build() {
        methodBlocks.forEach(block -> {
            //System.out.println(num ++);
            //System.out.println(block.size() +" " + methods.size());
            block.forEach(method -> {
                //System.out.println(method.displayName());
                if (methods.containsKey(method)) {
                    methods.put(method, methods.get(method) + 1);
                } else {
                    methods.put(method, 1);
                }
            });

            Deque<MethodEntity> contextDeque = new LinkedList<>();
            block.forEach(value -> {
                contextDeque.forEach(key -> {
                    KeyPairInfo info = new KeyPairInfo(key, value);
                    if(methodDistributions.containsKey(info)) {
                        methodDistributions.put(info, methods.get(info.key) + 1);
                    } else {
                        methodDistributions.put(info, 1);
                    }
                });

                contextDeque.addLast(value);
                if(contextDeque.size() > 10) {
                    contextDeque.removeFirst();
                }
            });
        });
    }

    public void printBlocks() {
        try {
            HashMap<String, Integer> methodMap = new HashMap<>();
            FileWriter writer = new FileWriter("/Users/liwp/Desktop/nums.txt");
            FileWriter writer2 = new FileWriter("/Users/liwp/Desktop/methods.txt");
            methodBlocks.forEach(block -> {
                block.forEach(method -> {
                    try {
                        if (!methodMap.containsKey(method.displayName())) {
                            methodMap.put(method.displayName(), num ++);
                        }
                        writer.write(methodMap.get(method.displayName()).toString() + '\n');
                        writer2.write(method.displayName() + '\n');
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                try {
                    //writer.write("\n\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            //System.out.println(methodMap.get("Method: java.lang.StringBuilder.toString()"));
            writer.close();
            writer2.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(num);
        }


    }
}
