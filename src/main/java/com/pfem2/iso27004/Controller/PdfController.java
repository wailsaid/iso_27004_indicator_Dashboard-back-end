package com.pfem2.iso27004.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import com.pfem2.iso27004.Service.PDFService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/generate-pdf")
public class PdfController {

    // private final TemplateEngine templateEngine;
    private final PDFService pdfService;

    @Autowired
    public PdfController(TemplateEngine templateEngine, PDFService pdfService) {
        // this.templateEngine = templateEngine;
        this.pdfService = pdfService;
    }

    @GetMapping
    public void generatePdf(HttpServletResponse response) {
        this.pdfService.generateRepport(response);
        /*
         * try {
         * response.setContentType("application/pdf");
         *
         * OutputStream outputStream = response.getOutputStream();
         * PdfWriter writer = new PdfWriter(outputStream);
         * PdfDocument pdfDocument = new PdfDocument(writer);
         *
         * // pdfDocument.addNewPage();
         * String templateName = "indicatorTemplate";
         * Context ct = new Context();
         *
         * List<Evaluation> evaluations =
         * this.evaluationservice.getDashboardIndicator();
         * Map<Evaluation, List<Evaluation>> data = new HashMap<Evaluation,
         * List<Evaluation>>();
         *
         * for (Evaluation evaluation : evaluations) {
         * pdfDocument.addNewPage();
         *
         * data.put(evaluation,
         * this.evaluationservice.getAllInicatorEvaluations(evaluation.getIndicator().
         * getId()));
         * }
         * ct.setVariable("evaluations", data);
         *
         * String renderedHtml = templateEngine.process(templateName, ct);
         *
         * ConverterProperties converterProperties = new ConverterProperties();
         * HtmlConverter.convertToPdf(renderedHtml, pdfDocument, converterProperties);
         *
         * pdfDocument.close();
         *
         * outputStream.flush();
         * outputStream.close();
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         */
    }

    /*
     * private JFreeChart createLineChart() {
     * DefaultCategoryDataset dataset = new DefaultCategoryDataset();
     * dataset.addValue(120, "Category 1", "Label 1");
     * dataset.addValue(200, "Category 2", "Label 2");
     * dataset.addValue(150, "Category 3", "Label 3");
     * JFreeChart chart = ChartFactory.createLineChart(
     * "Sample Line Chart", // chart title
     * "Category", // domain axis label
     * "Value", // range axis label
     * dataset, // data
     * PlotOrientation.VERTICAL, // orientation
     * false, // include legend
     * false, // tooltips
     * false // urls
     * );
     *
     * // Customize the chart appearance
     *
     * // chart.setBackgroundPaint(java.awt.Color.WHITE);
     * chart.getLegend().setVisible(false);
     * chart.getTitle().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
     * chart.getCategoryPlot().setOutlineVisible(false);
     * chart.getCategoryPlot().getDomainAxis().setTickLabelFont(new
     * java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
     * chart.getCategoryPlot().getRangeAxis().setTickLabelFont(new
     * java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
     * chart.getCategoryPlot().getRangeAxis().setLabelFont(new
     * java.awt.Font("Arial", java.awt.Font.BOLD, 14));
     * // chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(new
     * // java.awt.Color(170, 170, 170));
     * // chart.getCategoryPlot().getRangeAxis().setTickMarkPaint(new
     * // java.awt.Color(170, 170, 170));
     * // chart.getCategoryPlot().getRangeAxis().setAxisLinePaint(new
     * // java.awt.Color(170, 170, 170));
     * // chart.getCategoryPlot().getRenderer().setSeriesStroke(0, new
     * // BasicStroke(2.0f));
     * // chart.getCategoryPlot().getRenderer().setSeriesPaint(0, new
     * // java.awt.Color(91, 155, 213));
     *
     * return chart;
     * }
     *
     * private String createChartImageBase64() throws IOException {
     * // Create a chart using a charting library
     * // Here, we are creating a simple bar chart using JFreeChart
     * JFreeChart jFreeChart = createLineChart();
     *
     * // Convert the chart to an image
     * ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
     * ChartUtilities.writeChartAsPNG(outputStream, jFreeChart, 500, 300);
     *
     * // Convert the image byte array to Base64 string
     * byte[] chartImageBytes = outputStream.toByteArray();
     * String chartImageBase64 =
     * Base64.getEncoder().encodeToString(chartImageBytes);
     *
     * return chartImageBase64;
     * }
     */
}
