package yzx.study.frame2.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 流读取工具
 */
public class StreamTool {


    // 整体读取的缓冲大小
    public static int FULL_BUFFER_SIZE = 1024 * 2;
    // 分部读取的缓冲大小
    public static int SECTION_BUFFER_SIZE = 1024 * 4;


    /******************************************************************************************
     * 将流全部转换成字节数组
     *
     * @param in InputStream
     * @return 全部的字节数组
     * @throws IOException error
     *****************************************************************************************/
    public static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[FULL_BUFFER_SIZE];
        int len;
        while ((len = in.read(buffer)) != -1)
            out.write(buffer, 0, len);
        out.flush();
        return out.toByteArray();
    }


    /******************************************************************************************
     * 逐步分部分读取输入流
     *
     * @param in       InputStream
     * @param callback 每次读取的字节回调
     * @throws IOException error
     *****************************************************************************************/
    public static void read(InputStream in, OnSectionReadedCallBack callback) throws Exception {
        byte[] buffer = new byte[SECTION_BUFFER_SIZE];
        int len;
        while ((len = in.read(buffer)) != -1)
            callback.onReaded(buffer, len);
    }


    /******************************************************************************************
     * 回调
     *****************************************************************************************/
    public static interface OnSectionReadedCallBack {
        void onReaded(byte[] data, int len) throws Exception;
    }

}
