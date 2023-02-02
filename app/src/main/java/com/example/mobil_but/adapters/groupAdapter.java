package com.example.mobil_but.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_but.R;
import com.example.mobil_but.databinding.FragmentGrupolusturBinding;
import com.example.mobil_but.models.groupModel;
import com.example.mobil_but.onClickItem;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class groupAdapter extends RecyclerView.Adapter<groupAdapter.GroupViewHolder> {
    ArrayList<groupModel> groupModelArrayList;
    com.example.mobil_but.onClickItem onClickItem;

    public groupAdapter(ArrayList<groupModel> groupModelArrayList, onClickItem onClickItem){
        this.groupModelArrayList = groupModelArrayList;
        this.onClickItem = onClickItem;
    }



    @NonNull
    @Override
    public groupAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupViewHolder groupViewHolder = new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rehber_kisi, parent, false), onClickItem);
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull groupAdapter.GroupViewHolder holder, int position){
        groupModel groupModel = groupModelArrayList.get(position);
        holder.setData(groupModel);
    }

    @Override
    public int getItemCount() {return groupModelArrayList.size();}

    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView grupimage;
        TextView grupİsmi, grupAciklamasi;
        onClickItem onClickItem;
        public GroupViewHolder(View itemview, onClickItem onClickItem){
            super(itemview);
            grupimage = itemview.findViewById(R.id.item_gruplogo);
            grupİsmi = itemview.findViewById(R.id.item_grupadi);
            grupAciklamasi = itemview.findViewById(R.id.item_grupaciklamasi);

            this.onClickItem = onClickItem;
            itemview.setOnClickListener(this);
        }
        public void setData(groupModel groupModel){
            grupİsmi.setText(groupModel.getGrupadi());
            grupAciklamasi.setText(groupModel.getGrupaciklamasi());
            if (groupModel.getGruplogo_url() != null){
                Picasso.get().load(groupModel.getGruplogo_url()).into(grupimage);
            }
        }

        @Override
        public void onClick(View view){onClickItem.onClickItem(getAdapterPosition());}
    }
}
