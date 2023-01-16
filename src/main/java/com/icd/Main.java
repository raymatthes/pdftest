package com.icd;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
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

            BaseFont bf_helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, false);

            // headers and footers must be added before the document is opened

            Path inputPathLogo = Paths.get("input", "icd-logo.png");
            Image logo = Image.getInstance(inputPathLogo.toString());
            logo.scalePercent(3);
            Chunk logoChunk = new Chunk(logo, 0f, 0f);
            Phrase logoPhrase = new Phrase(0, logoChunk);

            Cell left = new Cell(logoPhrase);
            left.setHorizontalAlignment(HorizontalAlignment.LEFT);
            left.setVerticalAlignment(VerticalAlignment.CENTER);
            left.setBorderWidth(2);
            left.setBorder(Cell.RIGHT);
            left.setBorderColor(Color.decode("0x009845"));

            Phrase portalPhrase = new Phrase(0, "Portal", new Font(bf_helv, 14));
            portalPhrase.setLeading(10);
            Cell middle = new Cell(portalPhrase);
            middle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            middle.setVerticalAlignment(VerticalAlignment.CENTER);
            middle.setBorderWidth(0);

            Cell right = new Cell("\n");
            right.setHorizontalAlignment(HorizontalAlignment.LEFT);
            right.setVerticalAlignment(VerticalAlignment.CENTER);
            right.setBorderWidth(0);


            Table smallTable = new Table(3, 1);
            smallTable.setPadding(0);
            smallTable.setBorder(0);
            smallTable.setSpacing(0);
            smallTable.setOffset(0);
            smallTable.setWidth(100);
            smallTable.setWidths(new float[]{16f, 16.5f, 67.5f});
            smallTable.addCell(left, new Point(0, 0));
            smallTable.addCell(middle, new Point(0, 1));
            smallTable.addCell(right, new Point(0, 2));


            Phrase viewName = new Phrase(0, "My Dashboard dada", new Font(bf_helv, 10));
            Phrase timestamp = new Phrase(0, "Created on 15-Jan-2023 03:37 PM NY / 08:37 PM UK", new Font(bf_helv, 8));

            Cell middleCell = new Cell(viewName);
            middleCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
            middleCell.setVerticalAlignment(VerticalAlignment.CENTER);
            middleCell.setBorderWidth(0);

            Cell rightCell = new Cell(timestamp);
            rightCell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            rightCell.setVerticalAlignment(VerticalAlignment.CENTER);
            rightCell.setBorderWidth(0);

            Table table = new Table(3, 2);
            table.setTableFitsPage(true);
            table.setBorder(0);
            table.setPadding(0);
            table.setSpacing(0);
            table.setOffset(0);
            table.setWidth(100);
            table.setWidths(new int[]{33, 34, 33});

            table.insertTable(smallTable, new Point(0, 0));
            table.addCell(middleCell, new Point(0, 1));
            table.addCell(rightCell, new Point(0, 2));

            Cell verticalSpace = new Cell("\n");
            verticalSpace.setColspan(3);
            verticalSpace.setBorder(Cell.NO_BORDER);
            table.addCell(verticalSpace, new Point(1, 0));

            Phrase headerPhrase = new Phrase(0);
            headerPhrase.add(table);

            HeaderFooter header = new HeaderFooter(headerPhrase, false);
            header.setBorder(Rectangle.NO_BORDER);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorderWidthBottom(0);
            document.setHeader(header);

            document.open();

            instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));

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