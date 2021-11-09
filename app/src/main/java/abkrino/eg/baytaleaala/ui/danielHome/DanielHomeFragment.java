package abkrino.eg.baytaleaala.ui.danielHome;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import abkrino.eg.baytaleaala.R;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;

// todo use glide libary on adapter to show folder image from firebase
//https://www.youtube.com/watch?v=iEcokZOv5UY
public class DanielHomeFragment extends Fragment {
    private DanielHomeViewModel danielHomeViewModel;
    private List<The_Slide_Items_Model_Class> listItem;
    private ViewPager page;
    private TabLayout tabLayout;
    TextView textView;
    The_Slide_items_Pager_Adapter itemsPager_adapter;
    private final DatabaseReference mDatabasephoto = FirebaseDatabase.getInstance().getReference("pageDanielHome/images");
    Thread thread;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_danielhome, container, false);
        calling(root);
        handling();
        return root;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (getArguments() != null) {
//            backBootom = getArguments().getInt("p");
//        }
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    thread.stop();
//                    return true;
//                }
//                return false;
//            }
//        });
//    }

    public void calling(View root) {
        danielHomeViewModel = new ViewModelProvider(this).get(DanielHomeViewModel.class);
        textView = root.findViewById(R.id.text_danielHome);
        page = root.findViewById(R.id.my_pager);
        tabLayout = root.findViewById(R.id.my_tablayout);
        listItem = new ArrayList<>();
    }

    public void handling() {
        tabLayout.setupWithViewPager(page, true);
        danielHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        setImageSlider();
    }

    public void setImageSlider() {
        itemsPager_adapter = new The_Slide_items_Pager_Adapter(getActivity(), listItem);
        page.setAdapter(itemsPager_adapter);
        mDatabasephoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    The_Slide_Items_Model_Class the_slide_items_model_class = dataSnapshot.getValue(The_Slide_Items_Model_Class.class);
                    listItem.add(the_slide_items_model_class);
                    itemsPager_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new The_slide_timer(listItem), 2000, 3000);
    }

    //todo handle back bottom
    public class The_slide_timer extends TimerTask {
        List<The_Slide_Items_Model_Class> listItems;

        public The_slide_timer(List<The_Slide_Items_Model_Class> listItems) {
            this.listItems = listItems;
        }


        @Override
        public void run() {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(getActivity() != null){
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int currentItem, listSize;
                                currentItem = page.getCurrentItem();
                                listSize = listItems.size();
                                if (currentItem < listSize - 1) {
                                    page.setCurrentItem(page.getCurrentItem() + 1);
                                } else
                                    page.setCurrentItem(0);
                            }
                        });
                    }
                }
            });
            thread.start();
        }
    }
}

