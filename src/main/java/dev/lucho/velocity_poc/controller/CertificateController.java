package dev.lucho.velocity_poc.controller;

import java.io.IOException;
import java.util.UUID;

import com.itextpdf.text.DocumentException;
import dev.lucho.velocity_poc.service.CertificateService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/certificate", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/generate-pdf/{templateId}")
    public void generatePdf(
            @PathVariable
            UUID templateId, HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=certificate.pdf");

        try {
            byte[] result = certificateService.createCertificate(templateId);
            // Write the PDF to the response
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(result);
            outputStream.close();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
