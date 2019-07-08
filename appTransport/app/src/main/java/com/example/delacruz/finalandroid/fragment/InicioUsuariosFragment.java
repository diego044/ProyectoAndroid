package com.example.delacruz.finalandroid.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.delacruz.finalandroid.R;


public class InicioUsuariosFragment extends Fragment {


    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;



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
                    VerUsuarioFragment verUsuarioFragment = new VerUsuarioFragment();
                    return verUsuarioFragment;
                case 1:
                    RegUsuarioFragment regUsuarioFragment = new RegUsuarioFragment();
                    return regUsuarioFragment;
            }
            return null;
        }


        @Override
        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle(int position){

            switch (position){
                case 0:
                    String tab_users =("Usuarios");
                    return tab_users;
                case 1:
                    String tab_add_user =("Agregar usuario");
                    return tab_add_user;
            }
            return null;
        }

    }



}
