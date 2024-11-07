package com.example.pdfGenerator.controllers;

import com.example.pdfGenerator.dto.InvoiceRequest;
import com.example.pdfGenerator.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class PdfController {
    private final PdfService pdfService;

    @Autowired
    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody InvoiceRequest request){
        String pdfFilename = pdfService.generatePdf(request);
        if(pdfFilename==null){
            return new ResponseEntity<>("Error in generating PDF", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  new ResponseEntity<>("PDF generated Successfully" +pdfFilename,HttpStatus.CREATED);
    }
}
