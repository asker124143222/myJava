package com.home.alioss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.GetObjectRequest;

import java.io.File;

/**
 * @author xu.dm
 * @since 2023/8/7 11:21
 **/
public class DownloadTest implements ProgressListener {
    private long bytesRead = 0;
    private long totalBytes = -1;
    private boolean succeed = false;

    public static void main(String[] args) {
        String endpoint = "oss-cn-kunming-ynyc-d01-a.ops.ynyc.com/";
        String ak = "uKdpuonbgH7IbO1x";
        String sk = "AbWyZLA0bxdwEpv6MBgdcBx6sf4G59";
        String bucketName = "ynyc-zm-oss-dev";

        // 填写Object完整路径，完整路径中不能包含Bucket名称。例如exampledir/exampleobject.txt。
        String objectName = "wood-cloud/综合类A模板.ppt";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        String pathName = "D:\\temp\\oss-download\\综合类A模板.ppt";


        //String objectName = "wood-cloud/业务.mp4";
        //String pathName = "D:\\temp\\oss-download\\业务.mp4";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, ak, sk);

        try {
            // 下载文件的同时指定了进度条参数。
            ossClient.getObject(new GetObjectRequest(bucketName, objectName).
                            <GetObjectRequest>withProgressListener(new DownloadTest()),
                    new File(pathName));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public boolean isSucceed() {
        return succeed;
    }

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                System.out.println("Start to download......");
                break;
            case RESPONSE_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                System.out.println(this.totalBytes + " bytes in total will be downloaded to a local file");
                break;
            case RESPONSE_BYTE_TRANSFER_EVENT:
                this.bytesRead += bytes;
                if (this.totalBytes != -1) {
                    int percent = (int)(this.bytesRead * 100.0 / this.totalBytes);
                    System.out.println(bytes + " bytes have been read at this time, download progress: " +
                            percent + "%(" + this.bytesRead + "/" + this.totalBytes + ")");
                } else {
                    System.out.println(bytes + " bytes have been read at this time, download ratio: unknown" +
                            "(" + this.bytesRead + "/...)");
                }
                break;
            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                System.out.println("Succeed to download, " + this.bytesRead + " bytes have been transferred in total");
                break;
            case TRANSFER_FAILED_EVENT:
                System.out.println("Failed to download, " + this.bytesRead + " bytes have been transferred");
                break;
            default:
                break;
        }
    }
}
