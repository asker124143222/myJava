package com.home.tika;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author xu.dm
 * @since 2024/1/26 14:41
 **/
public class TikaMetadataExample {
    public static void main(String[] args) {
        try {
//            File file = new File("D:\\temp\\需求规格说明书-20240105013828235.docx");
//            File file = new File("D:\\temp\\接口测试.txt");
            File file = new File("D:\\temp\\geek.exe");
            Tika tika = new Tika();
            //detecting the file type using detect method
            String filetype = tika.detect(file);
            System.out.println(filetype);

            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream inputstream = new FileInputStream(file);
            ParseContext context = new ParseContext();
            parser.parse(inputstream, handler, metadata, context);
            System.out.println(handler.toString());
            //getting the list of all meta data elements
            String[] metadataNames = metadata.names();
            for(String name : metadataNames) {
                System.out.println(name + ": " + metadata.get(name));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
