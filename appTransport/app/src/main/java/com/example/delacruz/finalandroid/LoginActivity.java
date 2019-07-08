package com.example.delacruz.finalandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnentrar;
    private EditText txtpassword;
    private EditText txtusuario;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        txtusuario = (EditText) findViewById(R.id.txtusuario);
        txtusuario.setText("admin@example.com");

        txtpassword = (EditText) findViewById(R.id.txtpassword);
        btnentrar = (Button) findViewById(R.id.btnentrar);
        progressDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        btnentrar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        loguearUsuario();
    }

    private void loguearUsuario() {
        final String email = txtusuario.getText().toString().trim();
        String password = txtpassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Iniciando sesion");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            int pos = email.indexOf("@");
                            String user = email.substring(0, pos);
                            Toast.makeText(LoginActivity.this, "Usuario: " + txtusuario.getText(), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplication(), SideActivity.class);
                            intent.putExtra("email",email);
                            startActivity(intent);

                        } else {
                            Log.w("signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onBackPressed() {

    }
}
