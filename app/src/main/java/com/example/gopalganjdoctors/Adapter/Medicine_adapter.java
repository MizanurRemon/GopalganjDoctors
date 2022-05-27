package com.example.gopalganjdoctors.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopalganjdoctors.Model.Prescription_response;
import com.example.gopalganjdoctors.R;

import java.util.List;

public class Medicine_adapter extends RecyclerView.Adapter<Medicine_adapter.ViewHolder> {
    private List<Prescription_response.Medicine> medicineList;

    public Medicine_adapter(List<Prescription_response.Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.medicine_card, parent, false);
        return new Medicine_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prescription_response.Medicine response = medicineList.get(position);

        holder.daysText.setText(response.days + " days");
        holder.nameText.setText(response.medicine);
        holder.rulesText.setText(response.rules);

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView daysText, nameText, rulesText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            daysText = itemView.findViewById(R.id.daysTextID);
            nameText = itemView.findViewById(R.id.nameTextID);
            rulesText = itemView.findViewById(R.id.rulesTextID);
        }
    }
}
