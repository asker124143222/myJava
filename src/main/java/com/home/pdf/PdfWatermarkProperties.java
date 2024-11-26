package com.home.pdf;


import java.io.File;

/**
 * @author xu.dm
 * @since 2024/11/26 11:06
 **/
public class PdfWatermarkProperties {
    public PdfWatermarkProperties(String content) {
        this.content = content;
    }

    /**
     * 文字水印内容
     */
    private String content = "";

    /**
     * ttf类型字体文件. 为null则使用默认字体
     */
    private File fontFile;

    private float fontSize = 13;

    /**
     * cmyk颜色.参数值范围为 0-255
     */
    private int[] color = {0, 0, 0, 210};

    /**
     * 透明度
     */
    private float transparency = 0.3f;

    /**
     * 倾斜度. 默认30°
     */
    private double rotate = 0.3;

    /**
     * 初始添加水印的点位
     */
    private int x = -10;
    private int y = 10;

    /**
     * 内容区域的宽高.即单个水印范围的大小
     */
    private int width = 200;
    private int height = 200;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getFontFile() {
        return fontFile;
    }

    public void setFontFile(File fontFile) {
        this.fontFile = fontFile;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public float getTransparency() {
        return transparency;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    public double getRotate() {
        return rotate;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
