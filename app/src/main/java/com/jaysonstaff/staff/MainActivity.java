package com.jaysonstaff.staff;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jaysonstaff.staff.adapter.MainAdapter;
import com.jaysonstaff.staff.adapter.UriTypeAdapter;
import com.jaysonstaff.staff.model.MainModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_WORKSPACE = 1;
    private List<MainModel> itemList;
    private MainAdapter adapter;
    private SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        ListView listView = findViewById(R.id.listView);
        Button createButton = findViewById(R.id.create);

        itemList = new ArrayList<>();
        adapter = new MainAdapter(this, itemList);
        listView.setAdapter(adapter);

        String name = getIntent().getStringExtra("name");
        if (name != null) {
            textViewWelcome.setText("Xin Chào, \n" + name + "!");
        }


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadSavedData();
        createButton.setOnClickListener(v -> openWorkspaceActivity());

        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View itemMainView = inflater.inflate(R.layout.item_main, null, false);
        Button addButton = itemMainView.findViewById(R.id.add);
        addButton.setOnClickListener(v -> openStaffActivity());

        Button hamburgerButton = findViewById(R.id.hamburger_btn);
        hamburgerButton.setOnClickListener(v -> openSupportActivity());

        listView.setOnItemClickListener((parent, view, position, id) -> {

            MainModel selectedItem = (MainModel) adapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra("company", selectedItem.getCompany());
            intent.putExtra("address", selectedItem.getAddress());
            intent.putExtra("email", selectedItem.getEmail());
            intent.putExtra("phone", selectedItem.getPhone());
            intent.putExtra("employeeCount", selectedItem.getEmployeeCount());
            if (selectedItem.getImageUri() != null) {
                intent.putExtra("imageUri", selectedItem.getImageUri().toString());
            }
            startActivity(intent);
        });
    }

    private void openSupportActivity() {
        Intent intent = new Intent(this, SupportActivity.class);
        startActivity(intent);
    }

    private void openWorkspaceActivity() {
        Intent intent = new Intent(this, WorkspaceActivity.class);
        startActivityForResult(intent, REQUEST_CODE_WORKSPACE);
    }

    private void openStaffActivity() {
        Intent intent = new Intent(this, StaffActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_WORKSPACE && resultCode == RESULT_OK && data != null) {
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                String company = data.getStringExtra("company");
                String address = data.getStringExtra("address");
                String email = data.getStringExtra("email");
                String phone = data.getStringExtra("phone");
                String employeeCount = data.getStringExtra("employeeCount");
                String imageUriString = data.getStringExtra("imageUri");

                if (company != null && address != null && email != null && phone != null && employeeCount != null && imageUriString != null) {

                    MainModel selectedItem = itemList.get(position);
                    selectedItem.setCompany(company);
                    selectedItem.setAddress(address);
                    selectedItem.setEmail(email);
                    selectedItem.setPhone(phone);
                    selectedItem.setEmployeeCount(employeeCount);
                    selectedItem.setImageUri(Uri.parse(imageUriString));

                    adapter.notifyDataSetChanged();
                    saveData();
                }
            } else {
                String company = data.getStringExtra("company");
                String address = data.getStringExtra("address");
                String email = data.getStringExtra("email");
                String phone = data.getStringExtra("phone");
                String employeeCount = data.getStringExtra("employeeCount");
                String imageUriString = data.getStringExtra("imageUri");

                if (company != null && address != null && email != null && phone != null && employeeCount != null && imageUriString != null) {
                    Uri imageUri = Uri.parse(imageUriString);
                    MainModel mainModel = new MainModel(company, address, email, phone, employeeCount, imageUri);
                    itemList.add(mainModel);
                    adapter.notifyDataSetChanged();
                    saveData();
                }
            }
        }
    }

    private void loadSavedData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Uri.class, new UriTypeAdapter());
        Gson gson = gsonBuilder.create();

        String json = sharedPreferences.getString("workspaces", null);
        Type type = new TypeToken<List<MainModel>>() {
        }.getType();
        List<MainModel> workspaceList = gson.fromJson(json, type);
        itemList.clear();
        if (workspaceList != null && !workspaceList.isEmpty()) {
            itemList.addAll(workspaceList);
            adapter.notifyDataSetChanged();
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Uri.class, new UriTypeAdapter());
        Gson gson = gsonBuilder.create();

        if (itemList != null && !itemList.isEmpty()) {
            String json = gson.toJson(itemList);
            editor.putString("workspaces", json);
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSavedData();
    }
}
