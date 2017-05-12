package com.redding.tool.mybatis.generate.manager;

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
public class ManagerImplGenerateJavaFiesClient extends AbstractJavaGenerator {
    private PluginAdapter plugin;

    public ManagerImplGenerateJavaFiesClient(PluginAdapter plugin) {
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
        buffer.append("ManagerImpl");

        FullyQualifiedJavaType type          = new FullyQualifiedJavaType(buffer.toString());
        TopLevelClass          topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

//        String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
//        if (!stringHasValue(rootInterface)) {
//            rootInterface = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
//        }

        String rootInterface = "com.redding.rbac.infrastructure.manager.utils.DefaultManager";

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);


            FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());


            topLevelClass.setSuperClass(new FullyQualifiedJavaType(rootInterface + "<" + entityType.getShortName() +">"));

            topLevelClass.addImportedType(fqjt);
            topLevelClass.addImportedType(entityType);
            FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("com.redding.rbac.infrastructure." + entityType.getShortName() + "Manager");
            topLevelClass.addSuperInterface(superClass);
            topLevelClass.addImportedType(superClass);
            topLevelClass.addAnnotation("@Service");
        }


        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

        answer.add(topLevelClass);

        return answer;
    }
}
