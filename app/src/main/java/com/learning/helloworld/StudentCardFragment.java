package com.learning.helloworld;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

public class StudentCardFragment extends Fragment {
    public static final String KEY_STUDENT = "student";
    public static final int PROFILE_IMAGE_REQ_CODE = 101;

    private TextInputLayout firstNameTextField;
    private TextInputLayout lastNameTextField;
    private AppCompatImageView studentImage;
    private FloatingActionButton addImageButton;
    private Button saveButton;
    private Button deleteButton;

    private StudentViewModel mStudentViewModel;

    private Student student;

    static StudentCardFragment newInstance() {
        return new StudentCardFragment();
    }

    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_student_card, container, false);
        firstNameTextField = (TextInputLayout) view.findViewById(R.id.firstNameTextField);
        lastNameTextField  = (TextInputLayout) view.findViewById(R.id.lastNameTextField);
        studentImage = (AppCompatImageView) view.findViewById(R.id.studentImage);
        saveButton = (Button) view.findViewById(R.id.studentSaveButton);
        deleteButton = (Button) view.findViewById(R.id.studentDeleteButton);
        addImageButton = (FloatingActionButton) view.findViewById(R.id.addImageButton);

        firstNameTextField.getEditText().addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable edt) {
                if (firstNameTextField.getEditText().getText().length() > 0) {
                    firstNameTextField.setError(null);
                }
            }
        });

        lastNameTextField.getEditText().addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable edt) {
                if (lastNameTextField.getEditText().getText().length() > 0) {
                    lastNameTextField.setError(null);
                }
            }
        });

        if (getArguments() != null) {
            student = (Student) getArguments().getSerializable(KEY_STUDENT);
            firstNameTextField.getEditText().setText(student.getFirstName());
            lastNameTextField.getEditText().setText(student.getLastName());
            deleteButton.setVisibility(Button.VISIBLE);
        }
        else {
            student = new Student();
            deleteButton.setVisibility(Button.INVISIBLE);
        }

        mStudentViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(StudentViewModel.class);

        final ImagePicker.Builder imageBuilder = ImagePicker.Companion.with(this)
                .cropSquare()
                .maxResultSize(512, 512);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageBuilder.start(PROFILE_IMAGE_REQ_CODE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                String firstName = firstNameTextField.getEditText().getText().toString();
                String lastName = lastNameTextField.getEditText().getText().toString();
                if(firstName.length() <= 0) {
                    firstNameTextField.setError("required");
                    error = true;
                }
                if(lastName.length() <= 0) {
                    lastNameTextField.setError("required");
                    error = true;
                }
                if(error) return;
                if(!(firstName.equals(student.getFirstName()) && lastName.equals(student.getLastName()))){
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    mStudentViewModel.saveStudent(student);
                }
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Delete student")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mStudentViewModel.deleteStudent(student);
                                getActivity().getSupportFragmentManager().popBackStackImmediate();
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == PROFILE_IMAGE_REQ_CODE) {
            File imgFile = ImagePicker.Companion.getFile(data);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            studentImage.setImageBitmap(myBitmap);
        }
        else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        }
    }
}
