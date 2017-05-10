package cn.yunlai.cjds.mybatis.generate.service;

import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Created by wurenqing on 2017/3/20.
 */
public class ServiceGenerateJavaFiesClient extends AbstractJavaGenerator {
    private PluginAdapter plugin;

    public ServiceGenerateJavaFiesClient(PluginAdapter plugin) {
        this.plugin = plugin;
    }


    public List<CompilationUnit> getCompilationUnits() {


        String       domain = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
//        String prefix = domain.substring(0,domain.length()-2);

        StringBuffer buffer = new StringBuffer();
        buffer.append(plugin.getProperties().getProperty("targetPackage"));
        buffer.append(".");
        buffer.append(domain);
        buffer.append("Service");
        System.out.println(buffer + "=========");

        FullyQualifiedJavaType type      = new FullyQualifiedJavaType(buffer.toString());
        Interface              interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);

//        String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
//        if (!stringHasValue(rootInterface)) {
//            rootInterface = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
//        }

        String rootInterface = "com.jwx.vaccine.common.core.service.IService";

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);

            FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType() + "Do");
            interfaze.addSuperInterface(new FullyQualifiedJavaType(rootInterface + "<" + entityType.getShortName() + "Do" + ">"));
            interfaze.addImportedType(fqjt);
            interfaze.addImportedType(entityType);
        }

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

        answer.add(interfaze);

        return answer;
    }
}
