package com.liwp.reco.api.context;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import java.io.File;
import java.util.*;

/**
 * Created by liwp on 2017/6/18.
 */
public class TrainSourceParser {

    public static void parse() {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        Collection<File> javaFiles = FileUtils.listFiles(new File("/Users/liwp/Desktop/lucene/src"), new String[]{"java"}, true);
        Set<String> srcPathSet = new HashSet<>();
        Set<String> srcFolderSet = new HashSet<>();
        for (File javaFile : javaFiles) {
            String srcPath = javaFile.getAbsolutePath();
            String srcFolderPath = javaFile.getParentFile().getAbsolutePath();
            srcPathSet.add(srcPath);
            srcFolderSet.add(srcFolderPath);
        }
        String[] srcPaths = new String[srcPathSet.size()];
        srcPathSet.toArray(srcPaths);
        String[] srcFolderPaths = new String[srcFolderSet.size()];
        srcFolderSet.toArray(srcFolderPaths);

        parser.setEnvironment(null, srcFolderPaths, null, true);
        parser.setResolveBindings(true);
        Map<String, String> options = new Hashtable<>();
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.ENABLED);
        parser.setCompilerOptions(options);
        parser.setBindingsRecovery(true);


        parser.createASTs(srcPaths, null, new String[]{}, new FileASTRequestor() {
            @Override
            public void acceptAST(String sourceFilePath, CompilationUnit javaUnit) {

                javaUnit.accept(new RecoASTVisitor(RecoASTVisitor.Type.train));

            }
        }, null);
    }
}
