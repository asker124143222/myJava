package com.home.pdf;

import java.io.*;

/**
 * @author xu.dm
 * @since 2024/11/26 11:10
 **/
public class PdfBoxTest {
    public static void main(String[] args) throws IOException {
        String filename = "D:\\temp\\主表目录格式.pdf";
        String outputFilename = "D:\\temp\\out2.pdf";
        FileOutputStream out = new FileOutputStream(outputFilename);
        PdfUtil.addTextWatermark(new FileInputStream(filename), out, new PdfWatermarkProperties("测试pdf水印"));
    }
}
