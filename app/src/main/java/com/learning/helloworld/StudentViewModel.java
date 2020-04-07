package com.learning.helloworld;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private StudentLoader mLoader;

    private LiveData<List<Student>> mAllStudents;

    public StudentViewModel (Application application) {
        super(application);
        mLoader = new StudentLoader(application);
        mAllStudents = mLoader.loadStudents();
    }

    LiveData<List<Student>> getAllStudents() { return mAllStudents; }

    public void saveStudent(Student student) {
        if(student.id == null) {
            mLoader.insert(student);
        }
        else {
            mLoader.update(student);
        }
    }

    public void deleteStudent(Student student) {
        mLoader.delete(student);
    }
}
