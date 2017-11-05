package com.example.socketproject;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by hecheng on 2017/11/5.
 */

public class CreateFragment extends Fragment {
    private EditText id_create;
    private EditText name_create;
    private EditText major_create;
    private EditText class_create;

    private Button create_button;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.create_fragment, container, false);

        id_create = (EditText) view.findViewById(R.id.id_create);
        name_create = (EditText) view.findViewById(R.id.name_create);
        class_create = (EditText) view.findViewById(R.id.class_id_create);
        major_create = (EditText) view.findViewById(R.id.major_create);
        create_button = (Button) view.findViewById(R.id.create_button);


        id_create.setText("");
        name_create.setText("");
        major_create.setText("");
        class_create.setText("");


        create_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String newId = id_create.getText().toString();
                String newName = name_create.getText().toString();
                String newMajor = major_create.getText().toString();
                String newClass = class_create.getText().toString();
                boolean isOK = true;
                for(Student student : ((MainActivity)getActivity()).getStudentList()){
                    if(newId.equals(student.getId())){
                        isOK = false;
                    }
                }
                if(!isOK){
                    Toast.makeText(getActivity(),"学号已经存在", Toast.LENGTH_SHORT).show();
                }
                else if(newId.equals("")){
                    Toast.makeText(getActivity(),"学号不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(newName.equals("")){
                    Toast.makeText(getActivity(),"姓名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(newMajor.equals("")){
                    Toast.makeText(getActivity(),"专业不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(newClass.equals("")){
                    Toast.makeText(getActivity(),"班级不能为空", Toast.LENGTH_SHORT).show();
                }

                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("新增学生");
                    dialog.setMessage("确认要进行新增操作吗？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Student student = new Student();
                            student.setId(id_create.getText().toString());
                            student.setName(name_create.getText().toString());
                            student.setMajor(major_create.getText().toString());
                            student.setClassid(class_create.getText().toString());
                            ((MainActivity) getActivity()).getStudentList().add(student);
                            String msg = new Gson().toJson(student);
                            new SendThread(((MainActivity) getActivity()).getClientsocket(), msg, 5, null, null).start();
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            }
        });

        return view;
    }
}