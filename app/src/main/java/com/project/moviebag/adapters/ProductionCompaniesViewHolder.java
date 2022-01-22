package com.project.moviebag.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.moviebag.R;

public class ProductionCompaniesViewHolder extends RecyclerView.ViewHolder {
    ImageView company_logo;
    TextView company_name;

    public ProductionCompaniesViewHolder(@NonNull View itemView) {
        super(itemView);
        company_logo = itemView.findViewById(R.id.company_logo);
        company_name = itemView.findViewById(R.id.alternate_text_company);

    }
}
