package com.learning.helloworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class StudentViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private ImageView photoImageView;
    private Student student;
    private StudentsFragment.StudentClickListener studentClickListener;

    public StudentViewHolder(@NonNull View itemView, StudentsFragment.StudentClickListener studentClickListener) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.name);
        photoImageView = itemView.findViewById(R.id.photo);
        this.studentClickListener = studentClickListener;
    }

    void bind(Student student) {
        this.student = student;
        nameTextView.setText(student.getFirstName() + " " + student.getLastName());
        if(student.getImgPath() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(student.getImgPath());
            if(bitmap != null) photoImageView.setImageBitmap(bitmap);
        }
        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentClickListener.onStudentClicked(StudentViewHolder.this.student);
            }
        });
    }
}

class StudentsAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    List<Student> mStudents;
    private StudentsFragment.StudentClickListener studentClickListener;

    StudentsAdapter(StudentsFragment.StudentClickListener studentClickListener) {
        this.studentClickListener = studentClickListener;
    }

    void setStudents(List<Student> students){
        mStudents = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.student, parent, false);
        return new StudentViewHolder(itemView, studentClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(mStudents.get(position));
    }

    @Override
    public int getItemCount() {
        if(mStudents == null) {
            return 0;
        }
        return mStudents.size();
    }
}