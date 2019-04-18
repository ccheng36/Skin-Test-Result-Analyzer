package com.example.skintestresultanalyzer;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skintestresultanalyzer.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    MaterialEditText email, name, password;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (MaterialEditText) findViewById(R.id.email);
        password = (MaterialEditText) findViewById(R.id.password);

        signUpBtn = (Button) findViewById(R.id.SignUpBtn);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_ucsfstra = database.getReference("ucsf-stra");

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please Wait...");
                mDialog.show();

                table_ucsfstra.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(email.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Email already register", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.dismiss();
                            User user = new User(name.getText().toString(), email.getText().toString(), password.getText().toString());
                            table_ucsfstra.child(email.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
