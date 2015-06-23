package com.example.robercick.christianunion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robercick.christianunion.tabs.SlidingTabLayout;


public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    ViewPagerAdapter adapter;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToogle;
    private ViewPager mPager;
    private NavigationView mDrawer;
    private SlidingTabLayout tabs;
    int Numboftabs =5;
    private DrawerLayout mDrawerLayout;
    CharSequence Titles[]={"Articles","Sermons","Bible Plan","Events","Resources"};

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer=(NavigationView) findViewById(R.id.main_drawer);

        mDrawerToogle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToogle);
        mDrawerToogle.syncState();
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }

        });

        tabs.setViewPager(pager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Toast.makeText(this,"Hey you just hit"+item.getTitle(),Toast.LENGTH_SHORT).show();
            return true;
        }

        if(id== R.id.navigate){
            startActivity(new Intent(this,ResourceActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToogle.onConfigurationChanged(newConfig);
    }

    class MyPagerAdapter extends FragmentPagerAdapter{
        int[] icons={R.drawable.nav_home,R.drawable.nav_info,R.drawable.top_media};
        String[] tabsText;
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabsText=getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                ArticlesFragment articles=new ArticlesFragment();
                return articles;
            }else if(position==1){
                SermonsFragment sermons=new SermonsFragment();
                return sermons;
            }else if(position==2){
                BibleFragment bible=new BibleFragment();
                return bible;
            }else{
                ResourcesFragment resources=new ResourcesFragment();
                return resources;
            }

        }

        public int getPageIconResId(int position){
            return icons[position];
        }

       /* @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable=getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan=new ImageSpan(drawable);
            SpannableString spannableString=new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }*/
       @Override
       public CharSequence getPageTitle(int position) {
          return tabsText[position];
       }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public static class MyFragment extends Fragment{
        private TextView textView;

        public static MyFragment getInstance(int position) {
            MyFragment myFragment=new MyFragment();
            Bundle args=new Bundle();
            args.putInt("position",position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout=inflater.inflate(R.layout.fragment_my,container,false);
            textView=(TextView) layout.findViewById(R.id.position);
            Bundle bundle=getArguments();
            if(bundle!=null){
                textView.setText("The page currently selected is "+bundle.getInt("position"));
            }
            return layout;
        }
    }
}
