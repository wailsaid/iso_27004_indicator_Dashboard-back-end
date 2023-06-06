package com.pfem2.iso27004.Service;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.pfem2.iso27004.Entity.Evaluation;

import be.quodlibet.boxable.utils.PDStreamUtils;

@Service
public class generateReport {
    private final TemplateEngine templateEngine;
    private final EvaluationService evaluationService;

    @Autowired
    public generateReport(TemplateEngine templateEngine, EvaluationService evaluationService) {
        this.templateEngine = templateEngine;
        this.evaluationService = evaluationService;
    }

    public PDDocument GenerateReport() throws IOException {
        // Create a new PDF document
        PDDocument document = new PDDocument();

        // Add the front page
        /*
         * PDPage frontPage = new PDPage();
         * document.addPage(frontPage);
         * generatePageContent(frontPage, "frontpage.html");
         *
         * // Add the table of contents
         * PDPage contentsPage = new PDPage();
         * document.addPage(contentsPage);
         * generatePageContent(contentsPage, "tableofcontents.html");
         */

        // Add content pages (assuming you have a list of content items)
        List<Evaluation> evaluation = this.evaluationService.getDashboardIndicator();
        for (Evaluation contentItem : evaluation) {
            PDPage contentPage = new PDPage();
            document.addPage(contentPage);
            generatePageContent(document, contentPage, "indicatorTemplate", contentItem);
        }

        // Save the document to a PDF file
        // document.save("report.pdf");

        // Close the document
        // document.close();
        return document;
    }

    private void generatePageContent(PDDocument document, PDPage page, String templateName, Evaluation eval)
            throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Load the Thymeleaf template
        Context context = new Context();
        context.setVariable("indicator", eval.getIndicator());
        String renderedHtml = templateEngine.process(templateName, context);
        // String renderedHtml = " <h3 style='text-align: center; color: green'>
        // efef</h3>";

        // Render the HTML content to the PDF page

        PDStreamUtils.write(contentStream, renderedHtml, PDType1Font.TIMES_ROMAN, 12, page.getMediaBox().getWidth(),
                page.getMediaBox().getHeight(), java.awt.Color.DARK_GRAY);

        contentStream.close();
    }
}
