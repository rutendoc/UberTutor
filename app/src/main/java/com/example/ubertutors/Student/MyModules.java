package com.example.ubertutors.Student;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubertutors.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import android.widget.LinearLayout.LayoutParams;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyModules extends Fragment {

    private EditText editText, etd;
    private Button button;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    private Spinner moduleSpinner;
    private String dummyModCodes[] = {"Code1", "Code2", "Code3"};
    private ArrayAdapter<String>arrayAdapter;
    private DatabaseReference mDB;

    private String edt1, edt2;
    private ArrayList<Module> moduleArrayList = new ArrayList<>();

    private LinearLayout linearLayout;

    public MyModules() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_modules, container, false);

//        editText = rootView.findViewById(R.id.et);
//        etd = rootView.findViewById(R.id.etd);
//        button = rootView.findViewById(R.id.btn);
        recyclerView = rootView.findViewById(R.id.list);
        //linearLayout = rootView.findViewById(R.id.linlay);

//        mDB = FirebaseDatabase.getInstance().getReference().child("modules");
//        moduleSpinner = rootView.findViewById(R.id.spinner_dropdown);
//        Query query = mDB.orderByChild("moduleCode");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final List<String> moduleCodeList = new ArrayList<String>();
//                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                    String modCode = dataSnapshot1.child("moduleCode").getValue(String.class);
//                    moduleCodeList.add(modCode);
//                }
//                ArrayAdapter<String> arrayAdapter = new
//                        ArrayAdapter<String>(getContext(),
//                        android.R.layout.simple_spinner_item, moduleCodeList);
//                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                moduleSpinner.setAdapter(arrayAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG)
//                        .show();
//            }
//        });



//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Here we will retrieve the modules info from db on click from spinner
//
//
//
////                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
////                        .child("modules").push();
////                Map<String, Object> map = new HashMap<>();
////                map.put("moduleName", editText.getText().toString());
////                map.put("moduleCode", etd.getText().toString());
////
////                databaseReference.setValue(map);
//                CreateNewModuleCard();
//
//            }
//        });
//
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();

        return rootView;
    }


    private CardView cardView;
    private LayoutParams layoutParams;
    private TextView tvModuleCode;
    private TextView tvModuleName;
    private TextView textview;


    private  void CreateNewModuleCard(){
        cardView = new CardView(getContext());
        layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(10);
        cardView.setPadding(25, 25, 25, 25);
        cardView.setCardBackgroundColor(Color.MAGENTA);
        cardView.setMaxCardElevation(30);
        cardView.setMaxCardElevation(6);

        //tvModuleName = new TextView(getContext());

        textview = new TextView(getContext());

        textview.setLayoutParams(layoutParams);

        textview.setText("CardView Programmatically");

        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);

        textview.setTextColor(Color.WHITE);

        textview.setPadding(25,25,25,25);

        textview.setGravity(Gravity.CENTER);

        cardView.addView(textview);
        linearLayout.addView(cardView);


    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("modules");

        FirebaseRecyclerOptions<Module> options =
                new FirebaseRecyclerOptions.Builder<Module>()
                        .setQuery(query, new SnapshotParser<Module>() {
                            @NonNull
                            @Override
                            public Module parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Module(snapshot.child("moduleName").getValue().toString(),
                                        snapshot.child("moduleCode").getValue().toString(),
                                        snapshot.child("status").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Module, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.module_list_item, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, Module module) {
                holder.setTxtName(module.getModuleName());
                holder.setTxtCode(module.getModuleCode());
                holder.setTxtStatus(module.getStatus());

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Creating the instance of PopupMenu
                        PopupMenu popupMenu = new PopupMenu(getContext(),view);
                        //Inflating the Popup using xml file
                        popupMenu.getMenuInflater().inflate(R.menu.module_popup_menu, popupMenu.getMenu());
                        //registering popup with OnMenuItemClickListener

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(getContext(),"Your request has been sent to the tutor", Toast.LENGTH_LONG).show();
                                return true;
                            }
                        });

                        popupMenu.show();
                    }
                });
            }


        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null)
            adapter.stopListening();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView txtName;
        public TextView txtCode;
        public TextView txtStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtName = itemView.findViewById(R.id.list_name);
            txtCode = itemView.findViewById(R.id.list_code);
            txtStatus = itemView.findViewById(R.id.list_status);
        }

        public void setTxtName(String string) {
            txtName.setText(string);
        }

        public void setTxtCode(String string) {
            txtCode.setText(string);
        }

        public void setTxtStatus(String string) {txtStatus.setText(string);}
    }
}
