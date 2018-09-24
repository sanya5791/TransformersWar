package com.akhutornoy.transformerswar.ui.transformerlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhutornoy.transformerswar.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class TransformersAdapter extends RecyclerView.Adapter<TransformersAdapter.ViewHolder>{

    private List<TransformerModel> items;
    private final OnEditListener callback;

    public TransformersAdapter(OnEditListener listener) {
        this.callback = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transformer, parent, false);
        return new ViewHolder(view, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    public void setTransformers(List<TransformerModel> transformers) {
        this.items = transformers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void deleteById(String deleteId) {
        for (int i = 0; i < items.size(); i++) {
            TransformerModel item = items.get(i);
            if (item.getId().equals(deleteId)) {
                items.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }

    public interface OnEditListener {
        void onEdit(TransformerModel transformerModel);
        void onDelete(TransformerModel transformerModel);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final OnEditListener callback;
        private final View rootView;
        private final TextView nameTv;
        private final TextView rateTv;
        private final ImageView iconIv;

        private ViewHolder(View view, OnEditListener listener) {
            super(view);
            rootView = view;
            callback = listener;
            nameTv = view.findViewById(R.id.name_tv);
            rateTv = view.findViewById(R.id.rating_tv);
            iconIv = view.findViewById(R.id.ico_iv);
        }

        private void bind(TransformerModel transformer) {
            nameTv.setText(transformer.getName());
            rateTv.setText(transformer.getRate());
            Glide.with(rootView)
                    .load(transformer.getImageUrl())
                    .into(iconIv);
            rootView.setOnClickListener(v -> callback.onEdit(transformer));
            rootView.setOnLongClickListener(v -> {
                callback.onDelete(transformer);
                return true;
            });
        }
    }
}