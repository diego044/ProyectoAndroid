package com.example.delacruz.finalandroid;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.delacruz.finalandroid.fragment.InicioFragment;
import com.example.delacruz.finalandroid.fragment.InicioUsuariosFragment;
import com.example.delacruz.finalandroid.model.Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class SideActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fragmentManager = getSupportFragmentManager();
    String email;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String rolUser="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_side);
       // progressDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        email = getIntent().getStringExtra("email");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.emailside);
        final TextView navRolname = (TextView) headerView.findViewById(R.id.titleside);

        navUsername.setText(email);
        navigationView.setNavigationItemSelectedListener(this);
        initFirebase();

        final Menu nav_Menu = navigationView.getMenu();


        Query q = databaseReference.child("Usuario");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Usuario u = dataSnapshot1.getValue(Usuario.class);
                    String rol = "admin";
                    if (email.equals(u.getCorreo())&& rol.equals(u.getRol())){
                        nav_Menu.findItem(R.id.nav_users).setVisible(true);
                        rolUser = "admin";
                        break;
                    }
                    nav_Menu.findItem(R.id.nav_users).setVisible(false);
                    rolUser="usuario";


                }
                navRolname.setText(rolUser.substring(0, 1).toUpperCase() + rolUser.substring(1));
                InicioFragment inicioFragment = new InicioFragment();
                Bundle a = new Bundle();
                a.putString("roluser",rolUser);
                inicioFragment.setArguments(a);
                fragmentManager.beginTransaction().replace(R.id.contenedor,inicioFragment).commit();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onResume() {
        InicioFragment inicioFragment = new InicioFragment();
        Bundle a = new Bundle();
        a.putString("roluser",rolUser);
        inicioFragment.setArguments(a);
        fragmentManager.beginTransaction().replace(R.id.contenedor,inicioFragment).commitNow();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
                InicioFragment inicioFragment = new InicioFragment();
                Bundle a = new Bundle();
                a.putString("roluser",rolUser);
                inicioFragment.setArguments(a);
           fragmentManager.beginTransaction().replace(R.id.contenedor,inicioFragment).commit();
        }  else if (id == R.id.nav_users) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new InicioUsuariosFragment()).commit();
        }
        else if (id == R.id.nav_logout) {

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
       /* progressDialog.dismiss();*/
        return true;
    }

    public void initFirebase() {
        Context context = getApplicationContext();
        FirebaseApp.initializeApp(context);
        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

}
