package com.example.socketproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hecheng on 2017/11/5.
 */

public class StudentAdapter extends ArrayAdapter<Student> {

    private int resourceId;

    public StudentAdapter(Context context, int textViewResourceId, List<Student> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView studentId = (TextView) view.findViewById(R.id.id_text);
        TextView studentName = (TextView) view.findViewById(R.id.name_text);
        TextView studentMajor = (TextView) view.findViewById(R.id.major_text);
        TextView studentClass = (TextView) view.findViewById(R.id.class_id_text);
        studentId.setText(student.getId());
        studentName.setText(student.getName());
        studentMajor.setText(student.getMajor());
        studentClass.setText(student.getClassid());
        return  view;
    }
}
