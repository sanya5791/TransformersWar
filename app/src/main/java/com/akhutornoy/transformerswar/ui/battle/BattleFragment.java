package com.akhutornoy.transformerswar.ui.battle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akhutornoy.transformerswar.R;
import com.akhutornoy.transformerswar.base.BaseActivity;
import com.akhutornoy.transformerswar.base.BaseFragment;
import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.base.toolbar.BaseToolbar;
import com.akhutornoy.transformerswar.base.toolbar.IToolbar;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.AfterBattleState;
import com.akhutornoy.transformerswar.ui.battle.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class BattleFragment extends BaseFragment {
    private static final String ARG_TRANSFORMERS_LIST = "ARG_TRANSFORMERS_LIST";

    @Inject
    BattleViewModel viewModel;

    private Navigation navigation;

    private BattleAdapter adapter;

    private TextView winnerTeamTv;
    private TextView killedAutobotsTv;
    private TextView killedDecepticonsTv;

    public static BattleFragment newInstance(ArrayList<Transformer> transformerList) {
        BattleFragment fragment = new BattleFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_TRANSFORMERS_LIST, transformerList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Navigation)) {
            throw new IllegalArgumentException(String.format("Calling Activity '%s' should implement interface '%s'",
                    context.getClass().getSimpleName(), Navigation.class.getName()));
        }
        navigation = (Navigation) context;
    }

    @Nullable
    @Override
    protected BaseViewModel getBaseViewModel() {
        return viewModel;
    }

    @Override
    protected @LayoutRes int getFragmentLayoutId() {
        return R.layout.fragment_battle;
    }

    @Override
    protected void initViewModelObservers() {
        viewModel.getOnAfterBattleViewModel()
                .observe(this, this::showBattleResult);
    }

    @Override
    protected void initViews(View view) {
        initToolbar(view);
        initViewsById(view);
        initList(view);
        initListeners(view);

        viewModel.battle(getTransformers());
    }

    private List<Transformer> getTransformers() {
        ArrayList<Transformer> transformers = getArguments().getParcelableArrayList(ARG_TRANSFORMERS_LIST);
        return transformers;
    }

    private void initViewsById(View view) {
        winnerTeamTv = view.findViewById(R.id.winner_text_tv);
        killedAutobotsTv = view.findViewById(R.id.killed_autobots_text_tv);
        killedDecepticonsTv = view.findViewById(R.id.killed_decept_text_tv);
    }

    private void initListeners(View view) {
        view.findViewById(R.id.button).setOnClickListener(v -> navigation.navigateBack());
    }

    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new BattleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initToolbar(View view) {
        IToolbar toolbar = new BaseToolbar((BaseActivity) getActivity());
        setHasOptionsMenu(true);
        Toolbar toolbarView = view.findViewById(R.id.toolbar_view);
        toolbar.setToolbar(toolbarView, true);
        toolbar.setToolbarTitle(R.string.battle_battle_results);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            navigation.navigateBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showBattleResult(AfterBattleState afterBattleState) {
        Team winner = afterBattleState.getWinner();
        winnerTeamTv.setText(getWinnerTitle(winner));
        winnerTeamTv.setTextColor(getWinnerColorResource(winner));
        winnerTeamTv.setGravity(Gravity.CENTER_VERTICAL);
        winnerTeamTv.setCompoundDrawablesWithIntrinsicBounds(0, getWinnerIco(winner), 0, 0);
        killedAutobotsTv.setText(String.valueOf(afterBattleState.getAutobotsKilled()));
        killedDecepticonsTv.setText(String.valueOf(afterBattleState.getDecepticonsKilled()));

        adapter.setItems(afterBattleState.getFacedFighters());
    }

    private String getWinnerTitle(Team winner) {
        String winnerString;
        switch (winner) {
            case AUTOBOT:
                winnerString = getString(R.string.battle_autobots);
                break;
            case DECEPTICON:
                winnerString = getString(R.string.battle_decepticons);
                break;
            case TOTAL_ANNIHILATION:
                winnerString = getString(R.string.battle_all_loose);
                break;
            default:
                winnerString = getString(R.string.battle_no_winner);
        }

        return getString(R.string.battle_winner_is, winnerString);
    }

    private @ColorInt
    int getWinnerColorResource(Team winner) {
        @ColorRes int resColor;

        switch (winner) {
            case AUTOBOT:
                resColor = R.color.autobot;
                break;
            case DECEPTICON:
                resColor = R.color.decepticon;
                break;
            default:
                resColor = android.R.color.black;
        }
        return ContextCompat.getColor(Objects.requireNonNull(getActivity()), resColor);
    }

    private @DrawableRes int getWinnerIco(Team winner) {
        switch (winner) {
            case AUTOBOT:
                return R.drawable.ic_autobot;
            case DECEPTICON:
                return R.drawable.ic_decept;
            default:
                return 0;
        }
    }

    public interface Navigation {
        void navigateBack();
    }
}
