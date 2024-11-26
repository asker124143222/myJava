package com.home.pdf;

import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.util.autodetect.FontFileFinder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;

import java.io.*;
import java.net.URI;

/**
 * @author xu.dm
 * @since 2024/11/26 11:05
 **/
public class PdfUtil {
    private static final String DEFAULT_TTF_FILENAME = "simsun.ttf";
    private static final String DEFAULT_TTC_FILENAME = "simsun.ttc";
    private static final String DEFAULT_FONT_NAME = "SimSun";
    private static final TrueTypeFont DEFAULT_FONT;

    static {
        DEFAULT_FONT = loadSystemFont();
    }


    /**
     * 加载系统字体,提供默认字体
     *
     * @return
     */
    private synchronized static TrueTypeFont loadSystemFont() {
        //load 操作系统的默认字体. 宋体
        FontFileFinder fontFileFinder = new FontFileFinder();
        for (URI uri : fontFileFinder.find()) {
            try {
                final String filePath = uri.getPath();
                if (filePath.endsWith(DEFAULT_TTF_FILENAME)) {
                    return new TTFParser(false).parse(filePath);
                } else if (filePath.endsWith(DEFAULT_TTC_FILENAME)) {
                    TrueTypeCollection trueTypeCollection = new TrueTypeCollection(new FileInputStream(filePath));
                    final TrueTypeFont font = trueTypeCollection.getFontByName(DEFAULT_FONT_NAME);
                    //复制完之后关闭ttc
                    trueTypeCollection.close();
                    return font;
                }
            } catch (Exception e) {
                throw new RuntimeException("加载操作系统字体失败", e);
            }
        }

        return null;
    }


    /**
     * 添加文本水印
     * * 使用内嵌字体模式，pdf文件大小会增加1MB左右
     *
     * @param sourceFile 需要加水印的文件
     * @param descFile   目标存储路径
     * @param props      水印配置
     * @throws IOException
     */
    public static void addTextWatermark(File sourceFile, String descFile, PdfWatermarkProperties props) throws IOException {
        // 加载PDF文件
        PDDocument document = PDDocument.load(sourceFile);
        addTextToDocument(document, props);
        document.save(descFile);
        document.close();
    }

    /**
     * 添加文本水印
     *
     * @param inputStream  需要加水印的文件流
     * @param outputStream 加水印之后的流。执行完之后会关闭outputStream, 建议使用{@link BufferedOutputStream}
     * @param props        水印配置
     * @throws IOException
     */
    public static void addTextWatermark(InputStream inputStream, OutputStream outputStream, PdfWatermarkProperties props) throws IOException {
        // 加载PDF文件
        PDDocument document = PDDocument.load(inputStream);
        addTextToDocument(document, props);
        document.save(outputStream);
    }


    /**
     * 处理PDDocument,添加文本水印
     *
     * @param document
     * @param props
     * @throws IOException
     */
    public static void addTextToDocument(PDDocument document, PdfWatermarkProperties props) throws IOException {
        document.setAllSecurityToBeRemoved(true);

        // 遍历PDF文件，在每一页加上水印
        for (PDPage page : document.getPages()) {
            PDPageContentStream stream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            // 加载水印字体
            if (DEFAULT_FONT == null) {
                throw new RuntimeException(String.format("未提供默认字体.请安装字体文件%s或%s", DEFAULT_TTF_FILENAME, DEFAULT_TTC_FILENAME));
            }

            PDFont font;
            if (props.getFontFile() != null) {
                font = PDType0Font.load(document, props.getFontFile());
            } else {
                //当TrueTypeFont为字体集合时, embedSubSet 需要设置为true, 嵌入其子集
                font = PDType0Font.load(document, DEFAULT_FONT, true);
            }

            PDExtendedGraphicsState r = new PDExtendedGraphicsState();

            // 设置透明度
            r.setNonStrokingAlphaConstant(props.getTransparency());
            r.setAlphaSourceFlag(true);
            stream.setGraphicsStateParameters(r);

            // 设置水印字体颜色
            final int[] color = props.getColor();
            stream.setNonStrokingColor(color[0], color[1], color[2], color[3]);
            stream.beginText();
            stream.setFont(font, props.getFontSize());

            // 获取PDF页面大小
            float pageHeight = page.getMediaBox().getHeight();
            float pageWidth = page.getMediaBox().getWidth();

            // 根据纸张大小添加水印，30度倾斜
            for (int h = props.getY(); h < pageHeight; h = h + props.getHeight()) {
                for (int w = props.getX(); w < pageWidth; w = w + props.getWidth()) {
                    stream.setTextMatrix(Matrix.getRotateInstance(props.getRotate(), w, h));
                    stream.showText(props.getContent());
                }
            }

            // 结束渲染，关闭流
            stream.endText();
            stream.restoreGraphicsState();
            stream.close();
        }
    }


    /**
     * 设置pdf文件输出的响应头
     *
     * @param response web response
     * @param fileName 文件名(不含扩展名)
     */
//    public static void setPdfResponseHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
//        response.setContentType("application/pdf");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".pdf");
//    }


//    @GetMapping("/t")
//    public void getFile(HttpServletResponse response) throws IOException {
//        PdfUtil.setPdfResponseHeader(response, "watermark");
//        final ServletOutputStream out = response.getOutputStream();
//        PdfUtil.addTextWatermark(new FileInputStream("D:/测试文件.pdf"), out, new PdfWatermarkProperties("测试pdf水印"));
//    }
}
