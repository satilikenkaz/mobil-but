package com.example.mobil_but.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_but.R;
import com.example.mobil_but.models.MesajModel;
import com.example.mobil_but.onClickItem;

import java.util.ArrayList;
import java.util.List;

public class sendMessageAdapter extends RecyclerView.Adapter<sendMessageAdapter.MessageViewHolder>{

    List<MesajModel> mesajModelList;
    onClickItem onClickItem;
    public sendMessageAdapter(List<MesajModel> mesajModelList, onClickItem onClickItem){
        this.mesajModelList=mesajModelList;
        this.onClickItem=onClickItem;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mesaj,parent,false),onClickItem);
    }
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position){
        MesajModel mesajModel = mesajModelList.get(position);
        holder.setData(mesajModel);
    }
    @Override
    public int getItemCount(){ return mesajModelList.size();}
    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView messageName, messageDescription;
        onClickItem onClickItem;
        public MessageViewHolder(View itemView,onClickItem onClickItem){
            super(itemView);
            messageName=itemView.findViewById(R.id.cv_mesajadi);
            messageDescription = itemView.findViewById(R.id.cv_mesaj);
            this.onClickItem=onClickItem;
            itemView.setOnClickListener(this);
        }

        public void setData(MesajModel mesajModel){
            messageName.setText(mesajModel.getName());
            messageDescription.setText(mesajModel.getDescription());
        }
        @Override
        public void onClick(View view){
            onClickItem.onClickItem(getAdapterPosition());
        }
    }
}
