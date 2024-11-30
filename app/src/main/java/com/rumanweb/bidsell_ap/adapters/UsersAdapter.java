package com.rumanweb.bidsell_ap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumanweb.bidsell_ap.R;
import com.rumanweb.bidsell_ap.models.Users;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private List<Users> userList;

    public UsersAdapter(List<Users> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_users, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Users user = userList.get(position);

        holder.textFullName.setText(user.getFullName());
        holder.textUserName.setText(user.getUserName());
        holder.textEmail.setText(user.getEmail());
        holder.textPhoneNumber.setText(user.getPhoneNumber());

        if (user.getAdditionalInfo() != null && !user.getAdditionalInfo().isEmpty()) {
            holder.textAdditionalInfo.setText(user.getAdditionalInfo());
            holder.textAdditionalInfo.setVisibility(View.VISIBLE);
        } else {
            holder.textAdditionalInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textFullName, textUserName, textEmail, textPhoneNumber, textAdditionalInfo;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textFullName = itemView.findViewById(R.id.textFullName);
            textUserName = itemView.findViewById(R.id.textUserName);
            textEmail = itemView.findViewById(R.id.textEmail);
            textPhoneNumber = itemView.findViewById(R.id.textPhoneNumber);
            textAdditionalInfo = itemView.findViewById(R.id.textAdditionalInfo);
        }
    }
}