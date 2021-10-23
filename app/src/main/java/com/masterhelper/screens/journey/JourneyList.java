package com.masterhelper.screens.journey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.screens.ListScreen;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;

import java.util.ArrayList;

public class JourneyList extends ListScreen<JourneyModel> {
  String currentJourneyId;
  ContextPopupMenuBuilder contextPopupMenuBuilder;

  @Override
  public String getListTitle() {
    return getResources().getString(R.string.journey_list);
  }

  @Override
  public Boolean getBackButtonVisible() {
    return true;
  }

  @Override
  public Intent getCreateItemIntent() {
    return JourneyDetailsScreen.getScreenIntent(this);
  }

  @Override
  public ContextPopupMenuBuilder getHeaderPopup() {
    JourneyRepository repository = GlobalApplication.getAppDB().journeyRepository;

    contextPopupMenuBuilder = new ContextPopupMenuBuilder(new int[]{
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
              deleteItem(journeyId);
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

    return contextPopupMenuBuilder;
  }

  @Override
  public ArrayList<PropertyBarContentModel> getListItems() {
    JourneyRepository repository = GlobalApplication.getAppDB().journeyRepository;

    ArrayList<JourneyModel> journeyModels = repository.list(null);
    ArrayList<PropertyBarContentModel> items = new ArrayList<>();

    for (JourneyModel model : journeyModels) {
      PropertyBarContentModel UIItem = convertToItem(model);

      items.add(UIItem);
    }
    return items;
  }

  @Override
  public PropertyBarContentModel convertToItem(JourneyModel model) {
    return new PropertyBarContentModel(
      model.getId(),
      model.getTitle()
    );
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

  public static Intent getScreenIntent(Context context) {
    return new Intent(context, JourneyList.class);
  }
}