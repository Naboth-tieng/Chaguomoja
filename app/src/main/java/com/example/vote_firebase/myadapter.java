package com.example.vote_firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {
    Context context;
    ArrayList<candidate> list;
    int lastcheckedpostion = -1;
    boolean isnewradiobuttonchecked = false;
    String select;

    public myadapter(Context context, ArrayList<candidate> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemp,parent,false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        candidate candidate=list.get(position);
        holder.name.setText(candidate.getname());
        holder.deputy.setText(candidate.getDeputy());
        holder.party.setText(candidate.getParty());
        Picasso.get().load(candidate.getimage()).resize(325,325).centerCrop().into(holder.image);
        if (isnewradiobuttonchecked){
            holder.radioButton.setChecked(candidate.isSelected());
        }else{
            if (holder.getAdapterPosition()==0){
                holder.radioButton.setChecked(true);
                lastcheckedpostion = 0;
            }
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView name,party,deputy;
        RadioButton radioButton;
        ImageView image;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cname);
            party=itemView.findViewById(R.id.party);
            deputy=itemView.findViewById(R.id.deputy);
            image=itemView.findViewById(R.id.cimage);
            radioButton=itemView.findViewById(R.id.radio_button);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleRadiobuttonChecks(getAdapterPosition());
                }
            });
        }
    }
    private void handleRadiobuttonChecks(int adapterPosition) {
    isnewradiobuttonchecked = true;
    list.get(lastcheckedpostion).setSelected(false);
    list.get(adapterPosition).setSelected(true);
    lastcheckedpostion = adapterPosition;
    select = list.get(adapterPosition).getname();
    Toast.makeText(context,select,Toast.LENGTH_SHORT).show();
    notifyDataSetChanged();
    }
}
