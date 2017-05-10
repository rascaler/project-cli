package com.jwx.vaccine.support.model.pojo.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CompanyDto implements Serializable {
    private BigDecimal id;

    private String name;

    private String address;

    private Long employeeNum;

    /**
     * @return ID
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return ADDRESS
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return EMPLOYEE_NUM
     */
    public Long getEmployeeNum() {
        return employeeNum;
    }

    /**
     * @param employeeNum
     */
    public void setEmployeeNum(Long employeeNum) {
        this.employeeNum = employeeNum;
    }
}