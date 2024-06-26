/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package report.tool.database;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author HONG
 */
public class DeliveryReportDao {

    public List<DeliveryReport> get() throws SQLException {
        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<DeliveryReport> resultList = (List<DeliveryReport>) session.createQuery("From DeliveryReport")
                    .list();
            session.getTransaction().commit();
            return resultList;
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    public void delete(DeliveryReport deliveryReport) throws SQLException {
        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            session.delete(deliveryReport);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

    public void insert(DeliveryReport deliveryReport) throws SQLException {
        final Session session = Connection.getSession();
        try {
            session.getTransaction().begin();
            session.save(deliveryReport);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }

}
