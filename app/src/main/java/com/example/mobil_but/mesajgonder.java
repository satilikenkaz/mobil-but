package com.example.mobil_but;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_but.adapters.groupAdapter;
import com.example.mobil_but.adapters.sendMessageAdapter;
import com.example.mobil_but.models.MesajModel;
import com.example.mobil_but.models.groupModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class mesajgonder extends Fragment {

    ArrayList<groupModel> groups;
    ArrayList<MesajModel> mesajModels;
    RecyclerView groupRv, messagesRv;
    TextView seciligrup, secilimesaj;
    Button gonderbuton;
    groupModel groupModel;
    MesajModel mesajModel;

    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_mesajgonder,container,false);
        groupRv = root.findViewById(R.id.rv_mesajgonder_seciligrup);
        groupRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        groups = new ArrayList<>();
        messagesRv = root.findViewById(R.id.rv_mesajgonder_secilimesaj);
        messagesRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mesajModels = new ArrayList<>();
        gonderbuton = root.findViewById(R.id.bt_mesajgonder_toplumesajgonder);
        mAuth = FirebaseAuth.getInstance();
        secilimesaj = root.findViewById(R.id.tv_mesajgonder_secilimesaj);
        seciligrup = root.findViewById(R.id.tv_mesajgonder_seciligrup);

        fetchGroup();
        fetchMessage();
        return root;
    }

    private void fetchGroup() {
        String userID = mAuth.getCurrentUser().getUid();
        db.collection("groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
           groups.clear();
           for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
               groupModel groupModel = new groupModel(documentSnapshot.getString("isim"),documentSnapshot.getString("açıklama"),documentSnapshot.getString("logo"),(List<String>) documentSnapshot.get("numbers"),documentSnapshot.getId());
               groups.add(groupModel);
           }
           groupRv.setAdapter(new groupAdapter(groups, position -> {
               groupModel=groups.get(position);
               seciligrup.setText("Seçili Grup: "+groupModel.getGrupadi());
           }));
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
           groupRv.setLayoutManager(linearLayoutManager);
        });
    }
    private void fetchMessage(){
        String userID = mAuth.getCurrentUser().getUid();
        db.collection("messages").get().addOnSuccessListener(queryDocumentSnapshots -> {
            mesajModels.clear();
           for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()){
               MesajModel mesajModel=new MesajModel(documentSnapshot.getString("isim"),documentSnapshot.getString("açıklama"),documentSnapshot.getId());
               mesajModels.add(mesajModel);
           }
           messagesRv.setAdapter(new sendMessageAdapter(mesajModels, position -> {
               mesajModel = mesajModels.get(position);
               secilimesaj.setText("Seçili Mesaj: "+mesajModel.getName());
           }));
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
           messagesRv.setLayoutManager(linearLayoutManager);
        });
    }
}
