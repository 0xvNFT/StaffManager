package com.jaysonstaff.staff.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jaysonstaff.staff.R;
import com.jaysonstaff.staff.StaffActivity;
import com.jaysonstaff.staff.StaffListActivity;
import com.jaysonstaff.staff.UpdateActivity;
import com.jaysonstaff.staff.model.MainModel;

import java.util.List;

public class MainAdapter extends BaseAdapter {

    private final Context context;
    private final List<MainModel> itemList;

    public MainAdapter(Context context, List<MainModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;//test

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.pic);
            viewHolder.textViewCompany = convertView.findViewById(R.id.textViewCompany);
            viewHolder.textViewAddress = convertView.findViewById(R.id.textViewAddress);
            viewHolder.textViewEmail = convertView.findViewById(R.id.textViewEmail);
            viewHolder.textViewPhone = convertView.findViewById(R.id.textViewPhone);
            viewHolder.textViewEmployeeCount = convertView.findViewById(R.id.textViewEmployeeCount);
            viewHolder.addButton = convertView.findViewById(R.id.add);
            viewHolder.members = convertView.findViewById(R.id.members);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MainModel mainModel = itemList.get(position);
        viewHolder.textViewCompany.setText(mainModel.getCompany());
        viewHolder.textViewAddress.setText(mainModel.getAddress());
        viewHolder.textViewEmail.setText(mainModel.getEmail());
        viewHolder.textViewPhone.setText(mainModel.getPhone());
        viewHolder.textViewEmployeeCount.setText(mainModel.getEmployeeCount());

        Uri imageUri = mainModel.getImageUri();
        if (imageUri != null) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(context)
                    .load(imageUri)
                    .circleCrop()
                    .apply(options)
                    .into(viewHolder.imageView);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.no_image);
        }

        viewHolder.addButton.setOnClickListener(v -> openStaffActivity());

        viewHolder.members.setOnClickListener(v -> openStaffListActivity(mainModel.getCompany()));

        convertView.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("company", mainModel.getCompany());
            intent.putExtra("address", mainModel.getAddress());
            intent.putExtra("email", mainModel.getEmail());
            intent.putExtra("phone", mainModel.getPhone());
            intent.putExtra("employeeCount", mainModel.getEmployeeCount());
            if (mainModel.getImageUri() != null) {
                intent.putExtra("imageUri", mainModel.getImageUri().toString());
            }
            ((Activity) context).startActivityForResult(intent, 1);
        });

        return convertView;
    }

    private void openStaffActivity() {
        Intent intent = new Intent(context, StaffActivity.class);
        ((Activity) context).startActivityForResult(intent, 1);
    }

    private void openStaffListActivity(String name) {
        Intent intent = new Intent(context, StaffListActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    private static class ViewHolder {
        ImageView members;
        ImageView imageView;
        TextView textViewCompany;
        TextView textViewAddress;
        TextView textViewEmail;
        TextView textViewPhone;
        TextView textViewEmployeeCount;
        Button addButton;
    }


}
