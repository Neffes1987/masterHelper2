package com.masterhelper.npc;

import android.content.Intent;
import android.net.Uri;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.media.repository.MediaModel;
import com.masterhelper.media.repository.MediaRepository;
import com.masterhelper.npc.repository.NPCModel;
import com.masterhelper.npc.repository.NPCRepository;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.appBar.AppMenuActivity;
import com.masterhelper.ux.components.library.image.ComponentUIImage;

import java.io.File;

import static android.text.InputType.*;
import static com.masterhelper.media.FileViewerWidget.SELECTED_IDS_INTENT_EXTRA_NAME;
import static com.masterhelper.ux.components.library.image.Image.IMAGE_WIDGET_INTENT_RESULT;

public class NPCPage extends AppMenuActivity implements TabLayout.OnTabSelectedListener, SetBtnLocation {
  public final static String INTENT_PAGE_NPC_ID = "npcId";
  NPCModel currentNPC;
  NPCRepository npcRepository;
  MediaRepository mediaRepository;
  FragmentManager mn;
  ComponentUIImage previewControl;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_npc_page);

    TabLayout tabLayout = findViewById(R.id.NPC_TABS_ID);
    tabLayout.addOnTabSelectedListener(this);
    mn = getSupportFragmentManager();

    npcRepository = GlobalApplication.getAppDB().npcRepository;
    mediaRepository = GlobalApplication.getAppDB().mediaRepository;

    String NPCId = getIntent().getStringExtra(INTENT_PAGE_NPC_ID);
    currentNPC = npcRepository.getRecord(NPCId);
    switchTab(1);
    initMetaTab(currentNPC);
  }

  /**
   * Called when a tab enters the selected state.
   *
   * @param tab The tab that was selected
   */
  @Override
  public void onTabSelected(TabLayout.Tab tab) {
    switchTab(tab.getPosition() + 1);
  }

  /**
   * Called when a tab exits the selected state.
   *
   * @param tab The tab that was unselected
   */
  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  /**
   * Called when a tab that is already selected is chosen again by the user. Some applications may
   * use this action to return to the top level of a category.
   *
   * @param tab The tab that was reselected.
   */
  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }

  void toggleContainer(View container, boolean state) {
    container.setVisibility(state ? View.VISIBLE : View.GONE);
  }

  void switchTab(int tabIndex) {
    LinearLayout meta = findViewById(R.id.NPC_VIEW_MODE);
    LinearLayout edit = findViewById(R.id.NPC_EDIT_MODE);
    FloatingActionButton apply = findViewById(R.id.NPC_EDIT_TABS_APPLY_BTN_ID);

    toggleContainer(meta, tabIndex == 1);
    toggleContainer(edit, tabIndex == 2);
    toggleContainer(apply, tabIndex != 1);

    if (tabIndex == 1) {
      initMetaTab(currentNPC);
      return;
    }

    if (tabIndex == 2) {
      initEditTab(currentNPC);
      apply.setOnClickListener(v -> fillNPCModelByEditFields(currentNPC));
    }
  }

  void initTextField(int id, String value) {
    TextView label = findViewById(id);
    label.setText(value);
  }

  void initEditField(int id, String value, int textLength) {
    EditText field = findViewById(id);
    field.setText(value);
    if (textLength > 0) {
      field.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textLength)});
    }
    field.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_POSTAL_ADDRESS | TYPE_TEXT_FLAG_MULTI_LINE);
  }

  void initMetaTab(NPCModel currentModel) {
    initImageWidget(currentModel);
    initTextField(R.id.NPC_AGE_VIEW_ID, currentModel.age.get());
    initTextField(R.id.NPC_CHARACTER_VIEW_ID, currentModel.character.get());
    initTextField(R.id.NPC_RELATIONSHIP_VIEW_ID, currentModel.relations.get());
    initTextField(R.id.NPC_GOALS_VIEW_ID, currentModel.goals.get());
    initTextField(R.id.NPC_BACKGROUND_VIEW_ID, currentModel.background.get());

    setPageTitle(currentNPC.name.get());
  }

  void initEditTab(NPCModel currentModel) {
    initEditField(R.id.NPC_NAME_EDIT_FIELD, currentModel.name.get(), npcRepository.getNameLength());
    initEditField(R.id.NPC_AGE_EDIT_FIELD, currentModel.age.get(), 0);
    initEditField(R.id.NPC_CHARACTER_EDIT_FIELD, currentModel.character.get(), npcRepository.getCharacterLength());
    initEditField(R.id.NPC_RELATIONS_EDIT_FIELD, currentModel.relations.get(), npcRepository.getRelationLength());
    initEditField(R.id.NPC_GOALS_EDIT_FIELD, currentModel.goals.get(), npcRepository.getGoalsLength());
    initEditField(R.id.NPC_BACKGROUND_EDIT_FIELD, currentModel.background.get(), npcRepository.getBackgroundLength());
  }


  String getValueFromField(int id) {
    EditText field = findViewById(id);
    return field.getText().toString();
  }

  void fillNPCModelByEditFields(NPCModel currentModel) {
    String newName = getValueFromField(R.id.NPC_NAME_EDIT_FIELD);
    currentModel.name.set(newName);

    String newAge = getValueFromField(R.id.NPC_AGE_EDIT_FIELD);
    currentModel.age.set(newAge);

    String newCharacter = getValueFromField(R.id.NPC_CHARACTER_EDIT_FIELD);
    currentModel.character.set(newCharacter);

    String newRelations = getValueFromField(R.id.NPC_CHARACTER_EDIT_FIELD);
    currentModel.relations.set(newRelations);

    String newGoals = getValueFromField(R.id.NPC_GOALS_EDIT_FIELD);
    currentModel.goals.set(newGoals);

    String newBackground = getValueFromField(R.id.NPC_BACKGROUND_EDIT_FIELD);
    currentModel.background.set(newBackground);

    currentModel.save();
    switchTab(1);
  }

  void initImageWidget(NPCModel currentModel) {
    previewControl = ComponentUIImage.cast(mn.findFragmentById(R.id.NPC_PREVIEW_ID));
    previewControl.controls.setOnClick(this);
    previewControl.controls.setId(View.generateViewId());
    if (currentModel.previewUrl != null && currentModel.previewUrl.get() != null && currentModel.previewUrl.get().length() > 0) {
      previewControl.controls.setFile(new File(currentModel.previewUrl.get()));
    }
  }

  /**
   * click callback for short click event
   *
   * @param btnId - element unique id that fired event
   * @param tag   - element unique tag for searching in list
   */
  @Override
  public void onClick(int btnId, String tag) {

  }

  /**
   * click callback for long click event
   *
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {
    if (btnId == previewControl.controls.getId()) {
      previewControl.controls.openImageSelector(this);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == IMAGE_WIDGET_INTENT_RESULT && data != null) {
        String[] selectedItems = data.getStringArrayExtra(SELECTED_IDS_INTENT_EXTRA_NAME);
        if (selectedItems != null && selectedItems.length > 0) {
          MediaModel media = mediaRepository.getRecord(selectedItems[0]);
          Uri fileUri = Uri.parse(media.filePath.get());
          currentNPC.previewId.set(selectedItems[0]);
          currentNPC.previewUrl.set(fileUri.getPath());
          previewControl.controls.setFile(new File(fileUri.getPath()));
        } else {
          currentNPC.previewId.set("");
          previewControl.controls.clearPreview();
        }
        currentNPC.save();
      }
    }
  }

  @Override
  protected void onAppBarMenuItemControl() {

  }
}