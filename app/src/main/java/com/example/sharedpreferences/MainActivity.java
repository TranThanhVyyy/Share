package com.example.sharedpreferences;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private EditText edtStudentID, edtStudentName, edtlop;
    private Button btnSave, btnView, btnDelete;
    private TextView tvFeedback;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "StudentPrefs";
    private static final String STUDENT_ID_KEY = "student_id";
    private static final String STUDENT_NAME_KEY = "student_name";
    private static final String STUDENT_CLASS_KEY = "student_class";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            edtStudentID = findViewById(R.id.edtStudentID);
            edtStudentName = findViewById(R.id.edtStudentName);
            edtlop = findViewById(R.id.edtlop);
            btnSave = findViewById(R.id.btnSave);
            btnView = findViewById(R.id.btnView);
            btnDelete = findViewById(R.id.btnDelete);
            tvFeedback = findViewById(R.id.tvFeedback);
            sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveStudentInfo();
                }
            });
            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewStudentInfo();
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteStudentInfo();
                }
            });
        }
        private void saveStudentInfo() {
            String studentID = edtStudentID.getText().toString().trim();
            String studentName = edtStudentName.getText().toString().trim();
            String studentClass = edtlop.getText().toString().trim();
            if (studentID.isEmpty() || studentName.isEmpty() || studentClass.isEmpty()) {
                tvFeedback.setText("Vui lòng nhập đủ thông tin Mã sinh viên, Tên và Lớp sinh hoạt.");
                return;
            }
            String existingID = sharedPreferences.getString(STUDENT_ID_KEY, "");
            if (existingID.equals(studentID)) {
                tvFeedback.setText("Mã sinh viên đã tồn tại. Vui lòng sử dụng mã khác.");
                return;
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(STUDENT_ID_KEY, studentID);
            editor.putString(STUDENT_NAME_KEY, studentName);
            editor.putString(STUDENT_CLASS_KEY, studentClass);
            editor.apply();

            tvFeedback.setText("Lưu thông tin thành công!");
            edtStudentID.setText("");
            edtStudentName.setText("");
            edtlop.setText("");
        }
        private void viewStudentInfo() {
            String studentID = sharedPreferences.getString(STUDENT_ID_KEY, "");
            String studentName = sharedPreferences.getString(STUDENT_NAME_KEY, "");
            String studentClass = sharedPreferences.getString(STUDENT_CLASS_KEY, "");

            if (studentID.isEmpty() || studentName.isEmpty() || studentClass.isEmpty()) {
                tvFeedback.setText("Không có thông tin sinh viên đã lưu.");
            } else {
                tvFeedback.setText("Mã SV: " + studentID + "\nTên: " + studentName + "\nLớp: " + studentClass);
            }
        }
        private void deleteStudentInfo() {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            tvFeedback.setText("Xóa thông tin thành công.");
            edtStudentID.setText("");
            edtStudentName.setText("");
            edtlop.setText("");
        }
    }
