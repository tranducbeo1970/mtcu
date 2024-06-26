/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amhs.mtcu.utils.test;

import amhs.database.CommonUtils;
import amhs.mt.AddressConvertor;
import amhs.mt.parameters.DRParameter;
import amhs.mt.MtAttributes;
import amhs.mt.MtSessionManager;
import amhs.mt.ReportBuilder;
import amhs.mt.ReportRecipient;
import amhs.mtcu.config.AddressConversion;
import amhs.mtcu.config.Config;
import com.isode.x400api.X400_att;
import com.isode.x400mtapi.MTMessage;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class TestSendReport {

    public static void main(String[] args) {
        DOMConfigurator.configure("config/log.xml");
        // CommonUtils.configure("config/database.xml");
        Config.configure("config/gateway.xml");

        // AddressConversion addressConvertCfg = GatewayConfig.instance.getAddressConversion();
        // AddressConvertor.connect(addressConvertCfg.getDsaPresentationAdd(), addressConvertCfg.getDnName());

        // Open MTA session
        String origin = "/CN=BAAAFTAA/OU=BAAA/O=BA-REGION/PRMD=AFTNLAND-1/ADMD=ICAO/C=XX/";
        // String origin = "/CN=AAAAMHBA/OU=AAAA/O=AA-REGION/PRMD=UNKNOWN/ADMD=ICAO/C=XX/";
        String subjectId = "[/PRMD=VV/ADMD=ICAO/C=XX/;gateway.attech.518-140619.055033]";
        
        DRParameter parameter = new DRParameter(origin, subjectId);
        ReportRecipient report = new ReportRecipient("/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=IUTLAND/ADMD=ICAO/C=XX/", MtAttributes.RS_UNABLE_TO_TRANSFER, MtAttributes.D_UNRECOGNISED_OR_NAME, "");
        // ReportRecipient report = new ReportRecipient("/CN=IUTAMHAA/OU=IUTA/O=IUT-REGION/PRMD=UNKNOWN/ADMD=ICAO/C=XX/", MtAttributes.RS_UNABLE_TO_TRANSFER, MtAttributes.D_UNRECOGNISED_OR_NAME, "");
        report.setOriginReportRequest(1);
        report.setMtaReportRequest(1);
        parameter.add(report);
        MtSessionManager.open();
        
        MTMessage message = MtSessionManager.newMessage(X400_att.X400_MSG_REPORT);
        ReportBuilder.buildReport(message, parameter);
        int rst = MtSessionManager.deliver(message);
        
        MtSessionManager.close();
        
        if (rst == X400_att.X400_E_NOERROR) {
            System.out.println("Send success");
        } else {
            System.out.println("Send fail");
        }
    }
}
