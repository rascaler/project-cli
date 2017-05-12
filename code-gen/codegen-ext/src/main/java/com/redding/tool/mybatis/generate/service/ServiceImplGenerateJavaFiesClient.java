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

        FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("com.redding.rbac.service." + domain + "Service");
        topLevelClass.addSuperInterface(superClass);
        topLevelClass.addImportedType(superClass);
        topLevelClass.addAnnotation("@Service");

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

        answer.add(topLevelClass);

        return answer;
    }
}
