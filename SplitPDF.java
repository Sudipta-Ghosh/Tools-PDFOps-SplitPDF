import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
public class SplitPDF{
public static void main(String[] args) {
    try {
        SplitPDF.splitPDF(new FileInputStream("D:\\ECLIPSEWORKSPACE\\Tools\\SplitMergePDF\\Tools-PDFOps-SplitPDF\\JAX-RS.pdf"),
	                       new FileOutputStream("D:\\ECLIPSEWORKSPACE\\Tools\\SplitMergePDF\\Tools-PDFOps-SplitPDF\\JAX-RS1.pdf"), 1, 10);
	    SplitPDF.splitPDF(new FileInputStream("D:\\ECLIPSEWORKSPACE\\Tools\\SplitMergePDF\\Tools-PDFOps-SplitPDF\\JAX-RS.pdf"),
                    new FileOutputStream("D:\\ECLIPSEWORKSPACE\\Tools\\SplitMergePDF\\Tools-PDFOps-SplitPDF\\JAX-RS2.pdf"), 11, 23);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public static void splitPDF(InputStream inputStream,
        OutputStream outputStream, int fromPage, int toPage) {
    Document document = new Document();
    try {
        PdfReader inputPDF = new PdfReader(inputStream);

        int totalPages = inputPDF.getNumberOfPages();

        //make fromPage equals to toPage if it is greater
        if(fromPage > toPage ) {
            fromPage = toPage;
        }
        if(toPage > totalPages) {
            toPage = totalPages;
        }


        // Create a writer for the outputstream
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        document.open();
        PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data
        PdfImportedPage page;

        while(fromPage <= toPage) {
            document.newPage();
            page = writer.getImportedPage(inputPDF, fromPage);
            cb.addTemplate(page, 0, 0);
            fromPage++;
        }
        outputStream.flush();
        document.close();
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (document.isOpen())
            document.close();
        try {
            if (outputStream != null)
                outputStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
}