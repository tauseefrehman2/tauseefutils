package com.tauseef.tauseeflib;

import android.content.Context;
import android.net.Uri;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.InputStream;

public class TauseefExtraction {

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

    static StringBuilder stringBuilder;

    //Extract text from word file
    public static String extractTextFromWordFile(Context context, Uri uri) {

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            if (inputStream == null) return "";

            stringBuilder = new StringBuilder();
            XWPFDocument doc = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            stringBuilder.append(extractor.getText());

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
