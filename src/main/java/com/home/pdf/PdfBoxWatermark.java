package com.home.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

/**
 * @author xu.dm
 * @since 2024/11/26 10:12
 * 简单测试
 **/
public class PdfBoxWatermark {
    public static void main(String[] args) throws IOException {
        // 读取原始 PDF 文件
        String filename = "D:\\temp\\主表目录格式.pdf";
        PDDocument document = PDDocument.load(new File(filename));

        // 遍历 PDF 中的所有页面
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            PDPage page = document.getPage(i);
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            // 设置字体和字号
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 36);

            // 设置透明度
            contentStream.setNonStrokingColor(200, 200, 200);

            // 添加文本水印
            contentStream.beginText();
            // 设置水印位置
            contentStream.newLineAtOffset(100, 100);
            // 设置水印内容
            contentStream.showText("watermark");
            contentStream.endText();

            contentStream.close();
        }

        // 保存修改后的 PDF 文件
        document.save(new File("D:\\temp\\output.pdf"));
        document.close();
    }
}
