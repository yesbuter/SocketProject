package com.example.socketproject;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private ViewFragment viewFragment;
    private CreateFragment createFragment;
    private MyService.SocketLoadBinder socketloadBinder;



    private SocketInfo clientsocket;
    private String studentMsg;
    private List<Student> studentList;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public SocketInfo getClientsocket() {
        return clientsocket;
    }

    public void setClientsocket(SocketInfo clientsocket) {
        this.clientsocket = clientsocket;
    }
    @Override
    protected void onDestroy() {
        if(clientsocket != null){
            new DisConnectThread(MainActivity.this, clientsocket, null, "溜了溜了").start();
        }
        System.out.print("结束");
        super.onDestroy();
        //unbindService(serviceConnection);
    }

    public String getStudentMsg() {
        return studentMsg;
    }

    public List<Student> getStudentList(){
        return  studentList;
    }

    public void setStudentMsg(String studentMsg) {
        this.studentMsg = studentMsg;
        studentList = new Gson().fromJson(studentMsg, new TypeToken<List<Student>>(){}.getType());
        for(Student student : studentList){
            System.out.println(student.getId()+"  "+student.getName());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
        Toast.makeText(MainActivity.this, "欢迎使用(づ￣3￣)づ╭❤～",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_item:
                // TODO: 2017/10/25
                //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                }
                replaceFragment(homeFragment);
                break;
            case R.id.view_item:
                // TODO: 2017/10/26
                if(getStudentList()!=null){
                    if(viewFragment == null){
                        viewFragment = new ViewFragment();
                    }
                    replaceFragment(viewFragment);
                }
                else{
                    Toast.makeText(MainActivity.this, "文件未获取_(:з」∠)_", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.create_item:
                // TODO: 2017/10/25
                if(clientsocket!=null && !clientsocket.getClient().isClosed()){
                    if(createFragment == null){
                        createFragment = new CreateFragment();
                    }
                    replaceFragment(createFragment);
                }
                else{
                    Toast.makeText(MainActivity.this, "Socket未连接", Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
        return true;
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.all_layout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
