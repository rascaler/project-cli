package com.redding.tool.mybatis.generate.resolver;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * Created by Leven on 2017-02-17.
 */
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {


    @Override
    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        JdbcTypeInformation jdbcTypeInformation = this.typeMap.get(Integer.valueOf(introspectedColumn.getJdbcType()));
        FullyQualifiedJavaType answer;
        if(jdbcTypeInformation != null) {
            switch(introspectedColumn.getJdbcType()) {
                case -6:
                    answer = new FullyQualifiedJavaType(Integer.class.getName());break;
                default:
                    answer = jdbcTypeInformation.getFullyQualifiedJavaType();

            }
        }else{
            answer = jdbcTypeInformation.getFullyQualifiedJavaType();
        }
        return answer;
    }

}
