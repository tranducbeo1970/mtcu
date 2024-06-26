/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ANDH
 */
@Entity
@Table(name = "common")
public class SysConfig implements Serializable {

    private static final long serialVersionUID = -426456804541097293L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_id", nullable = false, length = 1)
    private String systemId;

    @Column(name = "system_origin", length = 8)
    private String systemOrigin;

    //<editor-fold defaultstate="collapsed" desc="Class property methods">
    /**
     * @return the systemId
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * @param systemId the systemId to set
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * @return the systemOrigin
     */
    public String getSystemOrigin() {
        return systemOrigin;
    }

    /**
     * @param systemOrigin the systemOrigin to set
     */
    public void setSystemOrigin(String systemOrigin) {
        this.systemOrigin = systemOrigin;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemId != null ? systemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SysConfig)) {
            return false;
        }
        SysConfig other = (SysConfig) object;
        return !((this.systemId == null && other.systemId != null) || (this.systemId != null && !this.systemId.equals(other.systemId)));
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Areas[ id=" + systemId + " ]";
    }

}
