package com.example.pdfGenerator.service;

import com.example.pdfGenerator.dto.InvoiceRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PdfService {
    private final TemplateEngine templateEngine;
    private static final AtomicLong idGenerator = new AtomicLong(1);

    @Value("${pdf.directory}")
    private String PDF_DIRECTORY;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generatePdf(InvoiceRequest request) {
        if (request.getInvoiceId() == null) {
            request.setInvoiceId(idGenerator.getAndIncrement());
        }

        String encodedFileName = URLEncoder.encode(request.getInvoiceId() + "_invoice.pdf", StandardCharsets.UTF_8);
        String filename = PDF_DIRECTORY + encodedFileName;
        Path pdfPath = Paths.get(filename);

        if (Files.exists(pdfPath)) {
            return encodedFileName;
        }

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

        try {
            OutputStream outputStream = Files.newOutputStream(pdfPath);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return encodedFileName;
    }
}
