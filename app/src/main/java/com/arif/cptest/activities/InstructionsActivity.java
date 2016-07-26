package com.arif.cptest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arif.cptest.R;
import com.arif.cptest.adapters.InstructionsRecyclerAdapter;
import com.arif.cptest.utils.SpacesItemDecoration;

public class InstructionsActivity extends AppCompatActivity {

    private RecyclerView mInstructionsRecyclerView;
    private RecyclerView.LayoutManager mVerticalLayoutManager;
    private String[] mInstructions;
    private InstructionsRecyclerAdapter mInstructionsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mInstructionsRecyclerView = (RecyclerView) findViewById(R.id.instructions_recycler_view);
        mInstructionsRecyclerView.addItemDecoration(new SpacesItemDecoration(12));
        mVerticalLayoutManager = new LinearLayoutManager(this);
        mInstructions = getResources().getStringArray(R.array.help_items);
        mInstructionsRecyclerAdapter = new InstructionsRecyclerAdapter(this, mInstructions);
        mInstructionsRecyclerView.setLayoutManager(mVerticalLayoutManager);
        mInstructionsRecyclerView.setAdapter(mInstructionsRecyclerAdapter);
    }

}
