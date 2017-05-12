package com.redding.tool.mybatis.generate.manager;

import com.redding.tool.mybatis.generate.service.ServiceGenerateJavaFiesClient;
import com.redding.tool.mybatis.generate.service.ServiceImplGenerateJavaFiesClient;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by wurenqing on 2017/3/20.
 */
public class ManagerGenerateJaveFilesPlugin extends PluginAdapter {


    private String targetPackage;
    private String targetProject;


    public String getTargetPackage() {
        return targetPackage;
    }

    public ManagerGenerateJaveFilesPlugin setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
        return this;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public ManagerGenerateJaveFilesPlugin setTargetProject(String targetProject) {
        this.targetProject = targetProject;
        return this;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        this.setTargetPackage(this.properties.getProperty("targetPackage"));
        this.setTargetProject(this.properties.getProperty("targetProject"));
    }

    /**
     * This method is called after all the setXXX methods are called, but before
     * any other method is called. This allows the plugin to determine whether
     * it can run or not. For example, if the plugin requires certain properties
     * to be set, and the properties are not set, then the plugin is invalid and
     * will not run.
     *
     * @param warnings add strings to this list to specify warnings. For example, if
     *                 the plugin is invalid, you should specify why. Warnings are
     *                 reported to users after the completion of the run.
     * @return true if the plugin is in a valid state. Invalid plugins will not
     * be called
     */
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        System.out.println("=======================");
        System.out.println("===       TEST      ===");
        System.out.println("=======================");
        List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();

        List<AbstractJavaGenerator> clientGenerators = new ArrayList<AbstractJavaGenerator>();

        try {
            //###取table信息
            Field privateStringField = this.context.getClass().getDeclaredField("introspectedTables");
            privateStringField.setAccessible(true);//允许访问私有字段
            List<IntrospectedTable> tables = (List<IntrospectedTable>) privateStringField.get(this.context);
            for (IntrospectedTable table : tables) {
                ManagerGenerateJavaFiesClient test = new ManagerGenerateJavaFiesClient(this);
                test.setContext(this.getContext());
                test.setIntrospectedTable(table);
                clientGenerators.add(test);

                ManagerImplGenerateJavaFiesClient testImpl = new ManagerImplGenerateJavaFiesClient(this);
                testImpl.setContext(this.getContext());
                testImpl.setIntrospectedTable(table);
                clientGenerators.add(testImpl);

            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for (AbstractJavaGenerator javaGenerator : clientGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        this.getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter());
                answer.add(gjf);
            }
        }
        return answer;
    }

}
