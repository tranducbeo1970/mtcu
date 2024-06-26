/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.dao;

import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.entity.GatewayOut;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author andh
 */
public class GatewayOutDao {

    public boolean save(GatewayOut gatewayOut) throws SQLException {
        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            session.save(gatewayOut);
            session.flush();
            session.clear();
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    public List<GatewayOut> getGatewayOut(long maxid) throws SQLException {

        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            final Query query = session.createQuery("From GatewayOut WHERE msgid > :id").setCacheable(false).setReadOnly(true);
            query.setParameter("id", maxid);
            List<GatewayOut> gatewayouts = query.list();
            session.flush();
            session.clear();
            session.getTransaction().commit();
            return gatewayouts;

        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            throw ex;
        }
    }

    public void clearGatewayOut() throws SQLException {

        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            final SQLQuery query = session.createSQLQuery("DELETE FROM gwout");
            query.executeUpdate();
            session.flush();
            session.clear();
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            throw ex;
        }
    }

}
