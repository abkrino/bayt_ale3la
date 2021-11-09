package abkrino.eg.baytaleaala.ui.demyamaHome;

//import com.squareup.picasso.Picasso;

public class The_Slide_Items_Model {
    private String imageUrl;

    public The_Slide_Items_Model() {
    }

    public The_Slide_Items_Model(String hero) {
        this.imageUrl = hero;

    }

    public String getFeatured_image() {

     return imageUrl;
    }



    public void setFeatured_image(String featured_image) {
        this.imageUrl = featured_image;
    }


}
