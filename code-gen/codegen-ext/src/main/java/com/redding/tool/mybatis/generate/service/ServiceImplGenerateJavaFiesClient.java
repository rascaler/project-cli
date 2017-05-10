package com.redding.tool.mybatis.generate.service;

import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Created by wurenqing on 2017/3/20.
 */
public class ServiceImplGenerateJavaFiesClient extends AbstractJavaGenerator {
    private PluginAdapter plugin;

    public ServiceImplGenerateJavaFiesClient(PluginAdapter plugin) {
        this.plugin = plugin;
    }


    public List<CompilationUnit> getCompilationUnits() {


        String       domain = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
//        String prefix = domain.substring(0,domain.length()-2);

        StringBuffer buffer = new StringBuffer();
        buffer.append(plugin.getProperties().getProperty("targetPackage"));
        buffer.append(".");
        buffer.append("impl.");
        buffer.append(domain);
        buffer.append("ServiceImpl");

        FullyQualifiedJavaType type          = new FullyQualifiedJavaType(buffer.toString());
        TopLevelClass          topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

//        String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
//        if (!stringHasValue(rootInterface)) {
//            rootInterface = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
//        }

        String rootInterface = "com.jwx.vaccine.common.core.service.BaseService";

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);


            FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType() + "Do");


            topLevelClass.setSuperClass(new FullyQualifiedJavaType(rootInterface + "<" + entityType.getShortName() + "Do" +">"));

            topLevelClass.addImportedType(fqjt);
            topLevelClass.addImportedType(entityType);
            FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("com.jwx.vaccine.support.c.service." + entityType.getShortName() + "Service");
            topLevelClass.addSuperInterface(superClass);
            topLevelClass.addImportedType(superClass);
        }


        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

        answer.add(topLevelClass);

        return answer;
    }
}
