/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool.database;

import java.io.File;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;

/**
 *
 * @author root
 */
public class Connection {

    protected static SessionFactory factory;// = configuration.buildSessionFactory(builder.build());
    private static final MInterceptor mInterceptor = new MInterceptor();

    public static void configure(String file) {
        try {
            Configuration configuration = new Configuration().configure(new File(file));
            configuration.setInterceptor(mInterceptor);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            factory = configuration.buildSessionFactory(builder.build());
        } catch (HibernateException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws SQLException {
        return factory.getCurrentSession();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    public static void setMsgTableName(String tableName) {
        mInterceptor.setName(tableName);
    }

    public static void setGwinName(String gwin) {
        mInterceptor.setGwin(gwin);
    }

    public static void setGwoutName(String gwout) {
        mInterceptor.setGwout(gwout);
    }

}
