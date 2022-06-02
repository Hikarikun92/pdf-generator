package br.hikarikun92.pdfgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Map;

@RestController
@RequestMapping("pdf")
public class PdfController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfController.class);

    private final HtmlGenerator htmlGenerator;
    private final PdfGenerator pdfGenerator;

    public PdfController(HtmlGenerator htmlGenerator, PdfGenerator pdfGenerator) {
        this.htmlGenerator = htmlGenerator;
        this.pdfGenerator = pdfGenerator;
    }

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> createPdf(@RequestParam String name, @RequestParam int age) {
        final Map<String, Object> variables = Map.of("name", name, "age", age);
        final String html = htmlGenerator.generateHtmlFromTemplate("example", variables);

        LOGGER.debug("Generated HTML:\n{}", html);

        final byte[] pdf = pdfGenerator.generatePdfFromHtml(html);
        final ByteArrayResource resource = new ByteArrayResource(pdf);

        final String filename = "your_data.pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
