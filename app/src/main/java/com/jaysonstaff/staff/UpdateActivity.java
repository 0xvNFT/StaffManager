package com.jaysonstaff.staff;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class UpdateActivity extends AppCompatActivity {
    private EditText editTextCompany;
    private EditText editTextAddress;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextEmployeeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editTextCompany = findViewById(R.id.editCompany);
        editTextAddress = findViewById(R.id.editAddress);
        editTextEmail = findViewById(R.id.editEmail);
        editTextPhone = findViewById(R.id.editPhone);
        editTextEmployeeCount = findViewById(R.id.editEmployee);
        ImageView imageView = findViewById(R.id.edit_pic);


        Intent intent = getIntent();
        String company = intent.getStringExtra("company");
        String address = intent.getStringExtra("address");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        String employeeCount = intent.getStringExtra("employeeCount");
        String imageUriString = intent.getStringExtra("imageUri");

        editTextCompany.setText(company);
        editTextAddress.setText(address);
        editTextEmail.setText(email);
        editTextPhone.setText(phone);
        editTextEmployeeCount.setText(employeeCount);

        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            Glide.with(this)
                    .load(imageUri)
                    .circleCrop()
                    .error(R.drawable.no_image)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.no_image);
        }

        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(v -> updateDataAndFinish());


    }

    private void updateDataAndFinish() {
        Intent intent = new Intent();
        intent.putExtra("position", getIntent().getIntExtra("position", 0));
        intent.putExtra("company", editTextCompany.getText().toString());
        intent.putExtra("address", editTextAddress.getText().toString());
        intent.putExtra("email", editTextEmail.getText().toString());
        intent.putExtra("phone", editTextPhone.getText().toString());
        intent.putExtra("employeeCount", editTextEmployeeCount.getText().toString());
        intent.putExtra("imageUri", getIntent().getStringExtra("imageUri"));
        setResult(Activity.RESULT_OK, intent);
        finish();

    }
}
