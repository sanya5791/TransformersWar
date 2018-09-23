package com.akhutornoy.transformerswar.ui.transformerlist.addedit;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.akhutornoy.transformerswar.R;
import com.akhutornoy.transformerswar.base.BaseActivity;
import com.akhutornoy.transformerswar.base.BaseFragment;
import com.akhutornoy.transformerswar.base.BaseViewModel;
import com.akhutornoy.transformerswar.base.toolbar.BaseToolbar;
import com.akhutornoy.transformerswar.base.toolbar.IToolbar;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;

import javax.inject.Inject;

public class AddTransformerFragment extends BaseFragment {

    @Inject
    AddTransformerViewModel viewModel;

    private RadioGroup teamRg;
    private EditText nameEt;
    private EditText strengthEt;
    private EditText intelligenceEt;
    private EditText speedEt;
    private EditText enduranceEt;
    private EditText rankEt;
    private EditText courageEt;
    private EditText firepowerEt;
    private EditText skillEt;

    private Navigation navigation;

    public static AddTransformerFragment newInstance() {
        return new AddTransformerFragment();
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
        viewModel.getOnTransformerAdded().observe(this,
                isAdded -> navigation.navigateToTransformersList());
    }

    @Override
    @LayoutRes
    protected int getFragmentLayoutId() {
        return R.layout.fragment_add_transformer;
    }

    @Override
    protected void initViews(View view) {
        initToolbar(view);
        initViewsById(view);
        initListeners(view);
    }

    private void initToolbar(View view) {
        IToolbar toolbar = new BaseToolbar((BaseActivity) getActivity());
        setHasOptionsMenu(true);
        Toolbar toolbarView = view.findViewById(R.id.toolbar_view);
        toolbar.setToolbar(toolbarView, true);
        toolbar.setToolbarTitle(R.string.add_add_transformer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            navigation.navigateToTransformersList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewsById(View view) {
        teamRg = view.findViewById(R.id.transformer_type_rg);
        nameEt = view.findViewById(R.id.name_et);
        strengthEt = view.findViewById(R.id.strength_et);
        intelligenceEt = view.findViewById(R.id.intelligence_et);
        speedEt = view.findViewById(R.id.speed_et);
        enduranceEt = view.findViewById(R.id.endurance_et);
        rankEt = view.findViewById(R.id.rank_et);
        courageEt = view.findViewById(R.id.courage_et);
        firepowerEt = view.findViewById(R.id.firepower_et);
        skillEt = view.findViewById(R.id.skill_et);
    }

    private void initListeners(View view) {
        view.findViewById(R.id.save_button).setOnClickListener(v -> onSaveClicked());
    }

    private void onSaveClicked() {
        Transformer transformer = getTransformerData();
        viewModel.addTransformer(transformer);
    }

    private Transformer getTransformerData() {
        return new Transformer.Builder()
                .setTeam(getTeam())
                .setName(nameEt.getText().toString())
                .setStrength(getNumber(strengthEt))
                .setIntelligence(getNumber(intelligenceEt))
                .setSpeed(getNumber(speedEt))
                .setEndurance(getNumber(enduranceEt))
                .setRank(getNumber(rankEt))
                .setCourage(getNumber(courageEt))
                .setFirepower(getNumber(firepowerEt))
                .setSkill(getNumber(skillEt))
                .build();
    }

    private String getTeam() {
        int checkedId = teamRg.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.autobot_rb:
                return getString(R.string.autobot_team_api_code);
            case R.id.deception_rb:
                return getString(R.string.decepticon_team_api_code);
            default:
                throw new IllegalArgumentException(String.format("can't find Team for id %d", checkedId));
        }
    }

    private int getNumber(EditText editText) {
        String text = editText.getText().toString();
        return text.isEmpty() ? 0 : Integer.valueOf(text);
    }

    public interface Navigation {
        void navigateToTransformersList();
    }
}
