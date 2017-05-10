package cn.yunlai.cjds.mybatis.generate.dto;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.RootClassInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wurenqing on 2017/3/20.
 */


public class DtoGenerateJavaFiesClient extends AbstractJavaGenerator {
    private PluginAdapter plugin;

    public DtoGenerateJavaFiesClient(PluginAdapter plugin) {
        this.plugin = plugin;
    }


    public List<CompilationUnit> getCompilationUnits() {


        String       domain = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        //去掉Do
//        String prefix = domain.substring(0,domain.length()-2);

        StringBuffer buffer = new StringBuffer();
        buffer.append(plugin.getProperties().getProperty("targetPackage"));
        buffer.append(".");
        buffer.append(domain);
        buffer.append("Dto");

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(buffer.toString());
//        TopLevelClass          topLevelClass = new TopLevelClass(type);
//        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
//
//
//        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
//
//
//        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
//
//        answer.add(topLevelClass);
//
//        return answer;


        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();

        Plugin plugins = context.getPlugins();
//        CommentGenerator commentGenerator = context.getCommentGenerator();
//

//        FullyQualifiedJavaType type          = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
//        commentGenerator.addJavaFileComment(topLevelClass);

        String rootInterface = "java.io.Serializable";

        topLevelClass.addSuperInterface(new FullyQualifiedJavaType(rootInterface));
        topLevelClass.addImportedType(rootInterface);


        FullyQualifiedJavaType superClass = getSuperClass();
        if (superClass != null) {
            topLevelClass.setSuperClass(superClass);
            topLevelClass.addImportedType(superClass);
        }

        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();

        if (introspectedTable.isConstructorBased()) {
            addParameterizedConstructor(topLevelClass);

            if (!introspectedTable.isImmutable()) {
                addDefaultConstructor(topLevelClass);
            }
        }

        String rootClass = getRootClass();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (RootClassInfo.getInstance(rootClass, warnings)
                    .containsProperty(introspectedColumn)) {
                continue;
            }

            Field field = getJavaBeansField(introspectedColumn);

            field.getAnnotations().clear();

            if (plugins.modelFieldGenerated(field, topLevelClass,
                    introspectedColumn, introspectedTable,
                    Plugin.ModelClassType.BASE_RECORD)) {
                topLevelClass.addField(field);
                topLevelClass.addImportedType(field.getType());
            }

            Method method = getJavaBeansGetter(introspectedColumn);
            if (plugins.modelGetterMethodGenerated(method, topLevelClass,
                    introspectedColumn, introspectedTable,
                    Plugin.ModelClassType.BASE_RECORD)) {
                topLevelClass.addMethod(method);
            }

            if (!introspectedTable.isImmutable()) {
                method = getJavaBeansSetter(introspectedColumn);
                if (plugins.modelSetterMethodGenerated(method, topLevelClass,
                        introspectedColumn, introspectedTable,
                        Plugin.ModelClassType.BASE_RECORD)) {
                    topLevelClass.addMethod(method);
                }
            }
        }

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(topLevelClass);
//        if (context.getPlugins().modelBaseRecordClassGenerated(
//                topLevelClass, introspectedTable)) {
//            answer.add(topLevelClass);
//        }

        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();

        StringBuffer fieldbuffer = new StringBuffer();
        fieldbuffer.append("表名: ").append(introspectedTable.getFullyQualifiedTableNameAtRuntime())
                .append(" \n")
                .append("{| class=\"wikitable sortable\"\n");

        fieldbuffer.append("! 排序 !! 字段名 !! 类型 !! 长度 !! 主键 !! 自增长 !! 空 !! 默认值 !! 备注\n");
        int c = 1;
        for (IntrospectedColumn introspectedColumn : columnList) {
            /**
             * ! 排序 !! 字段名 !! 类型 !! 长度 !! 主键 !! 自增长 !! 空 !! 默认值 !! 备注
             |-
             | 1 || id || int || 11 || 是 || 是 ||  ||  ||
             */


            fieldbuffer.append("|-\n");
            fieldbuffer.append("| ").append(c);
            fieldbuffer.append(" || ");
            fieldbuffer.append(introspectedColumn.getActualColumnName()).append(" || ")
                    .append(introspectedColumn.getJdbcTypeName().toLowerCase()).append(" || ")
                    .append(introspectedColumn.getLength()).append(" || ")
                    .append(introspectedColumn.isIdentity() ? "是" : "").append(" || ")
                    .append(introspectedColumn.isSequenceColumn() ? "是" : "").append(" || ")
                    .append(introspectedColumn.isNullable() ? "是" : "").append(" || ")
                    .append(introspectedColumn.getDefaultValue() == null ? "" : introspectedColumn.getDefaultValue()).append(" || ")
                    .append(introspectedColumn.getRemarks())
            .append("\n");

            c++;

        }
        fieldbuffer.append("\n")
                .append("|}\n=========");

        System.out.println(fieldbuffer);

        return answer;


    }

    private FullyQualifiedJavaType getSuperClass() {
        FullyQualifiedJavaType superClass;
        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            superClass = new FullyQualifiedJavaType(introspectedTable
                    .getPrimaryKeyType());
        } else {
            String rootClass = getRootClass();
            if (rootClass != null) {
                superClass = new FullyQualifiedJavaType(rootClass);
            } else {
                superClass = null;
            }
        }

        return superClass;
    }

    private boolean includePrimaryKeyColumns() {
        return !introspectedTable.getRules().generatePrimaryKeyClass()
                && introspectedTable.hasPrimaryKeyColumns();
    }

    private boolean includeBLOBColumns() {
        return !introspectedTable.getRules().generateRecordWithBLOBsClass()
                && introspectedTable.hasBLOBColumns();
    }

    private void addParameterizedConstructor(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(topLevelClass.getType().getShortName());
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

        List<IntrospectedColumn> constructorColumns =
                includeBLOBColumns() ? introspectedTable.getAllColumns() :
                        introspectedTable.getNonBLOBColumns();

        for (IntrospectedColumn introspectedColumn : constructorColumns) {
            method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(),
                    introspectedColumn.getJavaProperty()));
        }

        StringBuilder sb = new StringBuilder();
        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            boolean comma = false;
            sb.append("super("); //$NON-NLS-1$
            for (IntrospectedColumn introspectedColumn : introspectedTable
                    .getPrimaryKeyColumns()) {
                if (comma) {
                    sb.append(", "); //$NON-NLS-1$
                } else {
                    comma = true;
                }
                sb.append(introspectedColumn.getJavaProperty());
            }
            sb.append(");"); //$NON-NLS-1$
            method.addBodyLine(sb.toString());
        }

        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();

        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            sb.setLength(0);
            sb.append("this."); //$NON-NLS-1$
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" = "); //$NON-NLS-1$
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(';');
            method.addBodyLine(sb.toString());
        }

        topLevelClass.addMethod(method);
    }

    private List<IntrospectedColumn> getColumnsInThisClass() {
        List<IntrospectedColumn> introspectedColumns;
        if (includePrimaryKeyColumns()) {
            if (includeBLOBColumns()) {
                introspectedColumns = introspectedTable.getAllColumns();
            } else {
                introspectedColumns = introspectedTable.getNonBLOBColumns();
            }
        } else {
            if (includeBLOBColumns()) {
                introspectedColumns = introspectedTable
                        .getNonPrimaryKeyColumns();
            } else {
                introspectedColumns = introspectedTable.getBaseColumns();
            }
        }

        return introspectedColumns;
    }
}
