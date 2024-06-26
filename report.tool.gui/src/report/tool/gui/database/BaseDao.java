/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool.gui.database;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author root
 * @param <T>
 */
public abstract class BaseDao<T> {

    protected Class<T> clazz;

    protected static SessionFactory factory;// = configuration.buildSessionFactory(builder.build());

    public static void configure(String file) {
        Configuration configuration = new Configuration().configure(new File(file));
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        factory = configuration.buildSessionFactory(builder.build());

    }

    public static Session getSession() throws SQLException {
        return factory.getCurrentSession();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public void insert(T t) throws SQLException {
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(t);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
        }
    }

    public void delete(T t) throws SQLException {
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(t);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public List<T> selectAll() throws SQLException {
        Session session = getSession();
        Transaction tx = null;
        List<T> resultList = null;
        try {
            tx = session.beginTransaction();
            resultList = session.getNamedQuery(this.clazz.getSimpleName() + ".SELECT_ALL").list();
            tx.commit();
            return resultList;
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

    public List<T> selectAll(int limit) throws SQLException {
        Session session = getSession();
        Transaction tx = null;
        List<T> resultList = null;
        try {
            tx = session.beginTransaction();
            resultList = session.getNamedQuery(this.clazz.getSimpleName() + ".SELECT_ALL")
                    .setMaxResults(limit)
                    .list();
            tx.commit();
            return resultList;
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }
    
    public T selectById(int id) throws SQLException {
        Session session = getSession();
        Transaction tx = null;
        T resultList = null;
        try {
            tx = session.beginTransaction();
            resultList = (T) session.getNamedQuery(this.clazz.getSimpleName() + ".SELECT_BY_ID")
                    .setParameter("id", id)
                    .setMaxResults(1)
                    .uniqueResult();
            tx.commit();
            return resultList;
        } catch (Exception ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        }
    }

}
