package com.d4ti.lapoutta.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.d4ti.lapoutta.R;
import com.d4ti.lapoutta.apiHelper.BaseApiService;
import com.d4ti.lapoutta.apiHelper.UtilsApi;
import com.d4ti.lapoutta.modal.Transaction;

import java.util.List;

public class StoreTransactionAdapter extends RecyclerView.Adapter<StoreTransactionAdapter.ViewHolder> {

    private List<Transaction> transactions;
    private Context context;
    private BaseApiService baseApiService;

    public StoreTransactionAdapter(Context context) {
        this.context = context;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_detailtransaction, viewGroup, false);
        baseApiService = UtilsApi.getAPIService();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_no_order;
        private TextView tv_status_send;
        private TextView tv_status_transaction;
        private Button btn_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_no_order = itemView.findViewById(R.id.txt_no_order);
            tv_status_send = itemView.findViewById(R.id.txt_status_send);
            tv_status_transaction = itemView.findViewById(R.id.txt_status_transaction);
            btn_view = itemView.findViewById(R.id.btn_view);
        }
    }
}
