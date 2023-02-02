package com.example.mobil_but.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_but.R;
import com.example.mobil_but.models.MesajModel;

import java.util.List;

public class createmessageAdapter extends RecyclerView.Adapter<createmessageAdapter.MessageViewHolder> {
    List<MesajModel> mesajModelList;

    public createmessageAdapter(List<MesajModel> mesajModelList){
        this.mesajModelList=mesajModelList;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mesaj,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position){
        MesajModel mesajModel = mesajModelList.get(position);
        holder.setData(mesajModel);
    }

    @Override
    public int getItemCount(){
        return mesajModelList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView mesajadi,mesaj;
        public MessageViewHolder(View itemView){
            super(itemView);
            mesajadi=itemView.findViewById(R.id.cv_mesajadi);
            mesaj=itemView.findViewById(R.id.cv_mesaj);
        }
        public void setData(MesajModel mesajModel){
            mesajadi.setText(mesajModel.getName());
            mesaj.setText(mesajModel.getDescription());
        }
    }
}
