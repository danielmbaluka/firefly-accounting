package com.tri.erp.spring.service.implementations;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class DownloadService {
	protected final Logger logger = Logger.getLogger("service");
	
	@Autowired
	private ExporterService exporter;
	
	@Autowired
	private TokenService tokenService;

    @Autowired
    Environment env;
	
	public void download(String type, String token,
                         HttpServletResponse response, HashMap<String, Object> params,
                         String template, JRDataSource dataSource) {
		 
		try {

			InputStream reportStream = this.getClass().getResourceAsStream(template);
			JasperDesign jd = JRXmlLoader.load(reportStream);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			// Make sure to pass the JasperReport, report parameters, and data source
            JasperPrint jp = null;
            if (dataSource != null) {
                jp = JasperFillManager.fillReport(jr, params, dataSource);
            } else {
                jp = JasperFillManager.fillReport(jr, params, dataSource);
            }
			// Create an output byte stream where data will be written
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// Export report
			exporter.export(type, jp, response, baos);
			// Write to response stream
			write(token, response, baos);
		
		} catch (JRException jre) {
			logger.error("Unable to process download");
			throw new RuntimeException(jre);
		}
	}

    public void download(String type, String token, HttpServletResponse response, HashMap<String, Object> params,
                         String template, JRDataSource dataSource, Boolean withTempPdf) {

        try {
             if (withTempPdf) {
                // Create an output byte stream where data will be written
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                String newPdfFilename = "/bir2307-" + (new SimpleDateFormat("MMMddyyyy").format(new Date())) + ".pdf";

                PdfReader.unethicalreading = true;
                PdfReader pdfReader = new PdfReader(template);
                PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);

                // write contents here:
                BaseFont font = BaseFont.createFont();
                PdfContentByte overContent = pdfStamper.getOverContent(1);
                overContent.saveState();
                overContent.beginText();
                overContent.setFontAndSize(font, 10.0f);
                overContent.moveText(115, 845);
                overContent.showText(params.get("PAYEE_NAME").toString());
                overContent.endText();
                overContent.restoreState();

                pdfStamper.close();
                pdfReader.close();

                // Set our response properties
                response.setHeader("Content-Disposition", "inline; filename="+ newPdfFilename);

                // Set content type
                response.setContentType("application/pdf");
                response.setContentLength(baos.size());

                // Write to response stream
                OutputStream os = response.getOutputStream();
                baos.writeTo(os);
                os.flush();

                tokenService.remove(token);
            } else {

                InputStream reportStream = this.getClass().getResourceAsStream(template);
                JasperDesign jd = JRXmlLoader.load(reportStream);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                // Make sure to pass the JasperReport, report parameters, and data source
                JasperPrint jp = null;
                if (dataSource != null) {
                    jp = JasperFillManager.fillReport(jr, params, dataSource);
                } else {
                    jp = JasperFillManager.fillReport(jr, params, dataSource);
                }
                // Create an output byte stream where data will be written
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // Export report
                exporter.export(type, jp, response, baos);
                // Write to response stream
                write(token, response, baos);
            }
        } catch (JRException jre) {
            logger.error("Unable to process download");
            jre.printStackTrace();
        } catch (IOException e) {
            logger.error("Unable to process download");
            e.printStackTrace();
        } catch (DocumentException e) {
            logger.error("Unable to process download");
            e.printStackTrace();
        }
    }
	
	/**
	* Writes the report to the output stream
	*/
	private void write(String token, HttpServletResponse response, ByteArrayOutputStream baos) {
		 
		try {
			// Retrieve output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// Write to output stream
			baos.writeTo(outputStream);
			// Flush the stream
			outputStream.flush();
			
			// Remove download token
			tokenService.remove(token);
			
		} catch (Exception e) {
			logger.error("Unable to write report to the output stream");
			throw new RuntimeException(e);
		}
	}
}
