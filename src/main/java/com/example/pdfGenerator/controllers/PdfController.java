package com.example.pdfGenerator.controllers;

import com.example.pdfGenerator.dto.InvoiceRequest;
import com.example.pdfGenerator.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/invoices")
public class PdfController {

    @Autowired
    PdfService pdfService;
    @Value("${pdf.directory}")
    private String PDF_DIRECTORY;

    @PostMapping("/generate")
    public ResponseEntity<String> generateInvoice(@RequestBody InvoiceRequest request) {
        String filename = pdfService.generatePdf(request);
        if (filename == null) {
            return new ResponseEntity<>("Failed to generate PDF", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(filename, HttpStatus.CREATED);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadInvoice(@RequestParam("id") long id) {
        String filename = PDF_DIRECTORY + id + "_invoice.pdf";
        File file = new File(filename);

        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] pdfContent = inputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
