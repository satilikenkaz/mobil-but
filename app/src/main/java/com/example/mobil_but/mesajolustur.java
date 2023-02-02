package com.example.mobil_but;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_but.adapters.createmessageAdapter;
import com.example.mobil_but.models.MesajModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


public class mesajolustur extends Fragment {
    TextView mesaj_adi, mesaj;
    RecyclerView messageRV;
    Button create_button;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    ArrayList<MesajModel> mesajModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_mesajolustur, container, false);
        mesaj_adi = root.findViewById(R.id.et_mesajolustur_mesajadi);
        mesaj = root.findViewById(R.id.et_mesajolustur_mesaj);
        messageRV = root.findViewById(R.id.rv_mesajolustur_mesajlar);
        create_button = root.findViewById(R.id.bt_mesajolustur_mesajolustur);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        mesajModelArrayList = new ArrayList<>();

        create_button.setOnClickListener(View -> {
            String name = mesaj_adi.getText().toString();
            String description = mesaj.getText().toString();
            if (name.isEmpty() || description.isEmpty()){
                Toast.makeText(getContext(),"Alanları doldurun", Toast.LENGTH_SHORT).show();
            }
            CreateMessage(name,description);
        });
        FetchMessage();
        return root;
    }
    private void CreateMessage(String messageName, String messageDescription){
        String userId=mAuth.getCurrentUser().getUid();
        mStore.collection("/userdata/"+userId+"/messages").add(new HashMap<String, Object>(){{
            put("name",messageName);
            put("description", messageDescription);
        }}).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(),"Mesaj oluşturuldu", Toast.LENGTH_SHORT).show();
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                MesajModel mesajModel = documentSnapshot.toObject(MesajModel.class);
                mesajModelArrayList.add(mesajModel);
                messageRV.getAdapter().notifyDataSetChanged();
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(),"Mesaj oluşturulamadı", Toast.LENGTH_SHORT);
        });
    }
    private void FetchMessage(){
        String userId=mAuth.getCurrentUser().getUid();

        mStore.collection("/userdata/"+userId+"/messages").get().addOnSuccessListener(queryDocumentSnapshots -> {
            mesajModelArrayList.clear();
            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                MesajModel mesajModel = new MesajModel(documentSnapshot.getString("name"),documentSnapshot.getString("description"),documentSnapshot.getId());
                mesajModelArrayList.add(mesajModel);

                messageRV.setAdapter(new createmessageAdapter(mesajModelArrayList));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                messageRV.setLayoutManager(linearLayoutManager);
            }
        });
    }

}
