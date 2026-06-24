package com.example.d308vacationplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.ArrayList;
import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    private List<Vacation> mVacations;

    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {

        private final TextView vacationItemView;
        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);

            vacationItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Vacation current = mVacations.get(position);

                    //Part of B2
                    //Passing data to vacation details view
                    Intent intent = new Intent(context, VacationDetails.class);

                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("vacationTitle", current.getVacationTitle());
                    intent.putExtra("vacationHotel", current.getVacationHotel());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if (mVacations!= null) {
            Vacation current = mVacations.get(position);
            String vacationTitle = current.getVacationTitle();
            holder.vacationItemView.setText(vacationTitle);
        } else {
            String noVacation = "No vacation";

            holder.vacationItemView.setText(noVacation);
        }

    }

    @Override
    public int getItemCount() {
        if (mVacations != null) {
            return mVacations.size();
        }
        else return 0;

    }

    public void setVacation(List<Vacation> vacations){
        mVacations = vacations;
        notifyDataSetChanged();
    }


}
