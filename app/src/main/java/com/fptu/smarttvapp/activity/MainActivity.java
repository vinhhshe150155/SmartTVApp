package com.fptu.smarttvapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fptu.smarttvapp.R;

public class MainActivity extends AppCompatActivity {
    private EditText edtCode;
    private Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAction();
    }

    private void initAction() {
        btnEnter.setOnClickListener(v -> {
            if (isValidCode()) {
                navigateToDisplayActivity();
            } else {
                Toast.makeText(MainActivity.this, "Invalid Kode.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidCode() {
        String code = edtCode.getText().toString();
        return code.equalsIgnoreCase("bRuH");
    }

    private void initData() {

    }

    private void initView() {
        edtCode = findViewById(R.id.edtCode);
        btnEnter = findViewById(R.id.btnEnter);
    }

    private void navigateToDisplayActivity() {
        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        startActivity(intent);
    }
}
