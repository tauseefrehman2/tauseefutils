package com.tauseef.tauseeflib;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.InputStream;

public class TauseefUtils {

    private static final String TAG = "TauseefExtraction";

    //scope
    static {
        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl");

        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl");

        System.setProperty(
                "org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl");
    }

    /**
     * @param context where you are calling this method
     * @param uri     uri of file you want to extract
     * @return String will return
     */


    //Extract text from word file
    public static String extractWordFile(Context context, Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            if (inputStream == null) return stringBuilder.toString();

            XWPFDocument doc = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            stringBuilder.append(extractor.getText());

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }


    /**
     * @param context where you are calling this method
     * @param uri     uri of file you want to extract
     * @return String will return
     */

    public static String extractPDFFile(Context context, Uri uri) {
        Activity activity = (Activity) context;
        StringBuilder builder = new StringBuilder();
        ;
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            if (inputStream == null) return "";
            String fileContent = "";
            PdfReader reader = new PdfReader(inputStream);

            int n = reader.getNumberOfPages();
            for (int i = 1; i <= n; i++) {
                fileContent = PdfTextExtractor.getTextFromPage(reader, i);
                builder.append(fileContent);
            }
            reader.close();

        } catch (Exception e) {
            Log.d(TAG, "extractPdfFile: " + e.getMessage());
        }
        return builder.toString();
    }
}
