package com.example.socketproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by hecheng on 2017/11/3.
 */

public class ConnectThread extends Thread {

    Context context;
    private String serverip;
    private int port;
    private Socket socket;
    private  Handler handler;

    public ConnectThread(String serverip, int port, Handler myHandle, Context context) {
        this.handler = myHandle;
        this.serverip = serverip;
        this.port = port;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverip,port);
            socket.setSoTimeout(5000);
            System.out.println("okokokok");
            if(socket.isConnected()){
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                ((MainActivity)context).setClientsocket(new SocketInfo(socket));
            }
            else{
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
            System.out.println("客户端异常:" + e.getMessage() );
            Message message = new Message();
            message.what = 0;
            handler.sendMessage(message);
        }

    }
}
