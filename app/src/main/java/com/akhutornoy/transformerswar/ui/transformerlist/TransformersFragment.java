package com.akhutornoy.transformerswar.ui.transformerlist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akhutornoy.transformerswar.R;
import com.akhutornoy.transformerswar.base.BaseActivity;
import com.akhutornoy.transformerswar.base.BaseFragment;
import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.base.toolbar.BaseToolbar;
import com.akhutornoy.transformerswar.base.toolbar.IToolbar;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class TransformersFragment extends BaseFragment {

    @Inject
    TransformersViewModel viewModel;

    private Navigation navigation;
    private TransformersAdapter adapter;

    public static TransformersFragment newInstance() {
        return new TransformersFragment();
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
    protected void initViewModelObservers() {
        viewModel.getOnTransformersLoadedLiveData()
                .observe(this, this::showTransformers);
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_tranformers;
    }

    @Override
    protected void initViews(View view) {
        initToolbar(view);
        initListeners(view);
        initList(view);

        List<TransformerModel> transformers = viewModel.getOnTransformersLoadedLiveData().getValue();
        if (transformers == null) {
            viewModel.loadTransformers();
        } else {
            showTransformers(transformers);
        }
    }

    private void initToolbar(View view) {
        IToolbar toolbar = new BaseToolbar((BaseActivity) getActivity());
        Toolbar toolbarView = view.findViewById(R.id.toolbar_view);
        toolbar.setToolbar(toolbarView, false);
        toolbar.setToolbarTitle(R.string.transformers_list_transformers);
    }

    private void initListeners(View view) {
        view.findViewById(R.id.battle_button).setOnClickListener(v -> onBattleClicked());
        view.findViewById(R.id.add_fab).setOnClickListener(v -> onAddTransformerClicked());
    }

    private void onBattleClicked() {
        viewModel.loadTransformers();
    }

    private void onAddTransformerClicked() {
        navigation.navigateToCreateTransformer();
    }

    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new TransformersAdapter(onEditTransformer());
        recyclerView.setAdapter(adapter);
    }

    private TransformersAdapter.OnEditListener onEditTransformer() {
        return new TransformersAdapter.OnEditListener() {
            @Override
            public void onEdit(TransformerModel transformerModel) {
                Toast.makeText(getActivity(), "On Edit", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDelete(TransformerModel transformerModel) {
                Toast.makeText(getActivity(), "On Delete", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void showTransformers(List<TransformerModel> transformers) {
        adapter.setTransformers(transformers);
    }

    interface Navigation {
        void navigateToCreateTransformer();
        void navigateToEditTransformer();
    }
}
