package com.masterhelper.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.masterhelper.R;

public abstract class CommonScreen extends AppCompatActivity {

  public void setActionBarTitle(int toolbarTitle) {
    ActionBar bar = getSupportActionBar();
    assert bar != null;
    bar.setTitle(toolbarTitle);
    bar.setDisplayShowHomeEnabled(true);
    bar.setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onSupportNavigateUp() {
    super.onSupportNavigateUp();

    onBackPressed();

    return true;
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, CommonScreen.class);
  }
}