package io.pearlmaknun.mypharmacist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.pearlmaknun.mypharmacist.R;
import io.pearlmaknun.mypharmacist.model.NearestApoteker;

public class NearestApotekerAdapter extends RecyclerView.Adapter<NearestApotekerAdapter.MyViewHolder> {
    List<NearestApoteker> list;
    Context context;

    OnItemClickListener mOnItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.jarak)
        TextView jarak;
        @BindView(R.id.image)
        CircleImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public NearestApotekerAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_apoteker, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NearestApoteker item = list.get(position);
        if(item.getApotekerPhoto() != null){
            Glide.with(context)
                    .load(item.getApotekerPhoto())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.image);
        }
        holder.nama.setText(item.getApotekerName());
        holder.jarak.setText(item.getJarak() + " KM");
        holder.itemView.setOnClickListener(v -> mOnItemClickListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(NearestApoteker item) {
        list.add(item);
        notifyItemInserted(list.size() + 1);
    }

    public void addAll(List<NearestApoteker> listItem) {
        for (NearestApoteker item : listItem) {
            add(item);
        }
    }

    public void removeAll() {
        list.clear();
        notifyDataSetChanged();
    }

    public void swap(List<NearestApoteker> datas) {
        if (datas == null || datas.size() == 0)
            return;
        if (list != null && list.size() > 0)
            list.clear();
        list.addAll(datas);
        notifyDataSetChanged();
    }

    public NearestApoteker getItem(int pos) {
        return list.get(pos);
    }

    public String showHourMinute(String hourMinute) {
        String time = "";
        time = hourMinute.substring(0, 5);
        return time;
    }

    public void setFilter(List<NearestApoteker> list) {
        list = new ArrayList<>();
        list.addAll(list);
        notifyDataSetChanged();
    }

    public List<NearestApoteker> getListItem() {
        return list;
    }
}

