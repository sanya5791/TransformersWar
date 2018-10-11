package com.akhutornoy.transformerswar.ui.battle;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhutornoy.transformerswar.R;
import com.akhutornoy.transformerswar.repository.cache.TransformerEntity;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;

import java.util.List;

public class BattleAdapter extends RecyclerView.Adapter<BattleAdapter.ViewHolder>{

    private List<Fighters> items;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_battle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    public void setItems(List<Fighters> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameAutobotTv;
        private final ImageView iconAutobotIv;
        private final TextView nameDecepticonTv;
        private final ImageView iconDecepticonIv;

        private ViewHolder(View view) {
            super(view);
            nameAutobotTv = view.findViewById(R.id.name_autobot_tv);
            iconAutobotIv = view.findViewById(R.id.ico_autobot_iv);
            nameDecepticonTv = view.findViewById(R.id.name_decepticon_tv);
            iconDecepticonIv = view.findViewById(R.id.ico_decepticon_iv);
        }

        private void bind(Fighters fighters) {
            TransformerEntity a = fighters.getAutobot();
            TransformerEntity d = fighters.getDecepticon();
            nameAutobotTv.setText(a.getName());
            iconAutobotIv.setImageResource(getAutobotIco(
                    fighters.getFightResult()));

            nameDecepticonTv.setText(d.getName());
            iconDecepticonIv.setImageResource(getDecepticonIco(
                    fighters.getFightResult()));
        }

        @DrawableRes
        private int getAutobotIco(Fighters.FightResult result) {
            return result == Fighters.FightResult.AUTOBOT_WINNER
                    ? R.drawable.ic_autobot
                    : R.drawable.ic_autobot_killed;
        }

        @DrawableRes
        private int getDecepticonIco(Fighters.FightResult result) {
            return result == Fighters.FightResult.DECIPTICON_WINNER
                    ? R.drawable.ic_decept
                    : R.drawable.ic_decepticon_killed;
        }
    }
}