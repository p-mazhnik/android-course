package com.learning.helloworld;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StudentsFragment studentsFragment = StudentsFragment.newInstance(new StudentsFragment.StudentClickListener() {
            @Override
            public void onStudentClicked(Student student) {
                showStudentCard(student);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, studentsFragment)
                .commit();
    }

    private void showStudentCard(Student student) {
        StudentCardFragment studentCardFragment = StudentCardFragment.newInstance();

        if(student != null) {
            Bundle args = new Bundle();
            args.putSerializable(StudentCardFragment.KEY_STUDENT, student);
            studentCardFragment.setArguments(args);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, studentCardFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
