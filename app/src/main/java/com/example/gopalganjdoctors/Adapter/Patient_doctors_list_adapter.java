package com.example.gopalganjdoctors.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopalganjdoctors.Model.Doctors_response;
import com.example.gopalganjdoctors.R;

import java.util.List;

public class Patient_doctors_list_adapter extends RecyclerView.Adapter<Patient_doctors_list_adapter.ViewHolder> {

    private List<Doctors_response.Doctor> doctorsList;

    public Patient_doctors_list_adapter(List<Doctors_response.Doctor> doctorsList) {
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public Patient_doctors_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.doctors_card, parent, false);
        return new Patient_doctors_list_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Patient_doctors_list_adapter.ViewHolder holder, int position) {
        Doctors_response.Doctor response = doctorsList.get(position);
        holder.nameText.setText(response.doctorName);
        holder.designationText.setText(response.doctorDesignation);
        holder.phoneText.setText("Phone: " + response.doctorPhone);
    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    private OnItemClickListener onClickListener;
    private OnItemCallListener onCallListener;

    public interface OnItemCallListener {
        void OnItemCall(int position);
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener onClickListener, OnItemCallListener onCallListener) {
        this.onClickListener = onClickListener;
        this.onCallListener = onCallListener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, designationText, phoneText;

        LinearLayout callButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.nameTextID);
            designationText = itemView.findViewById(R.id.designationTextID);
            phoneText = itemView.findViewById(R.id.phoneTextID);

            callButton = itemView.findViewById(R.id.callButtonID);

            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onCallListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onCallListener.OnItemCall(position);
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.OnItemClick(position);
                        }
                    }
                }
            });


        }
    }
}
