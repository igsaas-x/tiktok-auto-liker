package com.construction.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SimpleReportService {

    @Autowired
    private SimpleReportFiller reportFiller;
    @Autowired
    private SimpleReportExporter reportExporter;

    public void generate(){
        reportFiller.setReportFileName("employeeEmailReport.jrxml");
        reportFiller.compileReport();

        reportFiller.setReportFileName("employeeReport.jrxml");
        reportFiller.compileReport();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Employee Report Example");
        parameters.put("minSalary", 15000.0);
        parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

        reportFiller.setParameters(parameters);
        reportFiller.fillReport();

        reportExporter.setJasperPrint(reportFiller.getJasperPrint());

        reportExporter.exportToPdf("employeeReport.pdf", "chanheng");
        reportExporter.exportToXlsx("employeeReport.xlsx", "Employee Data");
        reportExporter.exportToCsv("employeeReport.csv");
        reportExporter.exportToHtml("employeeReport.html");
    }
}
