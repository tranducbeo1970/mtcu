/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.tool.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.EmptyInterceptor;

/**
 *
 * @author ANDH
 */
public class MInterceptor extends EmptyInterceptor {




    /**
     *
     */
    private static final long serialVersionUID = -9018221493407415126L;


    private String name;
    private String gwin;
    private String gwout;
    private final SimpleDateFormat datetimeFormater = new SimpleDateFormat("ddMMyyyy");

    @Override
    public String onPrepareStatement(String sql) {
        if (this.name == null || this.name.isEmpty()) {
            this.name = String.format("MSG%s", datetimeFormater.format(new Date()));
        }
        
        String prepedStatement = super.onPrepareStatement(sql);
        prepedStatement = prepedStatement.replaceAll("MSGX", this.name);
        if (this.gwin != null && !this.gwin.isEmpty()) {
            prepedStatement = prepedStatement.replaceAll("gwin", this.gwin);
//            System.out.println("Replace GWIN to " + this.gwin);
        }
        
        if (this.gwout != null && !this.gwout.isEmpty()) {
            prepedStatement = prepedStatement.replaceAll("gwout", this.gwout);
//            System.out.println("Replace GWOUT to " + this.gwout);
        }
        
        return prepedStatement;
    }
    
    
    /**
     * @param gwin the gwin to set
     */
    public void setGwin(String gwin) {
        this.gwin = gwin;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param gwout the gwout to set
     */
    public void setGwout(String gwout) {
        this.gwout = gwout;
    }
}
