package com.construction.organization.report;

import lombok.extern.slf4j.Slf4j;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.ReportProcessingException;
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.csv.CSVReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlReportUtil;
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelReportUtil;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ReportService {

    public ResponseEntity processRequest(final String reportName, final MultiValueMap<String, String> queryParams) {

        final var outputTypeParam = queryParams.getFirst("output-type");
        final var reportParams = getReportParams(queryParams);

        final var outputType = StringUtils.hasText(outputTypeParam) ? outputTypeParam : "HTML";

        if (!(outputType.equalsIgnoreCase("HTML")
                || outputType.equalsIgnoreCase("PDF")
                || outputType.equalsIgnoreCase("XLS")
                || outputType.equalsIgnoreCase("XLSX")
                || outputType.equalsIgnoreCase("CSV"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No matching Output Type: " + outputType);
        }

        try {
            final var masterReport = loadReport(reportName);
            addParametersToReport(masterReport, reportParams);

            final var baos = new ByteArrayOutputStream();
            switch (outputType) {
                case "PDF":
                    PdfReportUtil.createPDF(masterReport, baos);
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(baos.toByteArray());
                case "XLS":
                    ExcelReportUtil.createXLS(masterReport, baos);
                    return ResponseEntity.ok().contentType(MediaType.ALL).body(baos.toByteArray());
                case "XLSX":
                    ExcelReportUtil.createXLSX(masterReport, baos);
                    return ResponseEntity.ok().contentType(MediaType.ALL).body(baos.toByteArray());
                case "CSV":
                    CSVReportUtil.createCSV(masterReport, baos, "UTF-8");
                    return ResponseEntity.ok().contentType(MediaType.ALL).body(baos.toByteArray());
                case "HTML":
                    HtmlReportUtil.createStreamHTML(masterReport, baos);
                    return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(baos.toByteArray());

            }
        } catch (final ResourceException | ReportProcessingException | IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No matching Output Type: " + outputType);
    }

    private MasterReport loadReport(final String reportName) throws ResourceException {
        final var manager = new ResourceManager();
        final var classloader = this.getClass().getClassLoader();
        final var reportDefinitionURL = classloader.getResource(String.format("report/%s.prpt", reportName));
        final var res = manager.createDirectly(reportDefinitionURL, MasterReport.class);
        return (MasterReport) res.getResource();
    }

    private void addParametersToReport(final MasterReport report, final Map<String, String> queryParams) {
        try {
            final var rptParamValues = report.getParameterValues();
            final var paramsDefinition = report.getParameterDefinition();
            for (final var paramDefEntry : paramsDefinition.getParameterDefinitions()) {
                final var paramName = paramDefEntry.getName();
                log.info("paramName:" + paramName);
                final var pValue = queryParams.get(paramName);
                if (!StringUtils.hasText(pValue)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pentaho Parameter: " + paramName + " - not Provided");
                }
                final var clazz = paramDefEntry.getValueType();
                log.info("addParametersToReport(" + paramName + " : " + pValue + " : " + clazz.getCanonicalName() + ")");
                if (clazz.getCanonicalName().equalsIgnoreCase("java.lang.Integer")) {
                    rptParamValues.put(paramName, Integer.parseInt(pValue));
                } else if (clazz.getCanonicalName().equalsIgnoreCase("java.lang.Long")) {
                    rptParamValues.put(paramName, Long.parseLong(pValue));
                } else if (clazz.getCanonicalName().equalsIgnoreCase("java.sql.Date")) {
                    rptParamValues.put(paramName, Date.valueOf(pValue));
                } else {
                    rptParamValues.put(paramName, pValue);
                }
            }
        } catch (final Exception e) {
            log.error("error.msg.reporting.error:" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private Map<String, String> getReportParams(final MultiValueMap<String, String> queryParams) {
        final Map<String, String> reportParams = new HashMap<>();
        final var keys = queryParams.keySet();
        String pKey;
        String pValue;
        for (final var k : keys) {
            if (k.startsWith("R_")) {
                pKey = k.substring(2);
                pValue = queryParams.get(k).get(0);
                reportParams.put(pKey, pValue);
            }
        }
        return reportParams;
    }
}
