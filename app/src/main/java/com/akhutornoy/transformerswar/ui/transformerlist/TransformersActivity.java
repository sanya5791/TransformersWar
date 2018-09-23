package com.akhutornoy.transformerswar.ui.transformerlist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.akhutornoy.transformerswar.R;
import com.akhutornoy.transformerswar.base.BaseActivity;

public class TransformersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {});
        if (savedInstanceState == null) {
            showFragment(TransformersFragment.newInstance());
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
