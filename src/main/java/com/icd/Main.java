package com.icd;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Ray Matthes
 */
public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello World");

        // set landscape
        Document document = new Document(PageSize.LETTER.rotate());
        try {

            document.addTitle("ICD Dashboard This is a great title");
            document.addSubject("ICD Dashboard  This is a great subject");
            document.addKeywords("ICD Dashboard");
            document.addCreator("ICD Dashboard, icdportal.com");
            document.addAuthor("ICD Dashboard ");

            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            Path outputPath = Paths.get("output", "20230102-22164649-dasher06-mydashboard-false.pdf");
            final PdfWriter instance = PdfWriter.getInstance(document, Files.newOutputStream(outputPath));

            // various fonts
            BaseFont bf_helv = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
            BaseFont bf_times = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false);
            BaseFont bf_courier = BaseFont.createFont(BaseFont.COURIER, "Cp1252", false);
            BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);

            // headers and footers must be added before the document is opened

            Path inputPathLogo = Paths.get("input", "icd-logo.png");
            Image logo = Image.getInstance(inputPathLogo.toString());
            logo.scalePercent(5);
            Chunk logoChunk = new Chunk(logo, 0f, 0f);
            Phrase logoPhrase = new Phrase(logoChunk);
            Phrase words = new Phrase("2023-01-02 22:16:46.49 dasher06-mydashboard-false", new Font(bf_helv));

            Phrase headerPhrase = new Phrase();
            headerPhrase.add(logoPhrase);
            headerPhrase.add(words);

            HeaderFooter header = new HeaderFooter(headerPhrase, false);
            header.setAlignment(Element.ALIGN_CENTER);
            document.setHeader(header);

            HeaderFooter footer = new HeaderFooter(
                    new Phrase("page: ", new Font(bf_helv)), true);
            footer.setBorder(Rectangle.NO_BORDER);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.setFooter(footer);


            document.open();

            instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));

//            document.add(new Paragraph("Hello World"));
//            document.add(new Paragraph("screenshot-20230102-22164649-dasher06-mydashboard-false.png"));

            Path inputPathPng = Paths.get("input", "screenshot-20230102-22164649-dasher06-mydashboard-false.png");
            Image png = Image.getInstance(inputPathPng.toString());
            png.scalePercent(20);
            document.add(png);

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }

        document.close();

    }

}