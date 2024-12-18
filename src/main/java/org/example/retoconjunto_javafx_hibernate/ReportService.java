package org.example.retoconjunto_javafx_hibernate;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que se encarga de generar un reporte en formato PDF a partir de un archivo Jasper.
 */
public class ReportService {

    /**
     * Método que genera un reporte en formato PDF a partir de un archivo Jasper.
     * @param data
     * @param reportTitle
     * @param logo
     * @throws JRException
     */
    public void generateReport(List<?> data, String reportTitle, InputStream logo) throws JRException {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", reportTitle);
        parameters.put("Logo", logo);
        parameters.put("ReportDate", new Date());

        JasperPrint jasperPrint = JasperFillManager.fillReport("listado_peliculas.jasper", parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint,"listado_peliculas.pdf");
    }
}
