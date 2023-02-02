package com.example.mobil_but.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_but.R;
import com.example.mobil_but.models.groupModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class createGroupAdapter extends RecyclerView.Adapter<createGroupAdapter.CreateGroupViewHolder> {
    List<groupModel> groupModelList;
    public createGroupAdapter(List<groupModel> groupModelList){
        this.groupModelList=groupModelList;
    }
    @NonNull
    @Override
    public  CreateGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        CreateGroupViewHolder createGroupViewHolder = new CreateGroupViewHolder(View.inflate(parent.getContext(), R.layout.cardview_grupolustur, null));
        return createGroupViewHolder;
    }
    @Override
    public void onBindViewHolder(CreateGroupViewHolder holder, int position){
        groupModel groupModel = groupModelList.get(position);
        holder.setData(groupModel);
    }
    @Override
    public int getItemCount(){return groupModelList.size();}
    public class CreateGroupViewHolder extends RecyclerView.ViewHolder{
        TextView grupismi, grupaciklamasi;
        ImageView gruplogosu;
        public CreateGroupViewHolder(View itemView){
            super(itemView);
            gruplogosu=itemView.findViewById(R.id.item_gruplogo);
            grupismi=itemView.findViewById(R.id.item_grupadi);
            grupaciklamasi=itemView.findViewById(R.id.item_grupaciklamasi);
        }
        public void setData(groupModel groupModel){
            grupismi.setText(groupModel.getGrupadi());
            grupaciklamasi.setText(groupModel.getGrupaciklamasi());
            if (groupModel.getGruplogo_url() != null){
                Picasso.get().load(groupModel.getGruplogo_url()).into(gruplogosu);
            }
        }
    }
}
