package abkrino.eg.baytaleaala.ui.demyamaHome;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import abkrino.eg.baytaleaala.ui.danielHome.The_Slide_Items_Model_Class;

public class DemyamaHomeViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    ArrayList<abkrino.eg.baytaleaala.ui.demyamaHome.The_Slide_Items_Model> listItems;
    private MutableLiveData<The_Slide_Items_Model> myImages;
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("pageDemyamaHome");
    private final DatabaseReference mDatabasephoto = FirebaseDatabase.getInstance().getReference("pageDemyamaHome/images");
    public String text;


    public DemyamaHomeViewModel() {
        mText = new MutableLiveData<>();
        listItems = new ArrayList<>();
        myImages = new MutableLiveData<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                text = Objects.requireNonNull(snapshot.child("text").getValue()).toString();
                mText.setValue(text);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabasephoto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    The_Slide_Items_Model the_slide_items_model = dataSnapshot.getValue(The_Slide_Items_Model.class);
                    listItems.add(the_slide_items_model);
                    myImages.setValue(the_slide_items_model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<The_Slide_Items_Model> getImage() {
        return myImages;
    }

}