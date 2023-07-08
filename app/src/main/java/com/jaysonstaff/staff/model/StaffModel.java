package com.jaysonstaff.staff.model;

import android.net.Uri;

public class StaffModel {
    private final String staffImageUriString;
    private String name;
    private String email;
    private String phone;
    private String position;
    private String part;

    public StaffModel(Uri staffImageUri, String name, String email, String phone, String position, String part) {
        this.staffImageUriString = staffImageUri != null ? staffImageUri.toString() : null;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.part = part;
    }

    public Uri getStaffImageUri() {
        return staffImageUriString != null ? Uri.parse(staffImageUriString) : null;
    }

    public void setStaffImageUri(Uri staffImageUri) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPositions(String position) {
        this.position = position;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}
