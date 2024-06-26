/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author ANDH
 */
public class AssemblyInfo {
    
    private String product;
    private String company;
    private String version;
    private String buildDate;
    private String buildBy;
    private String svnRevision;
    private String createBy;
    private String url;
    
    public  AssemblyInfo() {
    }
    
    public AssemblyInfo(String file) throws IOException {
        load(file);
    }
    
    public void load(String jarFile) throws IOException {
        java.io.File file = new java.io.File(jarFile);
        if (!file.exists()) {
            return;
        }
        
        java.util.jar.JarFile jar = new java.util.jar.JarFile(file);
        java.util.jar.Manifest manifest = jar.getManifest();
        loadAttribute(manifest.getMainAttributes());
        jar.close();
    }

    private Map<String,String> loadAttribute(java.util.jar.Attributes attributes) {
        Map<String, String> map = new HashMap<String, String>();
        Iterator<Object> it = attributes.keySet().iterator();
        while (it.hasNext()) {
            java.util.jar.Attributes.Name key = (java.util.jar.Attributes.Name) it.next();
            Object value = attributes.get(key);
            // map.put(key.toString(), value.toString());
            
            switch (key.toString().toLowerCase()) {
                case "project-name":
                    product = value.toString();
                    break;
                case "company":
                    company = value.toString();
                    break;
                case "version":
                    version = value.toString();
                    break;
                case "built-date":
                    buildDate = value.toString();
                    break;
                case "built-by":
                    buildBy = value.toString();
                    break;
                case "svn-revision":
                    svnRevision = value.toString();
                    break;
                case "created-by":
                    createBy = value.toString();
                    break;
                case "url":
                    url = value.toString();
                    break;
            }
        }
        return map;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return the buildDate
     */
    public String getBuildDate() {
        return buildDate;
    }

    /**
     * @return the buildBy
     */
    public String getBuildBy() {
        return buildBy;
    }

    /**
     * @return the svnRevision
     */
    public String getSvnRevision() {
        return svnRevision;
    }

    /**
     * @return the createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
}
