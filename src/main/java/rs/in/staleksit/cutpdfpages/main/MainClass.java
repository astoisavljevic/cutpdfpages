/**
 * 
 */
package rs.in.staleksit.cutpdfpages.main;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

/**
 * @author aleksandar
 *
 */
public class MainClass {

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			MainClass mainClass = new MainClass();
			// mainClass.removePdfPage(args[0], args[1], true, Integer.valueOf(args[2]));
			mainClass.removePdfPage("SenchaTouch.pdf", "SenchaTouchNew.pdf", true, 1);
		} else {
			System.out.println("Usage cutpdfpages <infilename> <outfilename> <pagetocut>");
		}
	}
	
	
	public void removePdfPage(String pdfSourceFile, String pdfDestinationFile, boolean debug, int pageToDelete) {
		
		try {
			PdfReader r = new PdfReader(pdfSourceFile);
			// consider what to use instead
			@SuppressWarnings("deprecation")
			RandomAccessFileOrArray raf = new RandomAccessFileOrArray(pdfSourceFile);
			Document document = new Document(r.getPageSizeWithRotation(1));
			
			PdfCopy writer = new PdfCopy(document, new FileOutputStream(pdfDestinationFile));
			document.open();
			
			PdfImportedPage page = null;
			
			for (int i=1; i<=r.getNumberOfPages(); i++) {
				//get the page content
                byte bContent [] = r.getPageContent(i,raf);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                //write the content to an output stream
                bs.write(bContent);
                
                System.out.println("page content length " + bs.size());
                
                // skip adding designated page
                if (i != pageToDelete) {
                	page = writer.getImportedPage(r, i);
                	writer.addPage(page);
                }
                
                bs.close();
			}
			//close everything
            document.close();
            writer.close();
            raf.close();
            r.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
