package com.example.socketproject;
import android.content.Context;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.net.Socket;
/**
 * Created by hecheng on 2017/10/25.
 */
public class SendThread extends Thread{

    private SocketInfo socket;
    private String msg;
    private int type;
    private Handler handler;
    private Context context;
    public SendThread(SocketInfo socket, String msg, int type, Handler handler, Context context) {
        this.type = type;
        this.socket = socket;
        this.msg = msg;
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            genProtocol(socket, msg,type, handler, context);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 构造协议
     *
     * @throws IOException
     */
    private static void genProtocol(SocketInfo socket, String msg, int type, Handler handler, Context context) throws IOException {
        Socket client = socket.getClient();
        byte[] bytes = msg.getBytes();         //消息内容
        int totalLen = 1 + 4 + bytes.length;   //消息长度
        DataOutputStream outs = socket.getOut();


        outs.writeByte(type);                   //写入消息类型
        outs.flush();
        outs.writeInt(totalLen);                //写入消息长度
        outs.flush();
        outs.write(bytes);                      //写入消息内容
        outs.flush();

        if(type == 2 ){
            System.out.println("for receive:" + String.valueOf(client.isClosed()));
            new ReceiveThread(socket ,context,handler).start();
        }

        if(type == 1){
            System.out.println(String.valueOf(client.isClosed()));
            client.close();
            outs.close();
            socket.getIs().close();
        }
    }
}
