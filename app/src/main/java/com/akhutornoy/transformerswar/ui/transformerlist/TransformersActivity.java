package com.akhutornoy.transformerswar.ui.transformerlist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.akhutornoy.transformerswar.R;
import com.akhutornoy.transformerswar.base.BaseActivity;
import com.akhutornoy.transformerswar.ui.transformerlist.addedit.AddTransformerFragment;

public class TransformersActivity extends BaseActivity
        implements TransformersFragment.Navigation, AddTransformerFragment.Navigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            navigateToTransformersList();
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_transformers;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_warriors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateToCreateTransformer() {
        showFragment(AddTransformerFragment.newInstance(), true);
    }

    @Override
    public void navigateToEditTransformer() {
        Toast.makeText(this, "Not Implemented yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToTransformersList() {
        showFragment(TransformersFragment.newInstance());
    }
}
