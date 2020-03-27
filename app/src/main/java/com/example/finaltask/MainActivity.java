package com.example.finaltask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragments;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private String[] titles = {"主页","搜索","发布","我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        tabLayout = findViewById(R.id.tablayout);
        fragments = new ArrayList<Fragment>();
        fragments.add(new MainFragment());
        fragments.add(new SearchFragment());
        fragments.add(new PublicFragment());
        fragments.add(new HomeFragment());
        viewPager = (NoScrollViewPage) findViewById(R.id.viewpager);
        adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        onTabItemSelected();
    }

    private void onTabItemSelected(){
        for (int i = 0;i < tabLayout.getTabCount();i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable drawable = null;
            switch (i){
                case 0:
                    drawable = getResources().getDrawable(R.mipmap.ic_main);
                    break;
                case 1:
                    drawable = getResources().getDrawable(R.mipmap.ic_search);
                    break;
                case 2:
                    drawable = getResources().getDrawable(R.mipmap.ic_public);
                    break;
                case 3:
                    drawable = getResources().getDrawable(R.mipmap.ic_home);
                    break;
            }
            tab.setIcon(drawable);
        }
    }

    private class TitleFragmentPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> mFragmentList = null;
        private String[] titles;

        public TitleFragmentPagerAdapter(FragmentManager mFragmentManager,List<Fragment> fragmentList){
            super(mFragmentManager);
            mFragmentList = fragmentList;
        }

        public TitleFragmentPagerAdapter(FragmentManager mFragmentManager,List<Fragment> fragmentList,String[] titles){
            super(mFragmentManager);
            mFragmentList = fragmentList;
            this.titles = titles;
        }

        public int getCount(){
            return mFragmentList.size();
        }

        public Fragment getItem(int position){
            Fragment fragment = null;
            if (position < mFragmentList.size()){
                fragment = mFragmentList.get(position);
            }
            else {
                fragment = mFragmentList.get(0);
            }
            return fragment;
        }

        public CharSequence getPageTitle(int position){
            if (titles != null && titles.length > 0){
                return titles[position];
            }
            return null;
        }
    }
}
