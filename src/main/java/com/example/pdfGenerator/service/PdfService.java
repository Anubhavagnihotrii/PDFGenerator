package com.example.pdfGenerator.service;

import com.example.pdfGenerator.dto.InvoiceRequest;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.model.IText;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class PdfService {
    private final TemplateEngine templateEngine;
    private static final String PDF_DIRECTORY = "src/main/resources/static/pdfs/";

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generatePdf(InvoiceRequest request){
        String filename = PDF_DIRECTORY+request.getBuyer()+"_invoice.pdf";
        Path pdfPath = Paths.get(filename);

        if(Files.exists(pdfPath)){
            return filename;
        }

        // Calculate total amount using InvoiceCalculator
        BigDecimal totalAmount = InvoiceCalculator.calculateTotalAmount(request);


        Context context = new Context();
        context.setVariable("seller", request.getSeller());
        context.setVariable("sellerGstin", request.getSellerGstin());
        context.setVariable("sellerAddress", request.getSellerAddress());
        context.setVariable("buyer", request.getBuyer());
        context.setVariable("buyerGstin", request.getBuyerGstin());
        context.setVariable("buyerAddress", request.getBuyerAddress());
        context.setVariable("items", request.getItems());
        context.setVariable("totalAmount", totalAmount);

        String htmlContent = templateEngine.process("invoice", context);

        try{
            OutputStream outputStream = Files.newOutputStream(pdfPath);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return filename;
    }
}
