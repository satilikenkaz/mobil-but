package com.example.mobil_but;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil_but.adapters.groupAdapter;
import com.example.mobil_but.models.groupModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class creategroup extends Fragment {

    TextView groupName, groupDescription;
    Button createGroup;
    RecyclerView groupRV;
    ImageView groupImage;

    Uri filePath;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    FirebaseStorage mStorage;

    ArrayList<groupModel> groupModelArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_grupolustur, container,false);
        groupRV = root.findViewById(R.id.rv_grupolustur_gruplar);
        groupName = root.findViewById(R.id.et_grupolustur_grupadi);
        groupDescription = root.findViewById(R.id.et_grupolustur_grupaciklamasi);
        createGroup = root.findViewById(R.id.bt_grupolustur_grupolustur);
        groupImage = root.findViewById(R.id.ib_grupolustur_logo);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();

        groupModelArrayList = new ArrayList<>();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
            if (result.getResultCode()==getActivity().RESULT_OK){
                Intent data = result.getData();
                filePath = data.getData();
                groupImage.setImageURI(filePath);
            }
        });
        groupImage.setOnClickListener(view ->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });
        createGroup.setOnClickListener(view ->{
            String name = groupName.getText().toString();
            String description = groupDescription.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(getContext(),"Grup ismi boş bırakılamaz.", Toast.LENGTH_SHORT);
            }
            if (description.isEmpty()){
                Toast.makeText(getContext(),"Grup açıklaması boş bırakılamaz.", Toast.LENGTH_SHORT);
            }
            if (filePath!=null){
                StorageReference storageReference = mStorage.getReference().child("/resimler"+ UUID.randomUUID().toString());
                storageReference.putFile(filePath).addOnSuccessListener(uri ->{
                    String downloadUrl = uri.toString();
                    Toast.makeText(getContext(),"Resim yüklendi",Toast.LENGTH_SHORT);
                    CreateGroup(name,description,downloadUrl);
                });
                return;
            }
            Toast.makeText(getContext(),"Grup başarıyla oluşturuldu.",Toast.LENGTH_SHORT);
        });
        FetchGroup();
        return root;
    }

    private void FetchGroup() {
        String userId = mAuth.getCurrentUser().getUid();

        mStore.collection("/userdata"+userId+"/groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            groupModelArrayList.clear();
            for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()){
                groupModel groupModel = new groupModel(documentSnapshot.getString("name"),documentSnapshot.getString("description"),documentSnapshot.getString("image"),(List<String>) documentSnapshot.get("numbers"),documentSnapshot.getId());
                groupModelArrayList.add(groupModel);
            }
            groupRV.setAdapter(new groupAdapter(groupModelArrayList, position -> {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                groupRV.setLayoutManager(linearLayoutManager);
            }));
        });
    }

    private void CreateGroup(String name, String description, String downloadUrl) {

        String userId = mAuth.getCurrentUser().getUid();
        mStore.collection("/userdata/"+userId+"/groups").add(new HashMap<String, Object>(){{
            put("name",name);
            put("description", description);
            put("image", downloadUrl);
            put("numbers", new ArrayList<String>());
        }}).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(),"Grup oluşturuldu.",Toast.LENGTH_SHORT).show();

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                groupModel groupModel = documentSnapshot.toObject(com.example.mobil_but.models.groupModel.class);
                groupModelArrayList.add(groupModel);
                groupRV.getAdapter().notifyDataSetChanged();
            });
        }).addOnFailureListener(e ->{
            Toast.makeText(getContext(),"Tekrar deneyin.",Toast.LENGTH_SHORT).show();
        });
    }
}
