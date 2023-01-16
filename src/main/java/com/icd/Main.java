package com.icd;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
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

    public static BaseFont FONT_HELVETICA;

    static {
        try {
            FONT_HELVETICA = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {

//        Rectangle page = PageSize.A4;
//        float pngScalePercent = 21.5f;

        Rectangle page = PageSize.LETTER;
        float pngScalePercent = 20.1f;

        System.out.println("Start");

        // set landscape
        Document document = new Document(page.rotate());
        try {

            // we create a writer that listens to the document
            // and directs a PDF-stream to a file
            Path outputPath = Paths.get("output", "20230102-22164649-dasher06-mydashboard-false.pdf");
            final PdfWriter instance = PdfWriter.getInstance(document, Files.newOutputStream(outputPath));

            // headers and footers must be added before the document is opened

            Table logoTable = getLogoTable();
            Table headerTable = getHeaderTable(logoTable);
            createHeader(document, headerTable);

            document.open();
            createInfo(document, instance);
            createContent(pngScalePercent, document);

        } catch (DocumentException | IOException exception) {
            System.err.println(exception.getMessage());
        }

        document.close();

    }

    private static void createContent(float pngScalePercent, Document document) throws IOException {
        Path inputPathPng = Paths.get("input", "screenshot-20230102-22164649-dasher06-mydashboard-false.png");
        Image png = Image.getInstance(inputPathPng.toString());
        png.scalePercent(pngScalePercent);
        document.add(png);
    }

    private static void createInfo(Document document, PdfWriter instance) {
        document.addTitle("ICD Dashboard This is a great title");
        document.addSubject("ICD Dashboard  This is a great subject");
        document.addKeywords("ICD Dashboard");
        document.addCreator("ICD Dashboard, icdportal.com");
        document.addAuthor("ICD Dashboard ");
        instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
    }

    private static void createHeader(Document document, Table table) {
        Phrase headerPhrase = new Phrase(0);
        headerPhrase.add(table);

        HeaderFooter header = new HeaderFooter(headerPhrase, false);
        header.setBorder(Rectangle.NO_BORDER);
        header.setAlignment(Element.ALIGN_CENTER);
        header.setBorderWidthBottom(0);
        document.setHeader(header);
    }

    private static Table getHeaderTable(Table logoTable) {
        Phrase viewName = new Phrase(0, "My Dashboard dada", new Font(FONT_HELVETICA, 10));
        Phrase timestamp = new Phrase(0, "Created on 15-Jan-2023 03:37 PM NY / 08:37 PM UK", new Font(FONT_HELVETICA, 8));

        Cell middleCell = new Cell(viewName);
        middleCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        middleCell.setVerticalAlignment(VerticalAlignment.CENTER);
        middleCell.setBorderWidth(0);

        Cell rightCell = new Cell(timestamp);
        rightCell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        rightCell.setVerticalAlignment(VerticalAlignment.CENTER);
        rightCell.setBorderWidth(0);

        Table headerTable = new Table(3, 2);
        headerTable.setTableFitsPage(true);
        headerTable.setBorder(0);
        headerTable.setPadding(0);
        headerTable.setSpacing(0);
        headerTable.setOffset(0);
        headerTable.setWidth(100);
        headerTable.setWidths(new int[]{33, 34, 33});

        headerTable.insertTable(logoTable, new Point(0, 0));
        headerTable.addCell(middleCell, new Point(0, 1));
        headerTable.addCell(rightCell, new Point(0, 2));

        Cell verticalSpace = new Cell("\n");
        verticalSpace.setColspan(3);
        verticalSpace.setBorder(Cell.NO_BORDER);
        headerTable.addCell(verticalSpace, new Point(1, 0));

        return headerTable;
    }

    private static Table getLogoTable() throws IOException {
        Path inputPathLogo = Paths.get("input", "icd-logo.png");
        Image logoImage = Image.getInstance(inputPathLogo.toString());
        logoImage.scalePercent(3);
        Chunk logoChunk = new Chunk(logoImage, 0f, 0f);
        Phrase logoPhrase = new Phrase(0, logoChunk);

        Cell left = new Cell(logoPhrase);
        left.setHorizontalAlignment(HorizontalAlignment.LEFT);
        left.setVerticalAlignment(VerticalAlignment.CENTER);
        left.setBorderWidth(2);
        left.setBorder(Cell.RIGHT);
        left.setBorderColor(Color.decode("0x009845"));

        Phrase portalPhrase = new Phrase(0, "Portal", new Font(FONT_HELVETICA, 14));
        portalPhrase.setLeading(10);
        Cell middle = new Cell(portalPhrase);
        middle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        middle.setVerticalAlignment(VerticalAlignment.CENTER);
        middle.setBorderWidth(0);

        Cell right = new Cell("\n");
        right.setHorizontalAlignment(HorizontalAlignment.LEFT);
        right.setVerticalAlignment(VerticalAlignment.CENTER);
        right.setBorderWidth(0);

        Table logoTable = new Table(3, 1);
        logoTable.setPadding(0);
        logoTable.setBorder(0);
        logoTable.setSpacing(0);
        logoTable.setOffset(0);
        logoTable.setWidth(100);
        logoTable.setWidths(new float[]{16f, 16.5f, 67.5f});
        logoTable.addCell(left, new Point(0, 0));
        logoTable.addCell(middle, new Point(0, 1));
        logoTable.addCell(right, new Point(0, 2));

        return logoTable;
    }

}