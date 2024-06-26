/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package report.tool.gui.database;

import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static report.tool.gui.database.BaseDao.getSession;
import report.tool.gui.database.ent.DeliveryReport;

/**
 *
 * @author HONG
 * @param <DeliveryReport>
 */
public class DeliveryReportDao extends BaseDao<DeliveryReport> {
    
    public DeliveryReportDao() {
        super();
        this.clazz = DeliveryReport.class;
    }
    
    public void save(DeliveryReport t) throws SQLException {
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
}
