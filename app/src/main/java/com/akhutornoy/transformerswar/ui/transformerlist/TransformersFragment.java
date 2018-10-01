package com.akhutornoy.transformerswar.ui.transformerlist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akhutornoy.transformerswar.R;
import com.akhutornoy.transformerswar.base.BaseActivity;
import com.akhutornoy.transformerswar.base.BaseFragment;
import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.base.toolbar.BaseToolbar;
import com.akhutornoy.transformerswar.base.toolbar.IToolbar;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.utils.SwipeToDeleteCallback;

import java.util.ArrayList;
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
        viewModel.getOnTransformerEditLiveData()
                .observe(this, this::onTransformerEdited);
        viewModel.getOnTransformerDeleteLiveData()
                .observe(this, this::onTransformerDeleted);
        viewModel.getOnStartBattleLiveData()
                .observe(this, this::navigateToBattleScreen);
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
        viewModel.startBattle();
    }

    private void onAddTransformerClicked() {
        viewModel.resetLiveData();
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

        setSwipeToDeleteCallback(recyclerView);
    }

    private void setSwipeToDeleteCallback(RecyclerView recyclerView) {
        SwipeToDeleteCallback deleteOnSwipeCallback = new SwipeToDeleteCallback(getActivity()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.onDeleteByPosition(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(deleteOnSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private TransformersAdapter.OnEditListener onEditTransformer() {
        return new TransformersAdapter.OnEditListener() {
            @Override
            public void onEdit(TransformerModel transformerModel) {
                viewModel.editTransformer(transformerModel);
            }

            @Override
            public void onDelete(TransformerModel transformerModel) {
                viewModel.deleteTransformer(transformerModel);
            }
        };
    }

    private void onTransformerEdited(@Nullable Transformer transformer) {
        if (transformer != null) {
            viewModel.resetLiveData();
            navigation.navigateToEditTransformer(transformer);
        }
    }

    private void navigateToBattleScreen(@Nullable ArrayList<Transformer> transformers) {
        if (transformers != null) {
            viewModel.resetLiveData();
            navigation.navigateToStartBattle(transformers);
        }
    }

    private void onTransformerDeleted(@Nullable TransformerModel transformer) {
        if (transformer != null) {
            adapter.deleteById(transformer.getId());
            showTransformerDeletedMessage(transformer);
        }
    }

    private void showTransformerDeletedMessage(TransformerModel transformer) {
        Toast toast = Toast.makeText(getActivity(), getString(R.string.transformers_is_deleted, transformer.getName()),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showTransformers(@Nullable List<TransformerModel> transformers) {
        if (transformers != null) {
            adapter.setTransformers(transformers);
        }
    }

    public interface Navigation {
        void navigateToCreateTransformer();
        void navigateToEditTransformer(Transformer transformer);
        void navigateToStartBattle(ArrayList<Transformer> transformers);
    }
}
