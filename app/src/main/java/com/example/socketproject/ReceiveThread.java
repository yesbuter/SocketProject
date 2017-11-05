package com.example.socketproject;

/**
 * Created by hecheng on 2017/11/3.
 */
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ReceiveThread extends Thread{

    private SocketInfo socket;
    private Context context;
    private Handler handler;

    public ReceiveThread(SocketInfo socket, Context context, Handler handler) {
        this.socket = socket;
        this.context = context;
        this.handler = handler;
    }


    @Override
    public void run() {
        try {
            System.out.println("begin:" + String.valueOf(socket.getClient().isClosed()));
            parseProtocol(socket, context, handler);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("1111"+String.valueOf(socket.getClient().isClosed()));
        }
    }

    /**
     * 消息解析
     *
     * @param
     * @throws IOException
     */
    private static void parseProtocol(SocketInfo socket, Context context, Handler handler) throws IOException {
        DataInputStream is = socket.getIs(); //读取Java标准数据类型的输入流


        //协议解析
        //0:say hi，2:请求文件
        byte type = is.readByte();               //读取消息类型
        int totalLen = is.readInt();             //读取消息长度
        byte[] data = new byte[totalLen - 4 - 1]; //定义存放消息内容的字节数组
        is.readFully(data);                      //读取消息内容
        String msg = new String(data);
        //String msg = new String(data, "utf-8");            //消息内容

        System.out.println("接收消息类型" + type);
        System.out.println("接收消息长度" + totalLen);
        System.out.println("发来的内容是:" + msg);
        if (type == 0) {
            System.out.println(msg);
        }
        if (type == 2) {
            Message message = new Message();
            message.what = 3;
            handler.sendMessage(message);
            ((MainActivity) context).setStudentMsg(msg);
        }
    }
}
