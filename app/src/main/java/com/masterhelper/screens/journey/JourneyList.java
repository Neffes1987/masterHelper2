package com.masterhelper.screens.journey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.screens.CommonScreen;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.UIAddButtonFragment;
import com.masterhelper.ux.list.ListAdapter;
import com.masterhelper.ux.list.ListFragment;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;

import java.util.ArrayList;
import java.util.List;

public class JourneyList extends CommonScreen {
  private JourneyListAdapter adapter;
  String currentJourneyId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_journeys_list);
    JourneyRepository repository = GlobalApplication.getAppDB().journeyRepository;

    ContextPopupMenuBuilder contextPopupMenuBuilder = new ContextPopupMenuBuilder(new int[]{
      R.string.open,
      R.string.delete
    });

    contextPopupMenuBuilder.setPopupMenuClickHandler((journeyId, menuItemIndex) -> {
      switch (menuItemIndex) {
        case 0:
          startActivity(CurrentScreen.getScreenIntent(this, journeyId));
          break;
        case 1:
          AlertDialog.Builder builder = new AlertDialog.Builder(this);

          builder.setPositiveButton(R.string.ok, (dialog, which) -> {
              repository.delete(journeyId);
              adapter.deleteItem(journeyId);
              if (currentJourneyId.equals(journeyId)) {
                showBackButton(false);
                cleanSetting(Setting.JourneyId);
              }
            })
            .setNegativeButton(R.string.cancel, (dialog, which) -> {
              dialog.dismiss();
            })
            .setTitle(R.string.journey_delete_title)
            .create()
            .show();
          break;
      }
      return true;
    });
    ArrayList<JourneyModel> journeyModels = repository.list(null);
    adapter = new JourneyListAdapter(contextPopupMenuBuilder);
    adapter.addJourneys(journeyModels);

    setActionBarTitle(R.string.journey_list);
  }

  @Override
  protected void onStart() {
    super.onStart();
    currentJourneyId = getSetting(Setting.JourneyId);
    showBackButton(currentJourneyId.length() > 0);
    if (adapter.getItemCount() == 0) {
      startActivity(EmptyCurrentScreen.getScreenIntent(this));
    }
  }

  @Override
  protected void onInitScreen() {
    UIAddButtonFragment addButtonFr = (UIAddButtonFragment) getSupportFragmentManager().findFragmentById(R.id.ADD_JOURNEY_FRAGMENT_ID);
    if (addButtonFr != null) {
      addButtonFr.setListener(v -> {
        startActivity(EditorScreen.getScreenIntent(this));
      });
    }

    ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.JOURNEYS_LIST_FRAGMENT_ID);
    assert listFragment != null;
    listFragment.setAdapter(adapter);
  }

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, JourneyList.class);
  }
}