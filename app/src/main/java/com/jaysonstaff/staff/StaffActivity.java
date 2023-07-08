package com.jaysonstaff.staff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jaysonstaff.staff.model.StaffModel;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView picStaffImageView;
    private Uri selectedImageUri;
    private List<StaffModel> staffList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        staffList = new ArrayList<>();
        picStaffImageView = findViewById(R.id.pic_staff);

        picStaffImageView.setOnClickListener(v -> openImagePicker());
        Button saveStaffButton = findViewById(R.id.save_staff);
        saveStaffButton.setOnClickListener(v -> saveStaffItem());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            picStaffImageView.setImageURI(selectedImageUri);
        }
    }

    private void saveStaffItem() {
        if (selectedImageUri != null) {
            String name = ((EditText) findViewById(R.id.first_last_name)).getText().toString();
            String email = ((EditText) findViewById(R.id.email_staff)).getText().toString();
            String phone = ((EditText) findViewById(R.id.phone_staff)).getText().toString();
            String position = ((EditText) findViewById(R.id.position)).getText().toString();
            String part = ((EditText) findViewById(R.id.part)).getText().toString();

            StaffModel staffModel = new StaffModel(selectedImageUri, name, email, phone, position, part);
            staffList.add(staffModel);

            Intent intent = new Intent(this, StaffListActivity.class);
            intent.putExtra("staffList", new Gson().toJson(staffList));
            intent.putExtra("imageUri", selectedImageUri.toString());
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("phone", phone);
            intent.putExtra("position", position);
            intent.putExtra("part", part);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }


}