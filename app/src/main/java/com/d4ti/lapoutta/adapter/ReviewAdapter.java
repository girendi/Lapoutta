package com.d4ti.lapoutta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Customer;
import com.d4ti.lapoutta.modal.Review;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    String url="http://192.168.43.157:1337/images/uploads/";

    private Context context;
    private List<Review> reviews;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    private BaseApiService baseApiService;

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_review, viewGroup, false);

        baseApiService = UtilsApi.getAPIService();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.txtReview.setText(getReviews().get(i).getBody());
        baseApiService.detailCustomer(getReviews().get(i).getId_customer()).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()){
                    List<Customer> customers = response.body();
                    if (!customers.isEmpty()){
                        viewHolder.txtName.setText(customers.get(0).getName());
                        Glide.with(context).load(url + customers.get(0).getImage()).into(viewHolder.civ_profile);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e("Message Error", t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civ_profile;
        TextView txtName;
        TextView txtReview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civ_profile = itemView.findViewById(R.id.img_profile);
            txtName = itemView.findViewById(R.id.txt_name);
            txtReview = itemView.findViewById(R.id.txt_review);
        }
    }
}
