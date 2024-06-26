/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.dao;

import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.entity.AftnConfig;
import com.attech.amhs.mtcu.database.entity.AftnMessage;
import com.attech.amhs.mtcu.database.entity.SysConfig;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author andh
 */
public class AFTNMessageDao {

    public AFTNMessageDao() {
    }

    public AftnMessage getDeliveriedMessage(String cid, int sequence) throws SQLException {

        final Session session = Connection.getSession();
        try {

            session.getTransaction().begin();
            Query q = session.createQuery("FROM AftnMessage WHERE cid=:cid AND csn=:sequence AND flag=:flag").setMaxResults(1).setCacheable(false).setReadOnly(true);
            q.setParameter("cid", cid);
            q.setParameter("sequence", sequence);
            q.setParameter("flag", "T");

            AftnMessage aftnMessage = (AftnMessage) q.uniqueResult();
            session.getTransaction().commit();
            return aftnMessage;

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    public String getAFTNAddressIndicator() throws SQLException {

        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            final Query q = session.createQuery("From SysConfig").setMaxResults(1).setCacheable(false).setReadOnly(true);
            SysConfig config = (SysConfig) q.uniqueResult();
            session.getTransaction().commit();
            return config == null ? null : config.getSystemOrigin();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    public List<AftnConfig> getAFTNConfig() throws SQLException {

        final Session session = Connection.getSession();

        try {

            session.getTransaction().begin();
            Query q = session.createQuery("FROM AftnConfig WHERE enabled='Y'").setCacheable(false).setReadOnly(true);;

            List<AftnConfig> AftnchannelList = q.list();
            session.getTransaction().commit();

            //System.out.println("..............");
            for (int i = 0; i < AftnchannelList.size(); i++) {

                int id = AftnchannelList.get(i).getId();
                String content = AftnchannelList.get(i).getCCT();
                String tmp = AftnchannelList.get(i).getADDRESS().replace(" ", "|^");
                String add = "^" + tmp;
                AftnchannelList.get(i).setAddress_pattern(add);
                //System.out.println(Integer.toString(id) + "-" + content + "-" + add);

            }
            //System.out.println("..............");
            return AftnchannelList;

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

    }

    @SuppressWarnings("unchecked")
    public void printFplMessage() throws SQLException {

        final Session session = Connection.getSession();

        try {

            session.getTransaction().begin();
            Query q = session.createQuery("FROM AftnMessage WHERE text like '%(FPL%'").setCacheable(false).setReadOnly(true);;

            List<AftnMessage> resultList = q.list();
            session.getTransaction().commit();

            for (AftnMessage aftnMessage : resultList) {
                String content = aftnMessage.getText().replace("\n", "[n]\n").replace("\r", "[r]");
//                System.out.println("----------------------------------------------------");
//                System.out.println(content);
            }

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    public List<AftnMessage> getAftnMessage(String tablename, List<String> addresses) throws SQLException {

        String queryString = String.format("FROM AftnMessage WHERE ", tablename);
        String and = "";
        for (String address : addresses) {
            queryString += (and + " TEXT like '%" + address + "%'");
            and = " OR";
        }

        final Session session = Connection.getSession();

        try {
            session.getTransaction().begin();
            Connection.setMsgTableName(tablename);
            Query query = session.createQuery(queryString).setCacheable(false).setReadOnly(true);;
            List<AftnMessage> resultList = query.list();
            session.getTransaction().commit();
            return resultList;

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }
}
