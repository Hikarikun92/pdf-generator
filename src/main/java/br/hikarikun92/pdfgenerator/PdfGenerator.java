package br.hikarikun92.pdfgenerator;

import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfGenerator {
    public byte[] generatePdfFromHtml(String content) {
        final ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(content);
        renderer.layout();

        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            renderer.createPDF(stream);
            return stream.toByteArray();
        } catch (IOException | DocumentException e) {
            throw new IllegalArgumentException("Error converting to PDF", e);
        }
    }
}
