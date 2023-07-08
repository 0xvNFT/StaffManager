package com.jaysonstaff.staff.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jaysonstaff.staff.R;
import com.jaysonstaff.staff.model.StaffModel;

import java.util.List;

public class StaffListAdapter extends BaseAdapter {

    private final Context context;
    private final List<StaffModel> staffList;

    public StaffListAdapter(Context context, List<StaffModel> staffList) {
        this.context = context;
        this.staffList = staffList;
    }

    @Override
    public int getCount() {
        return staffList.size();
    }

    @Override
    public Object getItem(int position) {
        return staffList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_staff, parent, false);


            viewHolder = new ViewHolder();
            viewHolder.staffImageView = convertView.findViewById(R.id.pic_staff_item);
            viewHolder.nameTextView = convertView.findViewById(R.id.textViewNameStaff);
            viewHolder.emailTextView = convertView.findViewById(R.id.textViewEmailStaff);
            viewHolder.phoneTextView = convertView.findViewById(R.id.textViewPhoneStaff);
            viewHolder.positionTextView = convertView.findViewById(R.id.textViewPositionStaff);
            viewHolder.partTextView = convertView.findViewById(R.id.textViewPartStaff);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StaffModel staffModel = staffList.get(position);
        if (staffModel.getStaffImageUri() != null) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(context)
                    .load(staffModel.getStaffImageUri())
                    .circleCrop()
                    .apply(options)
                    .into(viewHolder.staffImageView);
        } else {
            viewHolder.staffImageView.setImageResource(R.drawable.no_image);
        }

        viewHolder.nameTextView.setText(staffModel.getName());
        viewHolder.emailTextView.setText(staffModel.getEmail());
        viewHolder.phoneTextView.setText(staffModel.getPhone());
        viewHolder.positionTextView.setText(staffModel.getPosition());
        viewHolder.partTextView.setText(staffModel.getPart());

        return convertView;
    }

    private static class ViewHolder {
        ImageView staffImageView;
        TextView nameTextView;
        TextView emailTextView;
        TextView phoneTextView;
        TextView positionTextView;
        TextView partTextView;
    }
}
