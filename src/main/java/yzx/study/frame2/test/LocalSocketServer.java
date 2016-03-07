package yzx.study.frame2.test;

import android.net.LocalServerSocket;
import android.net.LocalSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import yzx.study.frame2.callback.GenericCallback;

public class LocalSocketServer {

    private static boolean running = true;
    private static LocalServerSocket server;


    /************************************************************************************************
     * 开启Socket服务
     *
     * @param onMessageReceive 来子client的消息回调
     * @param onServerError    服务器错误
     ************************************************************************************************/
    public static void start(final GenericCallback<String> onMessageReceive, final Runnable onServerError) {
        new Thread() {
            public void run() {
                running = true;
                try {
                    server = new LocalServerSocket("yz.a.bdc");
                    while (running) {
                        LocalSocket ls = server.accept();
                        dealInOut(ls, onMessageReceive);
                    }
                    LocalSocketServer.stop();
                } catch (Exception e) {
                    LocalSocketServer.stop();
                    if (onServerError != null)
                        onServerError.run();
                }
            }
        }.start();
    }


    /*************************************************************************************************
     * 停止Server
     ************************************************************************************************/
    public static void stop() {
        running = false;
        try {
            if (server != null)
                server.close();
        } catch (IOException e) {
        }
        server = null;
    }


    /**
     * 处理通信
     *
     * @param socket           Client的socket
     * @param onMessageReceive 消息回调对象
     */
    private static void dealInOut(final LocalSocket socket, final GenericCallback<String> onMessageReceive) {
        new Thread() {
            public void run() {
                try {
 /* 读取内容 */
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        sb.append("\n");
                    }
                    reader.close();
                    socket.shutdownInput();
                    if (sb.length() > 0)
                        sb.deleteCharAt(sb.length() - 1);
/* 写入内容 */
                    if (onMessageReceive != null) {
                        Object result = onMessageReceive.callback(sb.toString());
                        if (result != null) {
                            PrintWriter writer = new PrintWriter(socket.getOutputStream());
                            writer.write(result.toString());
                            writer.flush();
                            writer.close();
                        }
                    }
                    socket.shutdownOutput();
                    socket.close();
                } catch (Exception e) {
                }
            }
        }.start();
    }


}
