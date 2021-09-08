package com.masterhelper.screens.journey;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.masterhelper.R;
import com.masterhelper.ux.UIAddButtonFragment;

public class JourneyEmptyScreen extends AppCompatActivity {
  UIAddButtonFragment addButtonFr;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_journey_empty_screen);
  }

  @Override
  protected void onStart() {
    super.onStart();

    addButtonFr = (UIAddButtonFragment) getSupportFragmentManager().findFragmentById(R.id.ADD_JOURNEY_FRAGMENT_ID);
    if (addButtonFr != null) {
      addButtonFr.setListener(v -> {
        startActivity(JourneyEditorScreen.getScreenIntent(this));
      });
    }
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, JourneyEmptyScreen.class);
  }
}