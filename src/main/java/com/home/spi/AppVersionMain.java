package com.home.spi;

import com.wood.plugin.annotation.AppVersion;

/**
 * @author xu.dm
 * @since 2024/2/4 10:21
 **/
public class AppVersionMain {
    @AppVersion
    private final static String VERSION = "";

    public static void main(String[] args) {
        System.out.println("VERSION:"+VERSION);
        VersionClass versionClass = new VersionClass();
        System.out.println("测试版本号：" +  versionClass.getVersion());
    }

    public static class VersionClass{
        @AppVersion
        private String version;


        public String getVersion() {
            return version;
        }
    }
}
