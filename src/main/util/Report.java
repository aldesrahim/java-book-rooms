package main.util;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author aldes
 */
public class Report {

    public static void showReport(String name) throws Exception {
        showReport(name, null);
    }
    
    public static void showReport(String name, String queryString) throws Exception {
        try {
            File file = new File("src/resource/report/" + name + ".jrxml");
            String sourcePath = file.getAbsolutePath();
            
            Database db = Database.getInstance();
            db.connect();
            
            JasperDesign jd = JRXmlLoader.load(sourcePath);
            
            if (queryString != null && !queryString.isEmpty()) {
                JRDesignQuery jdQuery = new JRDesignQuery();
                jdQuery.setText(queryString);
                jd.setQuery(jdQuery);
            }
            
            JasperReport jr = JasperCompileManager.compileReport(jd);
            
            Map<String, Object> params = new HashMap<>();
            params.put(JRParameter.REPORT_LOCALE, new Locale("id", "ID"));
            params.put("REPORT_SUBTITLE", "SMI Gedung dan Ruangan - SDIT ARAFAH");
            params.put("KEPALA_SEKOLAH_NAME", "Yulianti, S.Pd");
            params.put("KEPALA_SEKOLAH_NIP", "NIP: 1230000033");
            
            JasperPrint jp = JasperFillManager.fillReport(
                    jr,
                    params,
                    db.getConnection()
            );
            
            if (jp.getPages().isEmpty()) {
                throw new Exception("Laporan kosong");
            }
            
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            throw e;
        }
    }
}
