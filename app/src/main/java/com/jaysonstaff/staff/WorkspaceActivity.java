package com.jaysonstaff.staff;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WorkspaceActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private EditText companyEditText;
    private EditText addressEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText employeeEditText;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspace);

        companyEditText = findViewById(R.id.company);
        addressEditText = findViewById(R.id.address);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        employeeEditText = findViewById(R.id.employee);
        Button saveButton = findViewById(R.id.save);
        ImageView imageViewPic = findViewById(R.id.pic);

        imageViewPic.setOnClickListener(v -> selectImageFromGallery());


        saveButton.setOnClickListener(v -> {
            String company = capitalizeFirstLetter(companyEditText.getText().toString());
            String address = capitalizeFirstLetter(addressEditText.getText().toString());
            String email = emailEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String employeeCount = employeeEditText.getText().toString().trim();

            if (TextUtils.isEmpty(company) || TextUtils.isEmpty(address) ||
                    TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) ||
                    TextUtils.isEmpty(employeeCount)) {
                Toast.makeText(WorkspaceActivity.this, "Vui lòng điền vào tất cả các lĩnh vực", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(WorkspaceActivity.this, "Định dạng email không hợp lệ", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.PHONE.matcher(phone).matches()) {
                Toast.makeText(WorkspaceActivity.this, "Định dạng số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            } else if (!TextUtils.isDigitsOnly(employeeCount)) {
                Toast.makeText(WorkspaceActivity.this, "Số lượng nhân viên phải là một số", Toast.LENGTH_SHORT).show();
            } else if (selectedImageUri == null) {
                Toast.makeText(WorkspaceActivity.this, "Vui lòng chọn một hình ảnh", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent();
                intent.putExtra("company", company);
                intent.putExtra("address", address);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("employeeCount", employeeCount);
                intent.putExtra("imageUri", selectedImageUri.toString());

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private String capitalizeFirstLetter(String input) {
        if (TextUtils.isEmpty(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private void selectImageFromGallery() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImageFromGallery();
            } else {
                Toast.makeText(this, "Quyền bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ImageView imageViewPic = findViewById(R.id.pic);
            imageViewPic.setImageURI(selectedImageUri);
        }
    }


}
