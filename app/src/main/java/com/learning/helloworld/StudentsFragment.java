package com.learning.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class StudentsFragment extends Fragment {
    private RecyclerView studentsView;
    private FloatingActionButton floatingActionButton;

    public interface StudentClickListener {
        void onStudentClicked(Student student);
    }

    private StudentClickListener onStudentClickListener;

    private StudentsAdapter studentsAdapter;

    private StudentViewModel mStudentViewModel;

    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);

        studentsView = view.findViewById(R.id.students);
        studentsView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentsAdapter = new StudentsAdapter(new StudentClickListener() {
            @Override
            public void onStudentClicked(Student student) {
                onStudentClickListener.onStudentClicked(student);
            }
        });
        studentsView.setAdapter(studentsAdapter);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click.
                onStudentClickListener.onStudentClicked(null);
            }
        });

        mStudentViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(StudentViewModel.class);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mStudentViewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable final List<Student> students) {
                studentsAdapter.setStudents(students);
            }
        });
    }

    static StudentsFragment newInstance(StudentClickListener studentClickListener) {
        StudentsFragment fragment = new StudentsFragment();
        fragment.onStudentClickListener = studentClickListener;
        return fragment;
    }
}