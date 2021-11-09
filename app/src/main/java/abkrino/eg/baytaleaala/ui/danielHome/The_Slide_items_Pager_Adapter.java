package abkrino.eg.baytaleaala.ui.danielHome;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.util.List;

import abkrino.eg.baytaleaala.R;

public class The_Slide_items_Pager_Adapter extends PagerAdapter {
    Context Mcontext;
    List<The_Slide_Items_Model_Class> theSlideItemsModelClassList;

    public The_Slide_items_Pager_Adapter(Context Mcontext, List<The_Slide_Items_Model_Class> theSlideItemsModelClassList) {
        this.Mcontext = Mcontext;
        this.theSlideItemsModelClassList = theSlideItemsModelClassList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.item_layout,container ,false);

        ImageView featured_image = sliderLayout.findViewById(R.id.my_featured_image);
//        Picasso.get().load(theSlideItemsModelClassList.get(position).getFeatured_image()).into(featured_image);
        Glide.with(Mcontext).load(theSlideItemsModelClassList.get(position).getFeatured_image()).into(featured_image);
//        featured_image.setImageURI(Uri.parse(theSlideItemsModelClassList.get(position).getFeatured_image()));
        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return theSlideItemsModelClassList.size();

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;

    }
}
