package com.example.gaff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RenterProfileEditFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LandLordProfileEditFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button register, logout;
    private EditText email, password, confirmPassword, name, contactNum;
    private Map<String, String> currentDataOnDb = new HashMap<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LandLordProfileEditFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RenterProfileEditFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static LandLordProfileEditFrag newInstance(String param1, String param2) {
        LandLordProfileEditFrag fragment = new LandLordProfileEditFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_land_lord_profile_edit, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        register = (Button) rootView.findViewById(R.id.register_btn);
        email = (EditText) rootView.findViewById(R.id.emailTextEdit);
        password = (EditText) rootView.findViewById(R.id.passwordTextEdit);
        confirmPassword  = (EditText) rootView.findViewById(R.id.passwordTextEditConfirm);
        name = (EditText) rootView.findViewById(R.id.nameTextEdit);
        contactNum = (EditText) rootView.findViewById(R.id.contactNumberTextEdit);

        currentDataOnDb = getCurrentDataFromDB();
        if(register == null){
            Log.e("Register is ", "register is null");
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText() == null || password.getText() == null || confirmPassword.getText() == null ||
                        email.getText().toString().length() == 0 || password.getText().toString().length() == 0
                        || confirmPassword.getText().toString().length() == 0 || name.getText() == null ||
                        contactNum.getText() == null || name.getText().toString().length() == 0 ||
                        contactNum.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Enter an email, password and confirm the password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    tryRegister(email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString(),
                            name.getText().toString(), contactNum.getText().toString());
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle SavedInstanceState){ //Retrospective Method added - Once the view is created - method is called
        super.onViewCreated(view, SavedInstanceState);

        logout = (Button) view.findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // We imported the navigation package
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
    }

    private HashMap<String, String> getCurrentDataFromDB(){
        HashMap<String, String> returnDataOnDb = new HashMap<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userEmail = user.getEmail().toString();
        String user_id = mAuth.getCurrentUser().getUid();
        db.collection("LandLordsDetails")
                .document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        returnDataOnDb.put("name", document.get("name").toString());
                        returnDataOnDb.put("contactNum", document.get("contactNum").toString());
                        returnDataOnDb.put("email", userEmail);
                        currentDataOnDb.put("name", document.get("name").toString());
                        currentDataOnDb.put("contactNum", document.get("contactNum").toString());
                        currentDataOnDb.put("email", userEmail);
                        Log.e("user name", document.get("name").toString());
                        email.setText(userEmail);
                        name.setText(document.get("name").toString());
                        contactNum.setText(document.get("contactNum").toString());
                    }
                }
            }
        });
        return  returnDataOnDb;

    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean isValidPassword(String s) {
        if (s == null || s.length() < 8) {
            return false;
        }
        return true;
    }

    public boolean isValidContactNumber(String num) {
        return Patterns.PHONE.matcher(num).matches();
    }

    public boolean isValidName(String name) {
        if(!name.matches("[a-zA-Z ]+"))
        {
            return false;
        }
        return true;
    }

    public boolean validate(String email, String password, String confirm,
                            String name, String contactNum) {
        if (!isValidEmail(email)) {
            Toast.makeText(getActivity(), "Invalid email",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPassword(password)) {
            Toast.makeText(getActivity(), "Invalid password, must be at least 8 characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }else if (!password.equals(confirm)){
            Toast.makeText(getActivity(), "Passwords do not match",
                    Toast.LENGTH_LONG).show();
            return  false;
        }else if (!isValidName(name)) {
            Toast.makeText(getActivity(), "Invalid Name, must be alphabetical characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }else if (!isValidContactNumber(contactNum)) {
            Toast.makeText(getActivity(), "Invalid contact number",
                    Toast.LENGTH_LONG).show();
            return false;
        } else{
            return true;
        }
    }

    public void openLandLordHome() {
        Intent intent = new Intent(getActivity(), LandLordHome.class);
        startActivity(intent);
    }

    private void updateEmail(String email) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Email Changed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updatePassword(String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateNameOrContactNum(String dataType, String data){
        String user_id = mAuth.getCurrentUser().getUid();
        db.collection("LandLordsDetails").document(user_id)
                .update(dataType, data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), dataType + "Changed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Firestore Error: "
                            + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void tryRegister(String email, String password, String confirm,
                             String name, String contactNum) {
        if (!validate(email, password, confirm, name, contactNum)) {
            return;
        }else {
            if(currentDataOnDb.get("name").equals(name)){
                Toast.makeText(getActivity(), "Name still same",
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getActivity(), "Not same",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
