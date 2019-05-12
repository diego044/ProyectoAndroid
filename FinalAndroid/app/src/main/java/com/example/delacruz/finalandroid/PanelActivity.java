package com.example.delacruz.finalandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PanelActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String user="names";
    TextView txtuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        txtuser =(TextView)findViewById(R.id.txtuser);
        String user = getIntent().getStringExtra("names");
        txtuser.setText("¡Bienvenido "+ user +"!");

    }

    @Override
    public void onClick(View v) {
        ListaPaquetes();
    }

    private void ListaPaquetes() {

        Intent intencion = new Intent(getApplication(), RecyclerActivity.class);
        startActivity(intencion);

    }
}
