package abkrino.eg.baytaleaala.ui.demyamaHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import abkrino.eg.baytaleaala.R;
import abkrino.eg.baytaleaala.ui.danielHome.DanielHomeFragment;
import abkrino.eg.baytaleaala.ui.danielHome.DanielHomeViewModel;
import abkrino.eg.baytaleaala.ui.danielHome.The_Slide_Items_Model_Class;
import abkrino.eg.baytaleaala.ui.danielHome.The_Slide_items_Pager_Adapter;
//import com.squareup.picasso.Picasso;

public class DemyamaHomeFragment extends Fragment {
    private DemyamaHomeViewModel demyamaHomeViewModel;
    private List<The_Slide_Items_Model> listItem;
    private ViewPager page;
    private TabLayout tabLayout;
    TextView textView;
    The_Slide_items_Adapter itemsPager_adapter;
    private final DatabaseReference mDatabasephoto = FirebaseDatabase.getInstance().getReference("pageDanielHome/images");
    Thread thread;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_demyamahome, container, false);
        calling(root);
        handling();




        return root;
    }
    public void calling(View root) {
        demyamaHomeViewModel = new ViewModelProvider(this).get(DemyamaHomeViewModel.class);
        textView = root.findViewById(R.id.text_demyamaHome);
        page = root.findViewById(R.id.my_pager);
        tabLayout = root.findViewById(R.id.my_tablayout);
        listItem = new ArrayList<>();
    }
    public void handling() {
        tabLayout.setupWithViewPager(page, true);
        demyamaHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        setImageSlider();
    }
    public void setImageSlider() {
        itemsPager_adapter = new The_Slide_items_Adapter(getActivity(), listItem);
        page.setAdapter(itemsPager_adapter);
        mDatabasephoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    The_Slide_Items_Model the_slide_items_model = dataSnapshot.getValue(The_Slide_Items_Model.class);
                    listItem.add(the_slide_items_model);
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
        List<The_Slide_Items_Model> listItems;

        public The_slide_timer(List<The_Slide_Items_Model> listItems) {
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