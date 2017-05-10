package com.jwx.vaccine.support.core.domain;

import java.math.BigDecimal;
import javax.persistence.*;

public class Company {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "SELECT COMPANY_SEQ.NEXTVAL FROM DUAL")
    private BigDecimal id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMPLOYEE_NUM")
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