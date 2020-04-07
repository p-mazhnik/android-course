package com.learning.helloworld;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM " + Student.TABLE_NAME)
    LiveData<List<Student>> getAll();

    @Query("SELECT * FROM " + Student.TABLE_NAME + " WHERE id IN (:userIds)")
    List<Student> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM " + Student.TABLE_NAME + " WHERE " + Student.COLUMN_FIRST_NAME + " LIKE :first AND "
            + Student.COLUMN_LAST_NAME + " LIKE :last LIMIT 1")
    Student findByName(String first, String last);

    @Insert
    void insertAll(List<Student> students);

    @Insert
    void insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);
}
