package com.example.socketproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by hecheng on 2017/10/26.
 */

public class ViewFragment extends Fragment {
    private List<Student> studentList ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment, container, false);
        studentList = ((MainActivity)getActivity()).getStudentList();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(((MainActivity)getActivity()), android.R.layout.simple_list_item_1,data);
        StudentAdapter adapter = new StudentAdapter(((MainActivity)getActivity()), R.layout.student_item, studentList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).setPosition(position);
                ((MainActivity)getActivity()).replaceFragment(new UpdateFragment());
            }
        });
        return view;
    }
}
