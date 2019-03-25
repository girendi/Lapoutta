package com.d4ti.lapoutta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.modal.Notif;

import java.util.List;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder>{

    private Context context;
    private List<Notif> notifs;

    public NotifAdapter(Context context) {
        this.context = context;
    }

    public List<Notif> getNotifs() {
        return notifs;
    }

    public void setNotifs(List<Notif> notifs) {
        this.notifs = notifs;
    }

    @NonNull
    @Override
    public NotifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_notif, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.ViewHolder viewHolder, int i) {
        viewHolder.txtTitle.setText(getNotifs().get(i).getTitle());
        viewHolder.txtBody.setText(getNotifs().get(i).getBody());
    }

    @Override
    public int getItemCount() {
        return notifs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView txtTitle;
        TextView txtBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtBody = itemView.findViewById(R.id.txt_body);
        }
    }
}
