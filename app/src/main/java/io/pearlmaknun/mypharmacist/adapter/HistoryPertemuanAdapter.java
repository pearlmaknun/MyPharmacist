package io.pearlmaknun.mypharmacist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist.R;
import io.pearlmaknun.mypharmacist.model.PertemuanHistory;

public class HistoryPertemuanAdapter extends RecyclerView.Adapter<HistoryPertemuanAdapter.MyViewHolder> {
    List<PertemuanHistory> list;
    Context context;

    HistoryPertemuanAdapter.OnItemClickListener mOnItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.waktu)
        TextView waktu;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.alamat)
        TextView alamat;
        @BindView(R.id.nomor)
        TextView nomor;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(final HistoryPertemuanAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public HistoryPertemuanAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public HistoryPertemuanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pertemuan, parent, false);

        HistoryPertemuanAdapter.MyViewHolder myViewHolder = new HistoryPertemuanAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final HistoryPertemuanAdapter.MyViewHolder holder, final int position) {
        final PertemuanHistory item = list.get(position);
        holder.nama.setText(item.getApotekerName());
        holder.waktu.setText(item.getPertemuanWaktu());
        if(item.getPertemuanAlamat() != null){
            holder.alamat.setText(item.getPertemuanAlamat());
        } else {
            if(item.getPertemuanLokasi() != null){
                holder.alamat.setText(item.getPertemuanLokasi());
            }
        }
        holder.nomor.setText(item.getApotekerNumber());
        switch (item.getPertemuanStatus()) {
            case "2":
                holder.status.setText("Berlangsung");
                holder.status.setTextColor(context.getResources().getColor(R.color.bg1));
                break;
            case "3":
                holder.status.setText("Expired");
                holder.status.setTextColor(context.getResources().getColor(R.color.red));
                break;
            case "4":
                holder.status.setText("Selesai");
                holder.status.setTextColor(context.getResources().getColor(R.color.green));
                break;
            default:
                break;
        }
        holder.itemView.setOnClickListener(v -> mOnItemClickListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(PertemuanHistory item) {
        list.add(item);
        notifyItemInserted(list.size() + 1);
    }

    public void addAll(List<PertemuanHistory> listItem) {
        for (PertemuanHistory item : listItem) {
            add(item);
        }
    }

    public void removeAll() {
        list.clear();
        notifyDataSetChanged();
    }

    public void swap(List<PertemuanHistory> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (list != null && list.size() > 0)
            list.clear();
        list.addAll(datas);
        notifyDataSetChanged();
    }

    public PertemuanHistory getItem(int pos) {
        return list.get(pos);
    }

    public String showHourMinute(String hourMinute) {
        String time = "";
        time = hourMinute.substring(0, 5);
        return time;
    }

    public void setFilter(List<PertemuanHistory> list) {
        list = new ArrayList<>();
        list.addAll(list);
        notifyDataSetChanged();
    }

    public List<PertemuanHistory> getListItem() {
        return list;
    }
}