package com.jaysonstaff.staff.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class MainModel implements Parcelable {
    public static final Creator<MainModel> CREATOR = new Creator<MainModel>() {
        @Override
        public MainModel createFromParcel(Parcel in) {
            return new MainModel(in);
        }

        @Override
        public MainModel[] newArray(int size) {
            return new MainModel[size];
        }
    };
    private String company;
    private String address;
    private String email;
    private String phone;
    private String employeeCount;
    private Uri imageUri;
    private String name;

    public MainModel(String company, String address, String email, String phone, String employeeCount, Uri imageUri) {
        this.company = company;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.employeeCount = employeeCount;
        this.imageUri = imageUri;
    }

    protected MainModel(Parcel in) {
        company = in.readString();
        address = in.readString();
        email = in.readString();
        phone = in.readString();
        employeeCount = in.readString();
        imageUri = in.readParcelable(Uri.class.getClassLoader()); // Read the imageUri from the parcel
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(String employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(company);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(employeeCount);
        dest.writeParcelable(imageUri, flags);
    }
}
