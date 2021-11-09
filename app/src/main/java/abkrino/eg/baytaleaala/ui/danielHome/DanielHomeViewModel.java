package abkrino.eg.baytaleaala.ui.danielHome;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DanielHomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    ArrayList<The_Slide_Items_Model_Class> listItems;
    private MutableLiveData<The_Slide_Items_Model_Class> myImages;
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("pageDanielHome");
    private final DatabaseReference mDatabasephoto = FirebaseDatabase.getInstance().getReference("pageDanielHome/images");
    public String text;


    public DanielHomeViewModel() {
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
                    The_Slide_Items_Model_Class the_slide_items_model_class = dataSnapshot.getValue(The_Slide_Items_Model_Class.class);
                    listItems.add(the_slide_items_model_class);
                    myImages.setValue(the_slide_items_model_class);
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



    public LiveData<The_Slide_Items_Model_Class> getImage() {
        return myImages;
    }
}