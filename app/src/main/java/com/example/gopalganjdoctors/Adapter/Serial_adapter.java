package com.example.gopalganjdoctors.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopalganjdoctors.Model.Serials_response;
import com.example.gopalganjdoctors.R;

import java.util.List;

public class Serial_adapter extends RecyclerView.Adapter<Serial_adapter.ViewHolder> {
    private List<Serials_response.Serial> serialList;

    public Serial_adapter(List<Serials_response.Serial> serialList) {
        this.serialList = serialList;
    }

    @NonNull
    @Override
    public Serial_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.serial_card, parent, false);
        return new Serial_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Serial_adapter.ViewHolder holder, int position) {
        Serials_response.Serial response = serialList.get(position);

        holder.serialNoText.setText(String.valueOf(position + 1));
        holder.nameText.setText(response.patientName);
        holder.diseaseText.setText(response.disease);
        holder.ageText.setText(response.patientAge);
        holder.phoneText.setText(response.patientPhone);
    }

    @Override
    public int getItemCount() {
        return serialList.size();
    }

    private OnItemViewListener onItemViewListener;

    private OnItemClickListener onClickListener;

    public interface OnItemViewListener {
        void OnItemView(int position);
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener, OnItemViewListener onItemViewListener) {

        this.onClickListener = onItemClickListener;
        this.onItemViewListener = onItemViewListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serialNoText, nameText, diseaseText, ageText, phoneText;

        LinearLayout viewPrescriptionButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serialNoText = itemView.findViewById(R.id.serialNoTextID);
            nameText = itemView.findViewById(R.id.nameTextID);
            ageText = itemView.findViewById(R.id.ageTextID);
            diseaseText = itemView.findViewById(R.id.diseaseTextID);
            phoneText = itemView.findViewById(R.id.phoneTextID);
            viewPrescriptionButton = itemView.findViewById(R.id.viewPrescriptionButtonID);

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

            viewPrescriptionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemViewListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemViewListener.OnItemView(position);
                        }
                    }
                }
            });
        }
    }
}
