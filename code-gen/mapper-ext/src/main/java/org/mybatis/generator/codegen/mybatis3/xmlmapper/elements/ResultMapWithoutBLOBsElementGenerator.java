//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;
import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.internal.util.StringUtility;

public class ResultMapWithoutBLOBsElementGenerator extends AbstractXmlElementGenerator {
    private boolean isSimple;

    public ResultMapWithoutBLOBsElementGenerator(boolean isSimple) {
        this.isSimple = isSimple;
    }

    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("resultMap");
        answer.addAttribute(new Attribute("id", this.introspectedTable.getBaseResultMapId()));
        String returnType;
        if(this.isSimple) {
            returnType = this.introspectedTable.getBaseRecordType();
        } else if(this.introspectedTable.getRules().generateBaseRecordClass()) {
            returnType = this.introspectedTable.getBaseRecordType();
        } else {
            returnType = this.introspectedTable.getPrimaryKeyType();
        }

        answer.addAttribute(new Attribute("type", returnType));
        this.context.getCommentGenerator().addComment(answer);
        if(this.introspectedTable.isConstructorBased()) {
            this.addResultMapConstructorElements(answer);
        } else {
            this.addResultMapElements(answer);
        }

        if(this.context.getPlugins().sqlMapResultMapWithoutBLOBsElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }

    }

    private void addResultMapElements(XmlElement answer) {
        XmlElement introspectedColumn;
        for(Iterator columns = this.introspectedTable.getPrimaryKeyColumns().iterator(); columns.hasNext(); answer.addElement(introspectedColumn)) {
            IntrospectedColumn i$ = (IntrospectedColumn)columns.next();
            introspectedColumn = new XmlElement("id");
            introspectedColumn.addAttribute(new Attribute("column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(i$)));
            introspectedColumn.addAttribute(new Attribute("property", i$.getJavaProperty()));
            introspectedColumn.addAttribute(new Attribute("jdbcType", i$.getJdbcTypeName()));
            if(StringUtility.stringHasValue(i$.getTypeHandler())) {
                introspectedColumn.addAttribute(new Attribute("typeHandler", i$.getTypeHandler()));
            }
        }

        List columns1;
        if(this.isSimple) {
            columns1 = this.introspectedTable.getNonPrimaryKeyColumns();
        } else {
            columns1 = this.introspectedTable.getBaseColumns();
        }

        XmlElement resultElement;
        for(Iterator i$1 = columns1.iterator(); i$1.hasNext(); answer.addElement(resultElement)) {
            IntrospectedColumn introspectedColumn1 = (IntrospectedColumn)i$1.next();
            resultElement = new XmlElement("result");
            resultElement.addAttribute(new Attribute("column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn1)));
            resultElement.addAttribute(new Attribute("property", introspectedColumn1.getJavaProperty()));
            switch (introspectedColumn1.getJdbcType()){
                case -6:resultElement.addAttribute(new Attribute("jdbcType", "INTEGER"));break;
                default:resultElement.addAttribute(new Attribute("jdbcType", introspectedColumn1.getJdbcTypeName()));

            }
            if(StringUtility.stringHasValue(introspectedColumn1.getTypeHandler())) {
                resultElement.addAttribute(new Attribute("typeHandler", introspectedColumn1.getTypeHandler()));
            }
        }

    }

    private void addResultMapConstructorElements(XmlElement answer) {
        XmlElement constructor = new XmlElement("constructor");

        XmlElement introspectedColumn;
        for(Iterator columns = this.introspectedTable.getPrimaryKeyColumns().iterator(); columns.hasNext(); constructor.addElement(introspectedColumn)) {
            IntrospectedColumn i$ = (IntrospectedColumn)columns.next();
            introspectedColumn = new XmlElement("idArg");
            introspectedColumn.addAttribute(new Attribute("column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(i$)));
            switch (i$.getJdbcType()){
                case -6:introspectedColumn.addAttribute(new Attribute("jdbcType", "INTEGER"));break;
                default:introspectedColumn.addAttribute(new Attribute("jdbcType", i$.getJdbcTypeName()));

            }
            introspectedColumn.addAttribute(new Attribute("javaType", i$.getFullyQualifiedJavaType().getFullyQualifiedName()));
            if(StringUtility.stringHasValue(i$.getTypeHandler())) {
                introspectedColumn.addAttribute(new Attribute("typeHandler", i$.getTypeHandler()));
            }
        }

        List columns1;
        if(this.isSimple) {
            columns1 = this.introspectedTable.getNonPrimaryKeyColumns();
        } else {
            columns1 = this.introspectedTable.getBaseColumns();
        }

        XmlElement resultElement;
        for(Iterator i$1 = columns1.iterator(); i$1.hasNext(); constructor.addElement(resultElement)) {
            IntrospectedColumn introspectedColumn1 = (IntrospectedColumn)i$1.next();
            resultElement = new XmlElement("arg");
            resultElement.addAttribute(new Attribute("column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn1)));
            resultElement.addAttribute(new Attribute("jdbcType", introspectedColumn1.getJdbcTypeName()));
            resultElement.addAttribute(new Attribute("javaType", introspectedColumn1.getFullyQualifiedJavaType().getFullyQualifiedName()));
            if(StringUtility.stringHasValue(introspectedColumn1.getTypeHandler())) {
                resultElement.addAttribute(new Attribute("typeHandler", introspectedColumn1.getTypeHandler()));
            }
        }

        answer.addElement(constructor);
    }
}
