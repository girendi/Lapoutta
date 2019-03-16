package com.d4ti.lapoutta.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.modal.Product;

import java.util.List;

public class ProductSlideAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;

    public ProductSlideAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
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

        Product product = products.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = inflater.inflate(R.layout.item_list_slide, null);
        ImageView imageView = view.findViewById(R.id.img_slide);
        Glide.with(context).load(product.getImage()).into(imageView);
        return view;
    }
}
