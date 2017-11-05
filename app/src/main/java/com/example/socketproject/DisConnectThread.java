package com.example.socketproject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by hecheng on 2017/11/3.
 */

public class DisConnectThread extends Thread{
    Context context;
    private SocketInfo socket;
    private Handler handler;
    private String msg;

    public DisConnectThread(Context context, SocketInfo socket, Handler handler, String msg){
        this.context = context;
        this.socket = socket;
        this.msg = msg;
        this.handler = handler;
    }

    @Override
    public void run() {
        if(socket.getClient() != null){
            new SendThread(socket ,msg ,1, null, null).start();
            if(handler != null){
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
        }
    }
}
