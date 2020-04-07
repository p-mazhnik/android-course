package com.learning.helloworld;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = Student.TABLE_NAME)
class Student implements Serializable {
    @ColumnInfo(name = Student.COLUMN_FIRST_NAME)
    String firstName;

    @ColumnInfo(name = Student.COLUMN_LAST_NAME)
    String lastName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Student.COLUMN_ID)
    Long id;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student() {
        this.firstName = null;
        this.lastName = null;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Ignore
    public static final String TABLE_NAME = "students";
    @Ignore
    public static final String COLUMN_ID = "id";
    @Ignore
    public static final String COLUMN_FIRST_NAME = "first_name";
    @Ignore
    public static final String COLUMN_LAST_NAME = "last_name";
}

class StudentLoader {
    private StudentDao mStudentDao;
    private LiveData<List<Student>> mAllStudents;

    StudentLoader(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mStudentDao = db.studentDao();
        mAllStudents = mStudentDao.getAll();
    }

    LiveData<List<Student>> loadStudents() {
        return mAllStudents;
    }

    static private List<Student> generateStudents() {
        List<Student> students = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            students.add(new Student("имя"+i, "фамилия"+i));
        }
        return students;
    }

    void saveStudents(final List<Student> students) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mStudentDao.insertAll(students);
            }
        });
    }

    void insert(final Student student) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mStudentDao.insert(student);
            }
        });
    }

    void update(final Student student) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mStudentDao.update(student);
            }
        });
    }

    void delete(final Student student) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mStudentDao.delete(student);
            }
        });
    }
}