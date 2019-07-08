package com.example.delacruz.finalandroid.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.delacruz.finalandroid.R;
import com.example.delacruz.finalandroid.SideActivity;


public class InicioFragment extends Fragment {


    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;

    Bundle args = new Bundle();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);



        View contenedor = (View) container.getParent();
        appBar = (AppBarLayout)contenedor.findViewById(R.id.appbar);
        viewPager = (ViewPager)view.findViewById(R.id.pager);
        tabs = new TabLayout(getActivity());
        appBar.removeView(tabs);
        tabs.setTabTextColors(Color.parseColor("#AAAAAA"),Color.parseColor("#FFFFFF"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        appBar.removeView(tabs);
        super.onPause();
    }

    @Override
    public void onResume() {
        appBar.removeView(tabs);
        appBar.addView(tabs);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);

        super.onResume();
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter{

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Bundle ar = new Bundle();
                    ar.putString("roluser",getArguments().getString("roluser"));
                    VerTransportistaFragment verTransportistaFragment = new VerTransportistaFragment();
                    verTransportistaFragment.setArguments(ar);
                    return verTransportistaFragment;
                case 1:
                    Bundle ar1 = new Bundle();
                    ar1.putString("roluser",getArguments().getString("roluser"));
                    VerRemitentesFragment verRemitentesFragment = new VerRemitentesFragment();
                    verRemitentesFragment.setArguments(ar1);
                    return verRemitentesFragment;
                case 2:
                    Bundle ar2 = new Bundle();
                    ar2.putString("roluser",getArguments().getString("roluser"));
                    VerDestinatariosFragment verDestinatariosFragment = new VerDestinatariosFragment();
                    verDestinatariosFragment.setArguments(ar2);
                    return verDestinatariosFragment;
                case 3:
                    Bundle ar3 = new Bundle();
                    ar3.putString("roluser",getArguments().getString("roluser"));
                    VerEnviosFragment verEnviosFragment = new VerEnviosFragment();
                    verEnviosFragment.setArguments(ar3);
                    return verEnviosFragment;
            }
            return null;
        }


        @Override
        public int getCount() {
            return 4;
        }

        public CharSequence getPageTitle(int position){

            switch (position){
                case 0:
                    String tab_transportista =("Transportistas");
                    return tab_transportista;
                case 1:
                    String tab_remitente =("Remitentes");
                    return tab_remitente;
                case 2:
                    String tab_destinatario =("Destinatarios");
                    return tab_destinatario;
                case 3:
                    String tab_envio =("Envios");
                    return tab_envio;
            }
            return null;
        }

    }



}
