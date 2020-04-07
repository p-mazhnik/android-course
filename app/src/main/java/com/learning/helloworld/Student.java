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
    private String firstName;

    @ColumnInfo(name = Student.COLUMN_LAST_NAME)
    private String lastName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Student.COLUMN_ID)
    private Long id;

    @ColumnInfo(name = Student.COLUMN_IMAGE_PATH)
    private String imgPath;

    public Student(String firstName, String lastName, String imgPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imgPath = imgPath;
    }

    @Ignore
    public Student() {
        this.firstName = null;
        this.lastName = null;
        this.imgPath = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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
    @Ignore
    public static final String COLUMN_IMAGE_PATH = "image_path";
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
            students.add(new Student("имя"+i, "фамилия"+i, null));
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