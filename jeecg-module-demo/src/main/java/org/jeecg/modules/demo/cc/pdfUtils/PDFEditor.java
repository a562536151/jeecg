package org.jeecg.modules.demo.cc.pdfUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This example code demonstrates how to edit a PDF document using Apache PDFBox and iText libraries.
 * Specifically, it shows how to delete a line of text in an existing PDF document.
 */
public class PDFEditor {
    public static void main(String[] args) throws IOException, DocumentException {

        String inputFilePath = "D:\\pdf\\xxxxxxxxxxxx11.pdf";
        String outputFilePath = "D:\\pdf\\xxxssss.pdf";
        String deleteString = "这是第二行";
        // Load the input PDF file

        File file = new File(inputFilePath);
        PDDocument pdfDocument = PDDocument.load(file);

// Instantiate a PDF parser and strip the text from the input PDF
        RandomAccessBufferedFileInputStream inputStream = new RandomAccessBufferedFileInputStream(file);
        PDFParser pdfParser = new PDFParser(inputStream);
        pdfParser.parse();
        String text = new PDFTextStripper().getText(pdfDocument);

// Search for the text to be deleted
        int deletePos = text.indexOf(deleteString);
        if (deletePos < 0) {
            System.out.println("Line to be deleted not found!");
            return;
        }

// Iterate over every page and delete the text
        for (int i = 0; i < pdfDocument.getNumberOfPages(); i++) {
            // Get the page, dimensions and content stream
            PDPage page = pdfDocument.getPage(i);
            PDRectangle mediabox = page.getMediaBox();
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page, true, false);

            // Set the font and font size
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Split the text into two parts and create a new paragraph
            String[] lines = text.substring(0, deletePos).split("\n");
            String line1 = lines[lines.length - 1];
            String line2 = text.substring(deletePos + deleteString.length());
            String paraText = line1 + " " + line2 + "\n";
            Paragraph para = new Paragraph(paraText);

            // Iterate over every element in the document and delete the text
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
            document.open();

            PdfContentByte cb = writer.getDirectContent();
            PdfReader reader = new PdfReader(inputFilePath);

            // Iterate over every page and copy it to the output PDF one page at a time
            for (int j = 1; j <= reader.getNumberOfPages(); j++) {
                document.newPage();
                PdfImportedPage page1 = writer.getImportedPage(reader, j);
                cb.addTemplate(page1, 0, 0);

                // Add the text to the page
                if (j == i + 1) {
                    para.setAlignment(Element.ALIGN_LEFT);
                    document.add(para);
                }
            }

            // Close the PDF document and content stream
            document.close();
            contentStream.close();
        }

// Save the output PDF file
        pdfDocument.save(outputFilePath);
        pdfDocument.close();

        System.out.println("PDF file edited successfully.");

    }
}