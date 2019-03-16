package com.d4ti.lapoutta.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.modal.Slide;

import java.util.ArrayList;
import java.util.List;

public class SlideAdapter extends BaseAdapter {

    private Context context;
    private List<Slide> slides;

    public SlideAdapter(Context context, List<Slide> slides) {
        this.context = context;
        this.slides = slides;
    }

    @Override
    public int getCount() {
        return slides.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Slide slide = slides.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = inflater.inflate(R.layout.item_list_slide, null);
        ImageView imageView = view.findViewById(R.id.img_slide);
        Glide.with(context).load(slide.getGambar()).into(imageView);
        return view;
    }
}
