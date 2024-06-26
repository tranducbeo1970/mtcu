/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.mt;

import com.attech.amhs.mtcu.database.Connection;
import com.attech.amhs.mtcu.database.dao.MessageDao;
import com.attech.amhs.mtcu.database.entity.MessageConversionLog;
import java.text.DecimalFormat;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andh2
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Connection.configure("config/database.xml");
    }
    
    @After
    public void tearDown() {
    }
    
     @Test
    public void testGetReferenceMessage() throws Exception {
        MessageDao dao = new MessageDao();
        DecimalFormat df2 = new DecimalFormat( "###,###,###,##0" );
        Long time1 = System.nanoTime();
        List<MessageConversionLog> log = dao.getReferenceMessage2("220416", "VVTSHCPY");
        Long time2 = System.nanoTime();
        System.out.println(String.format("Select IN %s nanosecond item count: %s", df2.format(time2 - time1), log.size()));
        assertNotNull(log);
        // assertNotNull(log.getParents());
    }

}
