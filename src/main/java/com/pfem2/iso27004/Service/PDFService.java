package com.pfem2.iso27004.Service;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.pfem2.iso27004.Entity.Evaluation;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PDFService {
    private final EvaluationService evaluationservice;
    private final TemplateEngine templateEngine;

    @Autowired
    public PDFService(EvaluationService evaluationservice, TemplateEngine templateEngine) {
        this.evaluationservice = evaluationservice;
        this.templateEngine = templateEngine;
    }

    public void generateRepport(HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");

            OutputStream outputStream = response.getOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);

            // pdfDocument.addNewPage();
            String templateName = "indicatorTemplate";
            Context ct = new Context();

            List<Evaluation> evaluations = this.evaluationservice.getDashboardIndicator();
            Map<Evaluation, List<Evaluation>> data = new HashMap<Evaluation, List<Evaluation>>();

            for (Evaluation evaluation : evaluations) {
                pdfDocument.addNewPage();

                data.put(evaluation,
                        this.evaluationservice.getAllInicatorEvaluations(evaluation.getIndicator().getId()));
            }
            ct.setVariable("evaluations", data);

            String renderedHtml = templateEngine.process(templateName, ct);

            ConverterProperties converterProperties = new ConverterProperties();
            HtmlConverter.convertToPdf(renderedHtml, pdfDocument, converterProperties);

            pdfDocument.close();

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
