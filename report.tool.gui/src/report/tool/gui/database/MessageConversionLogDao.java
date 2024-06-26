/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool.gui.database;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static report.tool.gui.database.BaseDao.getSession;
import report.tool.gui.database.ent.MessageConversionLog;

/**
 *
 * @author ANDH
 */
public class MessageConversionLogDao extends BaseDao<MessageConversionLog> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    public MessageConversionLogDao() {
        this.clazz = MessageConversionLog.class;
    }

    public List<MessageConversionLog> getIpm(int limit) throws SQLException {
        Session session = getSession();
        Transaction tx = null;
        List<MessageConversionLog> resultList = null;
        try {
            tx = session.beginTransaction();
            resultList = session.getNamedQuery("MessageConversionLog.SELECT_ALL_IPM")
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

}
