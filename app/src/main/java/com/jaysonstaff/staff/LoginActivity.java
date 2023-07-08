package com.jaysonstaff.staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "com.jaysonstaff.staff.sharedprefs";
    private static final String KEY_NAME = "name";
    private EditText editTextName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = findViewById(R.id.editTextName);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String savedName = getSavedName();
        if (!savedName.isEmpty()) {
            startMainActivity(savedName);
            return;
        }

        editTextName.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                    return "";
                }
            }
            return null;
        }});

        buttonLogin.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(LoginActivity.this, "xin hãy nhập tên của bạn", Toast.LENGTH_SHORT).show();
            } else {
                saveName(name);
                startMainActivity(name);
            }
        });
    }

    private String getSavedName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    private void saveName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    private void startMainActivity(String name) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();
    }
}
