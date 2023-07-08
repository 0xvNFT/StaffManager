
package com.jaysonstaff.staff;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jaysonstaff.staff.adapter.StaffListAdapter;
import com.jaysonstaff.staff.adapter.UriTypeAdapter;
import com.jaysonstaff.staff.model.StaffModel;
import com.sahana.horizontalcalendar.HorizontalCalendar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StaffListActivity extends AppCompatActivity {
    private static final int UPDATE_STAFF_REQUEST_CODE = 1;
    private TextView mDateTextView;
    private StaffListAdapter staffListAdapter;
    private List<StaffModel> staffList;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);

        staffList = new ArrayList<>();
        staffListAdapter = new StaffListAdapter(this, staffList);

        ListView staffListView = findViewById(R.id.staffListView);
        staffListView.setAdapter(staffListAdapter);

        String name = getIntent().getStringExtra("name");


        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        if (name != null) {
            textViewWelcome.setText("Xin Chào, \n" + name);
        }

        HorizontalCalendar mHorizontalCalendar = findViewById(R.id.horizontalCalendar);
        mDateTextView = findViewById(R.id.dateTextView);
        mHorizontalCalendar.setOnDateSelectListener(dateModel -> mDateTextView.setText(dateModel != null ? dateModel.day + " "
                + dateModel.dayOfWeek + " " + dateModel.month + "," + dateModel.year : ""));


        if (getIntent().hasExtra("staffList")) {
            String staffListJson = getIntent().getStringExtra("staffList");
            Type type = new TypeToken<List<StaffModel>>() {
            }.getType();
            List<StaffModel> savedStaffList = new Gson().fromJson(staffListJson, type);

            if (savedStaffList != null && !savedStaffList.isEmpty()) {
                staffList.addAll(savedStaffList);
                staffListAdapter.notifyDataSetChanged();
            }
        }
        staffListAdapter.notifyDataSetChanged();
        loadSavedStaffData();

        staffListView.setOnItemClickListener((parent, view, position, id) -> {
            StaffModel selectedItem = (StaffModel) staffListAdapter.getItem(position);
            Intent intent = new Intent(StaffListActivity.this, UpdateStaffActivity.class);
            intent.putExtra("staffName", selectedItem.getName());
            intent.putExtra("staffEmail", selectedItem.getEmail());
            intent.putExtra("staffPhone", selectedItem.getPhone());
            intent.putExtra("staffPosition", selectedItem.getPosition());
            intent.putExtra("staffPart", selectedItem.getPart());
            intent.putExtra("staffImageUri", selectedItem.getStaffImageUri().toString());
            intent.putExtra("position", position);
            startActivityForResult(intent, UPDATE_STAFF_REQUEST_CODE);
        });
        Button hamburgerButton = findViewById(R.id.hamburger_btn);
        hamburgerButton.setOnClickListener(v -> openSupportActivity());
    }

    private void openSupportActivity() {
        Intent intent = new Intent(StaffListActivity.this, SupportActivity.class);
        startActivity(intent);
    }

    private void loadSavedStaffData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Uri.class, new UriTypeAdapter());
        Gson gson = gsonBuilder.create();

        String json = sharedPreferences.getString("staffList", null);
        Type type = new TypeToken<List<StaffModel>>() {
        }.getType();
        List<StaffModel> savedStaffList = gson.fromJson(json, type);

        if (savedStaffList != null && !savedStaffList.isEmpty()) {
            staffList.addAll(savedStaffList);
            staffListAdapter.notifyDataSetChanged();
        }
    }

    private void saveStaffData() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Uri.class, new UriTypeAdapter());
        Gson gson = gsonBuilder.create();
        Log.d("StaffListActivity", "Saving staff data...");
        String json = gson.toJson(staffList);
        editor.putString("staffList", json);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveStaffData();
        Log.d("StaffListActivity", "onDestroy called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_STAFF_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            int selectedPosition = -1;
            int updatedPosition = data.getIntExtra("position", selectedPosition);
            if (updatedPosition != -1) {
                String updatedName = data.getStringExtra("updatedName");
                String updatedEmail = data.getStringExtra("updatedEmail");
                String updatedPhone = data.getStringExtra("updatedPhone");
                String updatedPositions = data.getStringExtra("updatedPosition");
                String updatedPart = data.getStringExtra("updatedPart");
                Uri updatedImageUri = data.getParcelableExtra("updatedImageUri");

                StaffModel updatedStaff = staffList.get(updatedPosition);
                updatedStaff.setName(updatedName);
                updatedStaff.setEmail(updatedEmail);
                updatedStaff.setPhone(updatedPhone);
                updatedStaff.setPositions(updatedPositions);
                updatedStaff.setPart(updatedPart);
                updatedStaff.setStaffImageUri(updatedImageUri);

                staffList.set(updatedPosition, updatedStaff);

                staffListAdapter.notifyDataSetChanged();
            }
        }
    }

}

