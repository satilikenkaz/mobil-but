package com.example.mobil_but.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_but.R;
import com.example.mobil_but.models.RehberModel;
import com.example.mobil_but.onClickItem;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class rehberAdapter extends RecyclerView.Adapter<rehberAdapter.ContactViewHolder> {

    List<RehberModel> rehberModelList;
    onClickItem onClickItem;

    public rehberAdapter(List<RehberModel> contactModelList, onClickItem onClickItem){
        this.rehberModelList=contactModelList;
        this.onClickItem=onClickItem;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        ContactViewHolder contactViewHolder = new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rehber_kisi,parent,false), onClickItem);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position){
        RehberModel contactModel = rehberModelList.get(position);
        holder.setData(contactModel);
    }

    @Override
    public int getItemCount(){return rehberModelList.size();}

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView contactImage;
        onClickItem onClickItem;
        TextView contactName, contactNumber;
        public ContactViewHolder(View itemView, onClickItem onClickItem){
            super(itemView);
            contactImage = itemView.findViewById(R.id.item_kisi_foto);
            contactName = itemView.findViewById(R.id.item_kisiadi);
            contactNumber = itemView.findViewById(R.id.item_kisinumarasi);

            this.onClickItem = onClickItem;
            itemView.setOnClickListener(this);
        }
        public void setData(RehberModel contactModel){
            contactName.setText(contactModel.getIsim());
            contactNumber.setText(contactModel.getNumara());
            if (contactModel.getResim() != null){
                Picasso.get().load(contactModel.getResim()).into(contactImage);
            }
        }

        @Override
        public void onClick(View view){ onClickItem.onClickItem(getAdapterPosition());}
    }

}
