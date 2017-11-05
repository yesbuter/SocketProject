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

public class UpdateFragment extends Fragment {
    private EditText id_edit;
    private EditText name_edit;
    private EditText major_edit;
    private EditText class_edit;

    private Button update_button;
    private Button delete_button;
    private int position;
    private Student student;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.update_fragment, container, false);

        id_edit = (EditText) view.findViewById(R.id.id_edit);
        name_edit = (EditText) view.findViewById(R.id.name_edit);
        class_edit = (EditText) view.findViewById(R.id.class_id_edit);
        major_edit = (EditText) view.findViewById(R.id.major_edit);
        update_button = (Button) view.findViewById(R.id.update_button);
        delete_button = (Button) view.findViewById(R.id.delete_button);

        position = ((MainActivity)getActivity()).getPosition();
        student = ((MainActivity)getActivity()).getStudentList().get(position);

        id_edit.setText(student.getId()+"\r\n（学号不可更改，只能删除）");
        name_edit.setText(student.getName());
        major_edit.setText(student.getMajor());
        class_edit.setText(student.getClassid());

        id_edit.setEnabled(false);

        update_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //String newId = id_edit.getText().toString();
                String newName = name_edit.getText().toString();
                String newMajor = major_edit.getText().toString();
                String newClass = class_edit.getText().toString();
                if(
                        newName.equals(student.getName())&&
                        newMajor.equals(student.getMajor())&&
                        newClass.equals(student.getClassid())){
                    Toast.makeText(getActivity(),"请输入更新内容", Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("更新");
                    dialog.setMessage("确认要进行更新操作吗？");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //((MainActivity) getActivity()).getStudentList().get(position).setId(id_edit.getText().toString());
                            ((MainActivity) getActivity()).getStudentList().get(position).setName(name_edit.getText().toString());
                            ((MainActivity) getActivity()).getStudentList().get(position).setMajor(major_edit.getText().toString());
                            ((MainActivity) getActivity()).getStudentList().get(position).setClassid(class_edit.getText().toString());
                            student = ((MainActivity) getActivity()).getStudentList().get(position);
                            String msg = new Gson().toJson(student);
                            new SendThread(((MainActivity) getActivity()).getClientsocket(), msg, 3, null, null).start();
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

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("删除");
                dialog.setMessage("确认要删除吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SendThread(((MainActivity) getActivity()).getClientsocket(), student.getId(), 4, null, null).start();
                        ((MainActivity) getActivity()).getStudentList().remove(position);
                        getFragmentManager().popBackStack();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
        return view;
    }
}
