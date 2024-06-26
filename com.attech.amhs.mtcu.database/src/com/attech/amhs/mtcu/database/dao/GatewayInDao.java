/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.dao;

import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.entity.GatewayIn;
import com.attech.amhs.mtcu.database.entity.GatewayInFails;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Do all the DB Job in this class
 *
 * @author andh
 */
public class GatewayInDao {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<GatewayIn> getGatewayInMessages(int maxResult) throws SQLException {
        final Session session = Connection.getSession();
        try {

            session.getTransaction().begin();
            final Query q = session.createQuery("FROM GatewayIn as GatewayIn ORDER BY PRIORITY, MSGID ASC").setMaxResults(maxResult).setCacheable(false).setReadOnly(true);
            final List resultList = q.list();
            session.flush();
            session.clear();
            session.getTransaction().commit();
            return resultList;

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    public void save(GatewayIn gatewayIn) throws SQLException {
        final Session session = Connection.getSession();
        try {

            session.getTransaction().begin();
            session.save(gatewayIn);
            session.flush();
            session.clear();
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    public void remove(GatewayIn gatewayIn) throws SQLException {
        final Session session = Connection.getSession();
        try {

            session.getTransaction().begin();
            session.delete(gatewayIn);
            session.flush();
            session.clear();
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }
    
    public void save(GatewayInFails gatewayInFail) throws SQLException {
        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            session.save(gatewayInFail);
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

}
