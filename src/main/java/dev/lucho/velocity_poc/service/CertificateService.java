package dev.lucho.velocity_poc.service;

import static com.itextpdf.text.pdf.PdfTemplate.createTemplate;
import static org.hibernate.cfg.AvailableSettings.DATASOURCE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import dev.lucho.velocity_poc.model.TemplateDTO;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.DataSourceResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {

    @Autowired
    private TemplateService templateService;

    public byte[] createCertificate(UUID id) throws IOException, DocumentException {
        TemplateDTO template = templateService.get(id);

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loaders", "ds");
        velocityEngine.setProperty("resource.loader.ds.class",
                "org.apache.velocity.runtime.resource.loader.DataSourceResourceLoader");
        velocityEngine.setProperty("resource.loader.ds.datasource_url", "jdbc:h2:mem:Velocity-PoC");
        velocityEngine.setProperty("resource.loader.ds.resource.table", "templates");
        velocityEngine.setProperty("resource.loader.ds.resource.key_column", "name");
        velocityEngine.setProperty("resource.loader.ds.resource.template_column", "content");
        velocityEngine.setProperty("resource.loader.ds.cache", "false");
        velocityEngine.setProperty("resource.loader.ds.modification_check_interval", "0");

        velocityEngine.init();

        System.out.println(velocityEngine.getProperty(RuntimeConstants.RESOURCE_LOADER));

        Template template1 = velocityEngine.getTemplate(template.getName());

        VelocityContext context = new VelocityContext();

        // Create dataModel
        context.put("logo", "https://placehold.co/200x25/F1F1F1/000?text=Blankfactor&font=raleway");

        Map<String, Object> certificate = new HashMap<>();
        certificate.put("expeditionDate", "2021-01-01");
        certificate.put("addressedTo", "Mike Doe");
        certificate.put("withSalary", false);

        context.put("certificate", certificate);

        Map<String, Object> employee = new HashMap<>();
        employee.put("names", "Big Joe");
        employee.put("governmentId", "123456789");
        employee.put("position", "Software Engineer");
        employee.put("startDate", "2021-01-01");
        employee.put("salaryInLetters", "One thousand");
        employee.put("salaryInNumbers", "1000");

        context.put("employee", employee);

        Map<String, Object> signer = new HashMap<>();
        signer.put("name", "John Doe");
        signer.put("position", "CEO");
        signer.put("email", "jhon@email.com");
        signer.put("signature", "https://placehold.co/280x70/F1F1F1/000?text=Signer%201&font=raleway");

        context.put("signer", signer);

        // Process the template
        StringWriter stringWriter = new StringWriter();
        template1.merge(context, stringWriter);

        // Create a PDF document using iText
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, pdfOutputStream);

        HtmlConverter.convertToPdf(stringWriter.toString(), pdfOutputStream);

        return pdfOutputStream.toByteArray();

    }

}
