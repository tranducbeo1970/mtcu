/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.aftn.testtool;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for connect to Ms Access to get data
 *
 * @author andh
 */
public class MsAccessConnector {

    // Contants
    private final static String connectionClass = "sun.jdbc.odbc.JdbcOdbcDriver";
    private final static String connectionStringFormat = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=%s;";

    // Database filename
    private String fileName;

    /**
     * Contructor with database file path
     *
     * @param fileName : the path and name of ms file
     */
    public MsAccessConnector(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Make connection String
     *
     * @param fileName
     * @return String which store connectino information
     */
    public String getConnectionString(String fileName) {
        return String.format(connectionStringFormat, fileName);
    }

    /**
     * Execute query
     *
     * @param query
     * @return Result set
     */
    public ResultSet executeQuery(String query) {
        try {
            Class.forName(connectionClass);
            String connectionString = getConnectionString(this.fileName);
            Connection conn = DriverManager.getConnection(connectionString, "", "");
            Statement s = conn.createStatement();

            String selTable = query;
            s.execute(selTable);
            ResultSet rs = s.getResultSet();
            // close and cleanup
            s.close();
            conn.close();

            return rs;
        } catch (ClassNotFoundException | SQLException ex) {

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);

            return null;
        }
    }

    public List<String> getAllMessage() {

        List<String> messages = new ArrayList<>();
        try {
            Class.forName(connectionClass);
            String connectionString = getConnectionString(this.fileName);
            Connection conn = DriverManager.getConnection(connectionString, "", "");
            Statement s = conn.createStatement();

            String selTable = "SELECT * FROM table1";
            s.execute(selTable);
            ResultSet rs = s.getResultSet();

            while ((rs != null) && (rs.next())) {
                String message = rs.getString(6);
                messages.add(message);
            }

            // close and cleanup
            s.close();
            conn.close();

            return messages;
        } catch (ClassNotFoundException | SQLException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            return null;
        }
    }
}
