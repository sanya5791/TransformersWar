package com.akhutornoy.transformerswar.ui.addedit;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;

import com.akhutornoy.transformerswar.R;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;

public class EditTransformerFragment extends AddTransformerFragment {
    private static final String ARG_TRANSFORMER = "ARG_TRANSFORMER";

    private String transformerId;

    public static EditTransformerFragment newInstance(Transformer transformer) {
        EditTransformerFragment fragment = new EditTransformerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRANSFORMER, transformer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        Transformer argTransformer = getArgTransformer();
        transformerId = argTransformer.getId();
        initTransformer(argTransformer);
    }

    @Override
    protected @StringRes int getToolbarTitle() {
        return R.string.add_edit_transformer_title;
    }

    private Transformer getArgTransformer() {
        assert getArguments() != null;
        return getArguments().getParcelable(ARG_TRANSFORMER);
    }

    private void initTransformer(Transformer transformer) {
        Team team = Team.valueOf(transformer.getTeam());
        setTeamRg(team);
        setName(transformer.getName());
        setStrength(String.valueOf(transformer.getStrength()));
        setIntelligence(String.valueOf(transformer.getIntelligence()));
        setSpeed(String.valueOf(transformer.getSpeed()));
        setEndurance(String.valueOf(transformer.getEndurance()));
        setRank(String.valueOf(transformer.getRank()));
        setCourage(String.valueOf(transformer.getCourage()));
        setFirePower(String.valueOf(transformer.getFirepower()));
        setSkill(String.valueOf(transformer.getSkill()));
    }

    @Override
    protected Transformer getTransformerData() {
        Transformer transformer = super.getTransformerData();
        transformer.setId(transformerId);
        return transformer;
    }
}
