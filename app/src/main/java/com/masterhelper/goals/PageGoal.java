package com.masterhelper.goals;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.R;
import com.masterhelper.global.GlobalApplication;
import com.masterhelper.goals.repository.GoalModel;
import com.masterhelper.goals.repository.GoalRepository;
import com.masterhelper.ux.components.core.SetBtnLocation;
import com.masterhelper.ux.components.library.appBar.UIToolbar;
import com.masterhelper.ux.components.library.buttons.floating.ComponentUIFloatingButton;
import com.masterhelper.ux.components.library.dialog.ComponentUIDialog;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import java.util.Arrays;

public class PageGoal extends AppCompatActivity {
    public static final String INTENT_GOAL_ID = "goalId";

    GoalModel currentGoal;

    FragmentManager mn;
    GoalRepository repository;

    ComponentUIDialog dialog;
    private ComponentUILabel description;

    void setAppBarLabel(String newName, String newProgress ){
        UIToolbar.setTitle(this, newName, newProgress);
    }

    void setDescriptionLabel(String newDescription){
        if(description == null){
            description = ComponentUILabel.cast(mn.findFragmentById(R.id.GOAL_DESCRIPTION_ID));
        }
        description.controls.setText(newDescription);
    }

    void initUpdateButton(){
      ComponentUIFloatingButton floatingButton = ComponentUIFloatingButton.cast(mn.findFragmentById(R.id.GOAL_EDIT_ID));
      floatingButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.done));
      floatingButton.controls.setIconColor(ResourceColors.ResourceColorType.common);
      floatingButton.controls.setOnClick(new SetBtnLocation() {
        @Override
        public void onClick(int btnId, String tag) {
          dialog.setTitle(GoalLocale.getLocalizationByKey(GoalLocale.Keys.updateScene));
          dialog.pNameField.setText(currentGoal.name.get());
          dialog.pDescriptionField.setText(currentGoal.description.get());
          int selectedProgressOption = Arrays.asList(GoalModel.dialogProgressOptionsValues).indexOf(currentGoal.progress.get());
          dialog.pRadioGroup.setSelectedItem(selectedProgressOption);
          dialog.setListener(new ComponentUIDialog.DialogClickListener() {
            @Override
            public void onResolve() {
              GoalModel.GoalProgress selectedProgressOption = Arrays.asList(GoalModel.dialogProgressOptionsValues).get(dialog.pRadioGroup.getSelectedItemIndex());
              currentGoal.name.set(dialog.pNameField.getText());
              currentGoal.description.set(dialog.pDescriptionField.getText());
              currentGoal.progress.set(selectedProgressOption);
              currentGoal.save();

              setDescriptionLabel(currentGoal.description.get());
              setAppBarLabel(currentGoal.name.get(), currentGoal.progressToString());
            }

            @Override
            public void onReject() {

            }
          });
          dialog.show();
        }

        @Override
        public void onLongClick(int btnId) {

        }
      });
    }

    ComponentUIDialog initDialog(int nameMaxLength, int descriptionLength){
        ComponentUIDialog dialog = new ComponentUIDialog(this);
        dialog.pNameLabel.show();
        dialog.pNameLabel.setText(GoalLocale.getLocalizationByKey(GoalLocale.Keys.goalName));

        dialog.pNameField.setText("");
        dialog.pNameField.setMaxLength(nameMaxLength);
        dialog.pNameField.show();

        dialog.pDescriptionLabel.show();
        dialog.pDescriptionLabel.setText(GoalLocale.getLocalizationByKey(GoalLocale.Keys.shortDescription));

        dialog.pDescriptionField.setText("");
        dialog.pDescriptionLabel.setMaxLength(descriptionLength);
        dialog.pDescriptionField.show();

        dialog.pRadioGroup.setList(Arrays.asList(GoalModel.dialogProgressOptionsTitles));
        dialog.pRadioGroup.show();

        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_goal);
        String goalId = getIntent().getStringExtra(INTENT_GOAL_ID);
        mn = getSupportFragmentManager();
        repository = GlobalApplication.getAppDB().goalRepository;
        currentGoal = repository.getRecord(goalId);
        dialog = initDialog(
          repository.getNameLength(),
          repository.getDescriptionLength()
        );

        setAppBarLabel(currentGoal.name.get(), currentGoal.progressToString());
        setDescriptionLabel(currentGoal.description.get());
        initUpdateButton();
    }
}