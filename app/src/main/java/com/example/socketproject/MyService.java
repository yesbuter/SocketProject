package com.example.socketproject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class MyService extends Service {

    private Socket socket = null;
    public MyService() {
    }

    private SocketLoadBinder socketBinder = new SocketLoadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return socketBinder;
    }

    class SocketLoadBinder extends Binder {


        public boolean connectService(String serviceIp, int port, Handler myHandle){
            // TODO: 2017/10/27  初始化socket连接
            /*
            this.myHandle = myHandle;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket("10.0.2.2",1995);
                        socket.setSoTimeout(5000);
                        System.out.println("okokokok");
                        if(socket.isConnected()){
                            myHandle.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyService.this, "成功连接" ,Toast.LENGTH_LONG).show();
                                }
                            });

                        }else{
                            Toast.makeText(MyService.this, "连接失败",Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("客户端异常:" + e.getMessage() );
                        Toast.makeText(MyService.this, "客户端异常:" +e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }).start();
            return true;*/

            try
            {
                //new ConnectThread(serviceIp,port,socket,myHandle,MyService.this).start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return true;
        }
        public void test(){
            Toast.makeText(MyService.this, "欢迎使用(づ￣3￣)づ╭❤～",Toast.LENGTH_LONG).show();
        }
        public void askForFile(){

        }

        public boolean sendHeartbeat(){
            // TODO: 2017/10/28   发送短数据确认连接
            return true;
        }


    }
}
