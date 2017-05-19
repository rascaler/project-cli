//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import java.util.Iterator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.internal.util.StringUtility;

public class ResultMapWithBLOBsElementGenerator extends AbstractXmlElementGenerator {
    public ResultMapWithBLOBsElementGenerator() {
    }

    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("resultMap");
        answer.addAttribute(new Attribute("id", this.introspectedTable.getResultMapWithBLOBsId()));
        String returnType;
        if(this.introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            returnType = this.introspectedTable.getRecordWithBLOBsType();
        } else {
            returnType = this.introspectedTable.getBaseRecordType();
        }

        answer.addAttribute(new Attribute("type", returnType));
        if(!this.introspectedTable.isConstructorBased()) {
            answer.addAttribute(new Attribute("extends", this.introspectedTable.getBaseResultMapId()));
        }

        this.context.getCommentGenerator().addComment(answer);
        if(this.introspectedTable.isConstructorBased()) {
            this.addResultMapConstructorElements(answer);
        } else {
            this.addResultMapElements(answer);
        }

        if(this.context.getPlugins().sqlMapResultMapWithBLOBsElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }

    }

    private void addResultMapElements(XmlElement answer) {
        XmlElement resultElement;
        for(Iterator i$ = this.introspectedTable.getBLOBColumns().iterator(); i$.hasNext(); answer.addElement(resultElement)) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)i$.next();
            resultElement = new XmlElement("result");
            resultElement.addAttribute(new Attribute("column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
            resultElement.addAttribute(new Attribute("property", introspectedColumn.getJavaProperty()));
            switch (introspectedColumn.getJdbcType()){
                case -6:resultElement.addAttribute(new Attribute("jdbcType", "INTEGER"));break;
                default:resultElement.addAttribute(new Attribute("jdbcType", introspectedColumn.getJdbcTypeName()));

            }
            if(StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute("typeHandler", introspectedColumn.getTypeHandler()));
            }
        }

    }

    private void addResultMapConstructorElements(XmlElement answer) {
        XmlElement constructor = new XmlElement("constructor");

        Iterator i$;
        IntrospectedColumn introspectedColumn;
        XmlElement resultElement;
        for(i$ = this.introspectedTable.getPrimaryKeyColumns().iterator(); i$.hasNext(); constructor.addElement(resultElement)) {
            introspectedColumn = (IntrospectedColumn)i$.next();
            resultElement = new XmlElement("idArg");
            resultElement.addAttribute(new Attribute("column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
            resultElement.addAttribute(new Attribute("jdbcType", introspectedColumn.getJdbcTypeName()));
            switch (introspectedColumn.getJdbcType()){
                case -6:resultElement.addAttribute(new Attribute("jdbcType", "INTEGER"));break;
                default:resultElement.addAttribute(new Attribute("jdbcType", introspectedColumn.getJdbcTypeName()));

            }
            if(StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute("typeHandler", introspectedColumn.getTypeHandler()));
            }
        }

        for(i$ = this.introspectedTable.getNonPrimaryKeyColumns().iterator(); i$.hasNext(); constructor.addElement(resultElement)) {
            introspectedColumn = (IntrospectedColumn)i$.next();
            resultElement = new XmlElement("arg");
            resultElement.addAttribute(new Attribute("column", MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn)));
            resultElement.addAttribute(new Attribute("jdbcType", introspectedColumn.getJdbcTypeName()));
            if(introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                StringBuilder sb = new StringBuilder();
                sb.append('_');
                sb.append(introspectedColumn.getFullyQualifiedJavaType().getShortName());
                resultElement.addAttribute(new Attribute("javaType", sb.toString()));
            } else if("byte[]".equals(introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName())) {
                resultElement.addAttribute(new Attribute("javaType", "_byte[]"));
            } else {
                resultElement.addAttribute(new Attribute("javaType", introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName()));
            }

            if(StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
                resultElement.addAttribute(new Attribute("typeHandler", introspectedColumn.getTypeHandler()));
            }
        }

        answer.addElement(constructor);
    }
}
