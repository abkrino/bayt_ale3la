package abkrino.eg.baytaleaala;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextClock;

import abkrino.eg.baytaleaala.R;

import abkrino.eg.baytaleaala.pojo.DataBaseHelper;
import abkrino.eg.baytaleaala.ui.byteel3ela.ByteEl3elaFragment;
import abkrino.eg.baytaleaala.ui.danielHome.DanielHomeFragment;
import abkrino.eg.baytaleaala.ui.demyamaHome.DemyamaHomeFragment;
import abkrino.eg.baytaleaala.ui.home.HomeFragment;
import abkrino.eg.baytaleaala.ui.internet.IOnBackPressed;
import abkrino.eg.baytaleaala.ui.internet.InternetFragment;
import abkrino.eg.baytaleaala.ui.internetBrowser.InternetBrowserFragment;
import abkrino.eg.baytaleaala.ui.internetBrowser.InternetBrowserViewModel;
import abkrino.eg.baytaleaala.ui.notifications.Notifications;
import abkrino.eg.baytaleaala.ui.ourSite.OurSiteFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static abkrino.eg.baytaleaala.ui.notifications.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {
    SNavigationDrawer sNavigationDrawer;
    private static final String TAG = "myDataBaseResult";
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("pageNotificaions");
    DataBaseHelper myDb;
    int color1=0;
    TextClock textClock;
    String title = "title";
    String body = "body";
    String time = "time";
    String resultString = "off";
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("EEEE , dd-MMM-yyyy , hh:mm:ss  a");
    private AppBarConfiguration mAppBarConfiguration;
    private NotificationManagerCompat notificationManager;
    Class fragmentClass;
    public static Fragment fragment;
//    @Override
//    protected void onDestroy() {
//        startService();
//        super.onDestroy();
//    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title = snapshot.child("title").getValue().toString();
                body = snapshot.child("body").getValue().toString();
                resultString = snapshot.child("switch").getValue().toString();
//                startService();
                insertOnDataBase();

                //notificationsــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ
                switch (resultString) {
                    case "off":
                        break;
                    case "on":
                        notificationManager = NotificationManagerCompat.from(MainActivity.this);
                        Intent intent = new Intent(MainActivity.this, Notifications.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                        @SuppressLint("WrongConstant") Notification notification = new Notification.Builder(MainActivity.this, CHANNEL_1_ID)
                                .setSmallIcon(R.drawable.icone)
                                .setContentTitle(title)
                                .setContentIntent(contentIntent)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setStyle(new Notification.BigTextStyle().bigText(body))
                                .setContentText(body).build();
                        notificationManager.notify(1, notification);
                        resultString = "off";
//                        stopService();
                        //notificationsــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــــ
                        break;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();

        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Notifications.class);
                startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Home",R.drawable.home));
        menuItems.add(new MenuItem("Internet",R.drawable.internet));
        menuItems.add(new MenuItem("Our site",R.drawable.oursite));
        menuItems.add(new MenuItem("Daniel Home",R.drawable.homed));
        menuItems.add(new MenuItem("Demyana Home",R.drawable.homem));
        menuItems.add(new MenuItem("Byte el3ela",R.drawable.homeb));
        menuItems.add(new MenuItem("Solve&Competition",R.drawable.internet));
        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }
        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        color1 = R.color.Red;
                        fragmentClass = HomeFragment.class;
                        break;
                    }
                    case 1:{
                        color1 = R.color.Orange;
                        fragmentClass = InternetFragment.class;
                        break;
                    }
                    case 2:{
                        color1 = R.color.Green;
                        fragmentClass = OurSiteFragment.class;
                        break;
                    }
                    case 3:{
                        color1 = R.color.Blue;
                        fragmentClass = DanielHomeFragment.class;
                        break;
                    }
                    case 4:{
                        color1 = R.color.Pink;
                        fragmentClass = DemyamaHomeFragment.class;
                        break;
                    }
                    case 5:{
                        color1 = R.color.Purple;
                        fragmentClass = ByteEl3elaFragment.class;
                        break;
                    }
                    case 6:{
                        color1 = R.color.LemonChiffon;
                        fragmentClass = InternetBrowserFragment.class;
                        break;
                    }
                }
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

//    @Override
//    public void onBackPressed() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
//            super.onBackPressed();
//        }
//    }

    public void insertOnDataBase() {
        switch (resultString) {
            case "off":
                break;
            case "on":
                myDb = new DataBaseHelper(getApplicationContext());
                time = format.format(calendar.getTime());
//        currentTime.getHours();
                Log.d(TAG, "Message Notification Body: " + time);
                boolean result = myDb.insertData(title, body, time);
                if (result) {
                    Log.d(TAG, " result : true");
                } else {
                    Log.d(TAG, " result : false");
                    break;
                }


        }
    }
}