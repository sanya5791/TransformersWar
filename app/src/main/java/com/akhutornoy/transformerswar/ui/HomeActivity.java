package com.akhutornoy.transformerswar.ui;

import android.os.Bundle;

import com.akhutornoy.transformerswar.R;
import com.akhutornoy.transformerswar.base.BaseActivity;
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.addedit.AddTransformerFragment;
import com.akhutornoy.transformerswar.ui.addedit.EditTransformerFragment;
import com.akhutornoy.transformerswar.ui.battle.BattleFragment;
import com.akhutornoy.transformerswar.ui.transformerlist.TransformersFragment;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity
        implements TransformersFragment.Navigation,
        AddTransformerFragment.Navigation,
        BattleFragment.Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            showFragment(TransformersFragment.newInstance());
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void navigateToCreateTransformer() {
        showFragment(AddTransformerFragment.newInstance(), true);
    }

    @Override
    public void navigateToEditTransformer(Transformer transformer) {
        showFragment(EditTransformerFragment.newInstance(transformer), true);
    }

    @Override
    public void navigateToStartBattle(ArrayList<Transformer> transformers) {
        showFragment(BattleFragment.newInstance(transformers), true);
    }

    @Override
    public void navigateBack() {
        onBackPressed();
    }
}
