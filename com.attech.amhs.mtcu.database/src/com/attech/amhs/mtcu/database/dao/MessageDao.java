/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.amhs.mtcu.database.dao;

import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.entity.GatewayIn;
import com.attech.amhs.mtcu.database.entity.GatewayInFails;
import com.attech.amhs.mtcu.database.entity.GatewayOut;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ANDH
 */
public class MessageDao {

    public MessageDao() {
    }

    public MessageConversionLog getByIpmId(String ipmId) throws SQLException {

        Transaction transaction = null;
        String queryString = "From MessageConversionLog WHERE ipm_id LIKE :ipmId AND type = 'AMHS' AND reference_id IS NOT NULL";
        try {
            Session session = Connection.getSession();
            transaction = session.getTransaction();
            transaction.begin();
            MessageConversionLog messageConversionLog = (MessageConversionLog) session.createQuery(queryString)
                    .setMaxResults(1)
                    .setReadOnly(true)
                    .setCacheable(false)
                    .setParameter("ipmId", ipmId)
                    .uniqueResult();
            transaction.commit();
            return messageConversionLog;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public MessageConversionLog getByMessageId(String messageId) throws SQLException {
        Transaction transaction = null;
        String queryString = "FROM MessageConversionLog WHERE message_id LIKE :messageId AND type = 'AMHS' AND reference_id IS NOT NULL";
        try {
            Session session = Connection.getSession();
            transaction = session.getTransaction();
            transaction.begin();
            // final Query q = session.createQuery("FROM MessageConversionLog WHERE message_id LIKE :messageId AND type = 'AMHS' AND reference_id IS NOT NULL").setMaxResults(1).setReadOnly(true).setCacheable(false);
            MessageConversionLog resultList = (MessageConversionLog) session.createQuery(queryString)
                    .setMaxResults(1)
                    .setReadOnly(true)
                    .setCacheable(false)
                    .setParameter("messageId", messageId)
                    .uniqueResult();
            // MessageConversionLog resultList = (MessageConversionLog) q.uniqueResult();
            // session.flush();
            // session.clear();
            transaction.commit();
            return resultList;

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    public List<MessageConversionLog> getReferenceMessage(String filingTime, String origin) throws SQLException {
        Transaction transaction = null;
        String queryString = "FROM MessageConversionLog "
                + " WHERE filing_time LIKE :time "
                + "     AND origin LIKE :origin "
                + "     AND type LIKE :type "
                + "     AND reference_id IS NOT NULL";

        // final Session session = Connection.getSession();
        try {
            Session session = Connection.getSession();
            transaction = session.getTransaction();
            transaction.begin();

            // final Query q = session.createQuery("FROM MessageConversionLog WHERE filing_time LIKE :time AND origin LIKE :origin AND type LIKE :type AND reference_id IS NOT NULL").setMaxResults(2).setReadOnly(true).setCacheable(false);;
            List<MessageConversionLog> messageConvertionLog = session.createQuery(queryString)
                    .setMaxResults(2)
                    .setReadOnly(true)
                    .setCacheable(false)
                    .setParameter("origin", origin)
                    .setParameter("time", filingTime)
                    .setParameter("type", "AFTN")
                    .list();

            // final List<MessageConversionLog> messageConvertionLog = q.list();
            // session.flush();
            // session.clear();
            // session.getTransaction().commit();
            transaction.commit();
            // session.clear();
            return messageConvertionLog;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    public List<MessageConversionLog> getReferenceMessage2(String filingTime, String origin) throws SQLException {
        final String queryString = "FROM MessageConversionLog "
                + " WHERE filing_time LIKE :time "
                + "     AND origin LIKE :origin "
                + "     AND type LIKE :type "
                + "     AND reference_id IS NOT NULL";
        Transaction transaction = null;
        // final Session session = Connection.getSession();
        try {
            Session session = Connection.getSession();
            transaction = session.getTransaction();
            transaction.begin();
            // session.getTransaction().begin();
            List<MessageConversionLog> messageConvertionLog = session.createQuery(queryString)
                    .setMaxResults(2)
                    .setReadOnly(true)
                    .setCacheable(false)
                    .setParameter("origin", origin)
                    .setParameter("time", filingTime)
                    .setParameter("type", "AFTN")
                    .list();
            // final List<MessageConversionLog> messageConvertionLog = q.list();
            // session.flush();
            // session.clear();
            transaction.commit();
            // session.getTransaction().commit();
            return messageConvertionLog;

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void save(List<GatewayOut> gatewayOuts) throws SQLException {
        Transaction transaction = null;
        // final Session session = Connection.getSession();
        try {
            Session session = Connection.getSession();
            transaction = session.getTransaction();
            transaction.begin();
            // session.getTransaction().begin();

            for (GatewayOut gatewayOut : gatewayOuts) {
                gatewayOut.setInsertedTime(new Date());
                session.save(gatewayOut);
            }
            // session.flush();
            // session.clear();
            // session.getTransaction().commit();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void save(GatewayOut gatewayOut) throws SQLException {
        Transaction transaction = null;
        // final Session session = Connection.getSession();
        try {

            Session session = Connection.getSession();
            transaction = session.getTransaction();
            transaction.begin();
            // session.getTransaction().begin();

            gatewayOut.setInsertedTime(new Date());
            session.save(gatewayOut);
            // session.flush();
            // session.clear();
            // session.getTransaction().commit();
            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void save(GatewayIn gatewayIn) throws SQLException {
        // final Session session = Connection.getSession();
        Transaction transaction = null;
        try {
            Session session = Connection.getSession();
            transaction = session.getTransaction();
            transaction.begin();

            // session.getTransaction().begin();
            session.save(gatewayIn);
            // session.flush();
            // session.clear();
            // session.getTransaction().commit();
            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    /*
    public void logMessageConversion(MessageConversionLog log) throws SQLException {
        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            final Date date = new Date();
            final String dateStr = dateFormat.format(date);
            log.setConvertedTime(date);
            log.setDate(dateStr);
            session.save(log);
            session.flush();
            session.clear();
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }
     */
    public void save(MessageConversionLog conversionLog) throws SQLException {
        Transaction transaction = null;
        try {
            Session session = Connection.getSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.save(conversionLog);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }
    
    public void save(GatewayInFails gatewayInFail) throws SQLException {
        Transaction transaction = null;
        try {
            Session session = Connection.getSession();
            transaction = session.getTransaction();
            transaction.begin();
            gatewayInFail.setCreatedTime(new Date());
            session.save(gatewayInFail);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    /*
    @SuppressWarnings("unchecked")
    public List<MessageConversionLog> getResubmitMessageList(String date, long minID) throws SQLException {
//        this.connect();
        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            Query q = session.createQuery("FROM MessageConversionLog WHERE status = 'FAIL' AND type = 'AFTN' AND date = :date AND reference_id IS NULL AND id > :minID ORDER By id ASC");
            q.setParameter("date", date);
            q.setParameter("minID", minID);
            q.setMaxResults(200);

            final List<MessageConversionLog> messageConvertionLog = q.list();
            session.flush();
            session.clear();
            session.getTransaction().commit();
            return messageConvertionLog;

        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }
     */
}
