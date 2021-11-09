package abkrino.eg.baytaleaala.ui.danielHome;

import android.graphics.Bitmap;
import android.net.Uri;

//import com.squareup.picasso.Picasso;

public class The_Slide_Items_Model_Class {
    private String featured_image;

    public The_Slide_Items_Model_Class() {
    }

    public The_Slide_Items_Model_Class(String hero) {
        this.featured_image = hero;

    }

    public String getFeatured_image() {

     return featured_image;
    }



    public void setFeatured_image(String featured_image) {
        this.featured_image = featured_image;
    }


}
