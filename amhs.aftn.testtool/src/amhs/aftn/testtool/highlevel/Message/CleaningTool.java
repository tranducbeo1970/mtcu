/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

/**
 *
 * @author andh
 */
public class CleaningTool {
    public static void main(String [] args) {
        
        X400MS.deleteMessage("message/accounts/IUTAAMHD.account.xml");
        X400MS.deleteMessage("message/accounts/IUTAMHAA.account.xml");
        X400MS.deleteMessage("message/accounts/IUTAMHAB.account.xml");
        // X400MS.deleteMessage("message/accounts/IUTAMHAC.account.xml");
        X400MS.deleteMessage("message/accounts/IUTAMHSE.account.xml");
        X400MS.deleteMessage("message/accounts/IUTAYFYX.account.xml");
        System.out.println("Done.");
    }
}
