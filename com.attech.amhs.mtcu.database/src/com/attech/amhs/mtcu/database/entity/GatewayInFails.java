/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 *
 * 
 * 
 * @author andh
 */
@Entity
@Table(name = "gwin_fails", indexes = {@Index(columnList = "ID", name = "gwin_fails_idx_ip")})
public class GatewayInFails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cpa", nullable = false, length = 1)
    private String cpa;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "time")
    private Date time;

    @Column(name = "text", length = 2200)
    private String text;

    @Column(name = "source", length = 200)
    private String source;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "error_detail", length = 10000)
    private String errorDetail;

    @Column(name = "created_time")
    private Date createdTime;
    
    public GatewayInFails () {
    }
    
    public GatewayInFails(GatewayIn gatewayIn) {
        this.cpa = gatewayIn.getCpa();
        this.priority = gatewayIn.getPriority();
        this.time = gatewayIn.getTime();
        this.text = gatewayIn.getText();
        this.source = gatewayIn.getSource();
        this.address = gatewayIn.getAddress();
    }

    //<editor-fold defaultstate="collapsed" desc="Class Properties">
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the cpa
     */
    public String getCpa() {
        return cpa;
    }

    /**
     * @param cpa the cpa to set
     */
    public void setCpa(String cpa) {
        this.cpa = cpa;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the errorDetail
     */
    public String getErrorDetail() {
        return errorDetail;
    }

    /**
     * @param errorDetail the errorDetail to set
     */
    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    /**
     * @return the createdTime
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    //</editor-fold>

}



/*
-- amss.gwin_fails definition

CREATE TABLE `gwin_fails` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cpa` varchar(1) CHARACTER SET latin1 NOT NULL,
  `priority` tinyint(4) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `text` varchar(2200) CHARACTER SET latin1 DEFAULT NULL,
  `source` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `address` varchar(250) CHARACTER SET latin1 DEFAULT NULL,
  `error_detail` varchar(10000) CHARACTER SET latin1 DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gwin_fails_idx_ip` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
*/
