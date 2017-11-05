package com.example.socketproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by hecheng on 2017/10/26.
 */

public class HomeFragment extends Fragment {

    private SocketInfo clientsocket = null;
    private  EditText ip_edit_text;
    private Button button_flag_on;
    private Button button_flag_off;
    private Button button_ask;
    private Button button_say_hi;
    private TextView state_view;
    private TextView text_text_view;


    public static final int FAIL_CONNECT=0;
    public static final int SUCCESS_CONNECT = 1;
    public static final int DISCONNECT = 2;
    public static final int GETFILE = 3;
    private Handler handler = new Handler(){

        public void handleMessage(Message msg){
            switch (msg.what) {
                case SUCCESS_CONNECT:
                    Toast.makeText(getActivity(),"连接成功(￣︶￣)↗",Toast.LENGTH_SHORT).show();
                    button_flag_on.setEnabled(false);
                    button_flag_off.setEnabled(true);
                    button_ask.setEnabled(true);
                    button_say_hi.setEnabled(true);
                    state_view.setText("连接中");
                    clientsocket = ((MainActivity)getActivity()).getClientsocket();
                    break;
                case FAIL_CONNECT:
                    Toast.makeText(getActivity(),"连接失败O__O …",Toast.LENGTH_SHORT).show();
                    break;
                case DISCONNECT:
                    Toast.makeText(getActivity(),"断开成功_(:з」∠)_",Toast.LENGTH_SHORT).show();
                    //((MainActivity)getActivity()).setStudentMsg(null);
                    ((MainActivity)getActivity()).setClientsocket(null);
                    button_ask.setEnabled(false);
                    button_say_hi.setEnabled(false);
                    button_flag_off.setEnabled(false);
                    button_flag_on.setEnabled(true);
                    break;
                case GETFILE:
                    Toast.makeText(getActivity(),"成功获得文件",Toast.LENGTH_SHORT).show();
                    button_ask.setText("重新请求文件");
                    //button_ask.setText(((MainActivity)getActivity()).getStudentMsg());
                    text_text_view.setText("获得文件内容：\r\n" + ((MainActivity)getActivity()).getStudentMsg());
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //启动并绑定服务到MainActivity
        //Intent bindIntent = new Intent(getActivity(), MyService.class);
        //getActivity().startService(bindIntent);
        //getActivity().bindService(bindIntent, ((MainActivity)getActivity()).getServiceConnection(), Context.BIND_AUTO_CREATE);

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ip_edit_text = (EditText) view.findViewById(R.id.ip_edit_text);
        button_flag_on = (Button) view.findViewById(R.id.button_on);
        button_flag_off = (Button) view.findViewById(R.id.button_off);
        state_view = (TextView) view.findViewById(R.id.state_view);
        button_ask = (Button) view.findViewById(R.id.button_ask_for_file);
        button_say_hi = (Button) view.findViewById(R.id.button_say_hi);
        text_text_view = (TextView) view.findViewById(R.id.text_test_view);

        if(((MainActivity)getActivity()).getClientsocket()!=null){
            if(((MainActivity)getActivity()).getClientsocket().getClient().isConnected()&&!((MainActivity)getActivity()).getClientsocket().getClient().isClosed()){
                clientsocket = ((MainActivity)getActivity()).getClientsocket();
                button_flag_on.setEnabled(false);
                button_flag_off.setEnabled(true);
                button_ask.setEnabled(true);
                button_say_hi.setEnabled(true);
                state_view.setText("连接中");
            }
        }
        if(((MainActivity)getActivity()).getStudentMsg()!=null){
            button_ask.setText("重新请求文件");
            text_text_view.setText("获得文件内容：\r\n" + ((MainActivity)getActivity()).getStudentMsg());
        }

        else{
            button_flag_off.setEnabled(false);
            button_ask.setEnabled(false);
        }



        button_flag_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //在服务中初始化socket
                //MyService.SocketLoadBinder socketLoadBinder = ((MainActivity) getActivity()).getSocketloadBinder();
                //socketLoadBinder.test();
                //socketLoadBinder.connectService(ip_edit_text.getText().toString(), 1995);
                //socketLoadBinder.connectService("10.0.2.2", 1995, ((MainActivity) getActivity()).getMyHandle());
                new ConnectThread(ip_edit_text.getText().toString(), 1995, handler ,getActivity()).start();
            }
        });
        button_flag_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //关闭socket连接
                new DisConnectThread(getActivity(), clientsocket, handler, "拜拜").start();
            }
        });
        button_ask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //发送传文件的请求
                if(clientsocket!=null&&!clientsocket.getClient().isConnected()) {
                    Toast.makeText(getActivity(), "故障...." , Toast.LENGTH_LONG).show();
                    new ConnectThread(ip_edit_text.getText().toString(), 1995, handler ,getActivity()).start();
                }
                else{
                    new SendThread(clientsocket, "for file", 2, handler, getActivity()).start();
                }
            }
        });
        return view;
    }


    @Override
    public void onDestroy() {
        getActivity().finish();
        super.onDestroy();
    }
}
