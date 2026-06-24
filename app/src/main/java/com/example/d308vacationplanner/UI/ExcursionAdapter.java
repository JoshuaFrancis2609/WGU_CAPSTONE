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
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private List<Excursion> mExcursions;

    private final Context context;
    private final LayoutInflater mInflater;

    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        //adding both TextViews
        private final TextView excursionItemView1;
        private final TextView excursionItemView2;

        private ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);

            excursionItemView1 = itemView.findViewById(R.id.textView3);
            excursionItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Excursion excursion = mExcursions.get(position);

                    //Part of B3 and B4
                    //Passing data of excursion detail view
                    Intent intent = new Intent(context, ExcursionDetails.class);

                    intent.putExtra("excursionId", excursion.getExcursionId());
                    intent.putExtra("excursionTitle", excursion.getExcursionTitle());
                    intent.putExtra("excursionDate", excursion.getExcursionDate());
                    intent.putExtra("vacationId", excursion.getVacationId());

                    context.startActivity(intent);

                }
            });
        }

    }

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if (mExcursions!= null) {
            Excursion current = mExcursions.get(position);
            holder.excursionItemView1.setText(current.getExcursionTitle());
            holder.excursionItemView2.setText(current.getExcursionDate());
        } else {
            String noExcursion = "No excursion";
            String noDate = "No excursion date";

            holder.excursionItemView1.setText(noExcursion);
            holder.excursionItemView2.setText(noDate);
        }

    }

    @Override
    public int getItemCount() {
        if (mExcursions != null) {
            return mExcursions.size();
        }
        else return 0;

    }

    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }

}
