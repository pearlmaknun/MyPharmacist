package io.pearlmaknun.mypharmacist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist.R;
import io.pearlmaknun.mypharmacist.model.Apotek;

public class ApotekAdapter extends RecyclerView.Adapter<ApotekAdapter.MyViewHolder> {
    List<Apotek> list;
    Context context;

    ApotekAdapter.OnItemClickListener mOnItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.jarak)
        TextView jarak;
        @BindView(R.id.nomor)
        TextView nomor;
        @BindView(R.id.alamat)
        TextView alamat;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(final ApotekAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public ApotekAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ApotekAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_apotek, parent, false);

        ApotekAdapter.MyViewHolder myViewHolder = new ApotekAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ApotekAdapter.MyViewHolder holder, final int position) {
        final Apotek item = list.get(position);
        holder.nama.setText(item.getApotikName());
        double value = Double.valueOf(item.getJarak());
        double roundOff = (double) Math.round(value * 100) / 100;
        holder.jarak.setText(roundOff + " KM");
        if(item.getApotikAddress() != null){
            holder.alamat.setText(item.getApotikAddress());
        }
        if(item.getApotikTelp() != null){
            holder.nomor.setText(item.getApotikTelp());
        }
        holder.itemView.setOnClickListener(v -> mOnItemClickListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Apotek item) {
        list.add(item);
        notifyItemInserted(list.size() + 1);
    }

    public void addAll(List<Apotek> listItem) {
        for (Apotek item : listItem) {
            add(item);
        }
    }

    public void removeAll() {
        list.clear();
        notifyDataSetChanged();
    }

    public void swap(List<Apotek> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (list != null && list.size() > 0)
            list.clear();
        list.addAll(datas);
        notifyDataSetChanged();
    }

    public Apotek getItem(int pos) {
        return list.get(pos);
    }

    public String showHourMinute(String hourMinute) {
        String time = "";
        time = hourMinute.substring(0, 5);
        return time;
    }

    public void setFilter(List<Apotek> list) {
        list = new ArrayList<>();
        list.addAll(list);
        notifyDataSetChanged();
    }

    public List<Apotek> getListItem() {
        return list;
    }
}