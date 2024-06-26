/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.attech.amhs.mtcu.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "config")
public class AftnConfig  implements Serializable{

    /**
     * @return the address_pattern
     */
    public String getAddress_pattern() {
        return address_pattern;
    }

    /**
     * @param address_pattern the address_pattern to set
     */
    public void setAddress_pattern(String address_pattern) {
        this.address_pattern = address_pattern;
    }

    @Transient
    private  String address_pattern;        
    
    /**
     * @return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * @return the CCT
     */
    public String getCCT() {
        return CCT;
    }

    /**
     * @param CCT the CCT to set
     */
    public void setCCT(String CCT) {
        this.CCT = CCT;
    }

    /**
     * @return the ADDRESS
     */
    public String getADDRESS() {
        return ADDRESS;
    }

    /**
     * @param ADDRESS the ADDRESS to set
     */
    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 1)
    private int Id;
    
    
    @Column(name = "cct", length = 4)
    private String CCT;
    
    
    @Column(name = "address", length = 1200)
    private String ADDRESS;
    
    
    public AftnConfig () {
    }
    
}
