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

public class Doctors_adapter extends RecyclerView.Adapter<Doctors_adapter.ViewHolder> {
    private List<Doctors_response.Doctor> doctorsList;

    public Doctors_adapter(List<Doctors_response.Doctor> doctorsList) {
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public Doctors_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.doctors_card, parent, false);
        return new Doctors_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Doctors_adapter.ViewHolder holder, int position) {
        Doctors_response.Doctor response = doctorsList.get(position);
        holder.nameText.setText(response.doctorName);
        holder.designationText.setText(response.doctorDesignation);
        holder.phoneText.setText("Phone: " + response.doctorPhone);
    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    private OnItemCallListener onCallListener;

    private OnItemClickListener onClickListener;

    public interface OnItemCallListener {
        void OnItemCall(int position);
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {

        this.onClickListener = onItemClickListener;

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
            callButton.setVisibility(View.GONE);

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
