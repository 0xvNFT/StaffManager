package com.jaysonstaff.staff;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.jaysonstaff.staff.model.StaffModel;

public class UpdateStaffActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView editPicStaffImageView;
    private Uri selectedImageUri;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);

        editPicStaffImageView = findViewById(R.id.edit_pic_staff);
        EditText editFirstLastName = findViewById(R.id.edit_first_last_name);
        EditText editEmailStaff = findViewById(R.id.edit_email_staff);
        EditText editPhoneStaff = findViewById(R.id.edit_phone_staff);
        EditText editPosition = findViewById(R.id.edit_position);
        EditText editPart = findViewById(R.id.edit_part);

        String staffName = getIntent().getStringExtra("staffName");
        String staffEmail = getIntent().getStringExtra("staffEmail");
        String staffPhone = getIntent().getStringExtra("staffPhone");
        String staffPosition = getIntent().getStringExtra("staffPosition");
        String staffPart = getIntent().getStringExtra("staffPart");
        String staffImageUriString = getIntent().getStringExtra("staffImageUri");

        StaffModel staffItem = new StaffModel(Uri.parse(staffImageUriString), staffName, staffEmail, staffPhone, staffPosition, staffPart);

        editFirstLastName.setText(staffItem.getName());
        editEmailStaff.setText(staffItem.getEmail());
        editPhoneStaff.setText(staffItem.getPhone());
        editPosition.setText(staffItem.getPosition());
        editPart.setText(staffItem.getPart());

        editPicStaffImageView.setOnClickListener(v -> openImagePicker());

        Button updateStaffButton = findViewById(R.id.updateStaffButton);
        updateStaffButton.setOnClickListener(v -> {
            String updatedName = editFirstLastName.getText().toString();
            String updatedEmail = editEmailStaff.getText().toString();
            String updatedPhone = editPhoneStaff.getText().toString();
            String updatedPositions = editPosition.getText().toString();
            String updatedPart = editPart.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("position", position);
            resultIntent.putExtra("updatedName", updatedName);
            resultIntent.putExtra("updatedEmail", updatedEmail);
            resultIntent.putExtra("updatedPhone", updatedPhone);
            resultIntent.putExtra("updatedPosition", updatedPositions);
            resultIntent.putExtra("updatedPart", updatedPart);
            resultIntent.putExtra("updatedImageUri", selectedImageUri);

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
        position = getIntent().getIntExtra("position", -1);
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
            editPicStaffImageView.setImageURI(selectedImageUri);
        }
    }
}
