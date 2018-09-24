package com.akhutornoy.transformerswar.ui.addedit;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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
import com.akhutornoy.transformerswar.utils.validation.validator.TransformerCriteriaValidator;
import com.akhutornoy.transformerswar.utils.validation.validator.TransformerNameValidator;
import com.akhutornoy.transformerswar.utils.validation.models.ValidationModel;
import com.akhutornoy.transformerswar.utils.validation.models.ValidationResult;
import com.akhutornoy.transformerswar.utils.validation.validator.ValidationState;

import java.util.ArrayList;
import java.util.List;

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
        viewModel.getOnTransformerValidated().observe(this,
                this::onTransformerValidated);

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
        toolbar.setToolbarTitle(getToolbarTitle());
    }

    protected @StringRes int getToolbarTitle() {
        return R.string.add_add_transformer;
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
        List<ValidationModel> validationModels = getValidationModels();
        viewModel.validate(validationModels);
    }

    private List<ValidationModel> getValidationModels() {
        List<ValidationModel> models = new ArrayList<>();
        models.add(getNameValidationModel());
        models.add(getCriteriaValidationModel(strengthEt));
        models.add(getCriteriaValidationModel(intelligenceEt));
        models.add(getCriteriaValidationModel(speedEt));
        models.add(getCriteriaValidationModel(enduranceEt));
        models.add(getCriteriaValidationModel(rankEt));
        models.add(getCriteriaValidationModel(courageEt));
        models.add(getCriteriaValidationModel(firepowerEt));
        models.add(getCriteriaValidationModel(skillEt));
        return models;
    }

    private ValidationModel getNameValidationModel() {
        TransformerNameValidator validator = new TransformerNameValidator(nameEt.getText().toString(), ValidationState.IS_EMPTY);
        return new ValidationModel(nameEt.getId(), validator, true);
    }

    private ValidationModel getCriteriaValidationModel(EditText criteriaEt) {
        int criteriaValue = getNumber(criteriaEt);
        TransformerCriteriaValidator validator = new TransformerCriteriaValidator(criteriaValue,
                ValidationState.IS_EMPTY,
                ValidationState.TRANSFORMER_CRITERIA_WRONG_FORMAT);
        return new ValidationModel(criteriaEt.getId(), validator, true);
    }

    private void onTransformerValidated(ValidationResult validationResult) {
        if (validationResult.isValid()) {
            Transformer transformer = getTransformerData();
            viewModel.addTransformer(transformer);
        } else {
            setValidationResults(validationResult.getResults());
        }
    }

    protected Transformer getTransformerData() {
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
        if (checkedId == Team.A.radioButtonId) {
            return Team.A.name();
        } else if (checkedId == Team.D.radioButtonId) {
            return Team.D.name();
        } else {
            throw new IllegalArgumentException(String.format("can't find Team for id %d", checkedId));
        }
    }

    private int getNumber(EditText editText) {
        String text = editText.getText().toString();
        return text.isEmpty() ? 0 : Integer.valueOf(text);
    }

    private void setValidationResults(List<ValidationResult.Result> results) {
        for (ValidationResult.Result result : results) {
            setValidationResult(result);
        }
    }

    private void setValidationResult(ValidationResult.Result result) {
        switch (result.getId()) {
            case R.id.name_et:
                setValidationResult(nameEt, result, R.string.add_cant_be_empty);
                break;
            case R.id.strength_et:
                setValidationResult(strengthEt, result, R.string.add_allowed_value_between_1_and_10);
                break;
            case R.id.intelligence_et:
                setValidationResult(intelligenceEt, result, R.string.add_allowed_value_between_1_and_10);
                break;
            case R.id.speed_et:
                setValidationResult(speedEt, result, R.string.add_allowed_value_between_1_and_10);
                break;
            case R.id.endurance_et:
                setValidationResult(enduranceEt, result, R.string.add_allowed_value_between_1_and_10);
                break;
            case R.id.rank_et:
                setValidationResult(rankEt, result, R.string.add_allowed_value_between_1_and_10);
                break;
            case R.id.courage_et:
                setValidationResult(courageEt, result, R.string.add_allowed_value_between_1_and_10);
                break;
            case R.id.firepower_et:
                setValidationResult(firepowerEt, result, R.string.add_allowed_value_between_1_and_10);
                break;
            case R.id.skill_et:
                setValidationResult(skillEt, result, R.string.add_allowed_value_between_1_and_10);
                break;
            default:
                throw new IllegalArgumentException(String.format("No View with id '%d'", result.getId()));
        }
    }

    private void setValidationResult(EditText editText, ValidationResult.Result result, @StringRes int errorMessage) {
        if (ValidationState.NO_ERRORS == result.getState()) {
            editText.setError(null);
        } else {
            editText.setError(getString(errorMessage));
        }
    }

    protected void setTeamRg(Team team) {
        switch (team) {
            case A:
                teamRg.check(Team.A.radioButtonId);
                break;
            case D:
                teamRg.check(Team.D.radioButtonId);
                break;
        }
    }

    protected void setName(String text) {
        nameEt.setText(text);
    }

    protected void setStrength(String text) {
        strengthEt.setText(text);
    }

    protected void setIntelligence(String text) {
        intelligenceEt.setText(text);
    }

    protected void setSpeed(String text) {
        speedEt.setText(text);
    }

    protected void setEndurance(String text) {
        enduranceEt.setText(text);
    }

    protected void setRank(String text) {
        rankEt.setText(text);
    }

    protected void setCourage(String text) {
        courageEt.setText(text);
    }

    protected void setFirePower(String text) {
        firepowerEt.setText(text);
    }

    protected void setSkill(String text) {
        skillEt.setText(text);
    }

    enum Team {
        A(R.id.autobot_rb),
        D(R.id.deception_rb);

        int radioButtonId;
        Team(@IdRes int radioButtonId) {
            this.radioButtonId = radioButtonId;
        }
    }

    public interface Navigation {
        void navigateToTransformersList();
    }
}
