//package com.example.ubertutors.Student;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.ubertutors.R;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.firebase.ui.database.SnapshotParser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class DashboardStudent extends AppCompatActivity {
//    private EditText editText, etd;
//    private Button button;
//    private RecyclerView recyclerView;
//    private LinearLayoutManager linearLayoutManager;
//    private FirebaseRecyclerAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_my_mods);
//
////        editText = findViewById(R.id.et);
////        etd = findViewById(R.id.etd);
////        button = findViewById(R.id.btn);
//        recyclerView = findViewById(R.id.list);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().
//                        child("modules").push();
//                Map<String, Object> map = new HashMap<>();
//                map.put("moduleName", editText.getText().toString());
//                map.put("moduleCode", etd.getText().toString());
//
//                databaseReference.setValue(map);
//
//            }
//        });
//
//        linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
//        fetch();
//    }
//
//    private void fetch() {
//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("modules");
//
//        FirebaseRecyclerOptions<Module> options =
//                new FirebaseRecyclerOptions.Builder<Module>()
//                        .setQuery(query, new SnapshotParser<Module>() {
//                            @NonNull
//                            @Override
//                            public Module parseSnapshot(@NonNull DataSnapshot snapshot) {
//                                return new Module(
//                                        snapshot.child("moduleName").getValue().toString(),
//                                        snapshot.child("moduleCode").getValue().toString());
//                            }
//                        })
//                        .build();
//
//        adapter = new FirebaseRecyclerAdapter<Module, ViewHolder>(options) {
//            @Override
//            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.module_list_item, parent, false);
//
//                return new ViewHolder(view);
//            }
//
//
//            @Override
//            protected void onBindViewHolder(ViewHolder holder, final int position, Module module) {
//                holder.setTxtModuleName(module.getModuleName());
//                holder.setTxtModuleCode(module.getModuleCode());
//
//                holder.root.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(DashboardStudent.this, String.valueOf(position),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//        };
//        recyclerView.setAdapter(adapter);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public LinearLayout root;
//        public TextView txtName;
//        public TextView txtCode;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            root = itemView.findViewById(R.id.list_root);
//            txtName = itemView.findViewById(R.id.list_name);
//            txtCode = itemView.findViewById(R.id.list_code);
//        }
//
//        public void setTxtModuleName(String string) {
//            txtName.setText(string);
//        }
//
//
//        public void setTxtModuleCode(String string) {
//            txtCode.setText(string);
//        }
//    }
//}
