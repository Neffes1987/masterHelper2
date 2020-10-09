package com.masterhelper.ux.pages.journeys;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import com.masterhelper.ux.components.core.SetBtnEvent;
import com.masterhelper.ux.components.library.buttons.icon.ComponentUIImageButton;
import com.masterhelper.ux.components.library.list.CommonItem;
import com.masterhelper.ux.components.library.text.label.ComponentUILabel;
import com.masterhelper.ux.resources.ResourceIcons;

/**  */
public class ListItemJourney extends CommonItem<TestUIListDataItem> implements SetBtnEvent {
  public boolean isInitiated = false;

  private View pLayout;
  private int pListItemId;
  private FragmentManager pManager;

  public ComponentUILabel name;
  private TestUIListDataItem defaultData;

  public ComponentUIImageButton editButton;
  public ComponentUIImageButton deleteButton;

  public ListItemJourney(FragmentManager manager) {
    setManager(manager);
  }

  public ListItemJourney(View view, FragmentManager manager) {
    pLayout = view.findViewById(CommonItem.TEMPLATE_HEADER_ID);
    pLayout.setId(this.generateViewId());
    setManager(manager);
    init();
  }

  private void setManager(FragmentManager pManager) {
    this.pManager = pManager;
  }

  @Override
  protected void update(TestUIListDataItem itemData, int listItemId) {
    if(name.controls == null){
      defaultData = itemData;
    } else {
      defaultData = null;
      name.controls.setText(itemData.getText());
    }
    pListItemId = listItemId;
  }

  private void initName(){
    name = new ComponentUILabel();
    attachFragment(this, name, pLayout, pManager);
  }

  private void initEditBtn(){
    editButton = new ComponentUIImageButton();
    attachFragment(this, editButton, pLayout, pManager);
  }

  private void initDeleteBtn(){
    deleteButton = new ComponentUIImageButton();
    attachFragment(this, deleteButton, pLayout, pManager);
  }

  protected void init(){
    if(isInitiated){
      return ;
    }
    isInitiated = true;
    initName();
    initEditBtn();
    initDeleteBtn();
  }

  @Override
  public CommonItem<TestUIListDataItem> clone(View view) {
    return new ListItemJourney(view, pManager);
  }

  @Override
  public void onFragmentAttached(String tag) {
    if (this.checkEqual(name, tag)) {
      if (defaultData != null) {
        name.controls.setText(defaultData.getText());
        name.setLayoutWeight(1);
      }
      return;
    }
    if(this.checkEqual(deleteButton, tag)){
      deleteButton.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.clear));
      deleteButton.controls.setOnClick(this);
    }
    if(this.checkEqual(editButton, tag)){
      editButton.controls.setId(View.generateViewId());
      editButton.controls.setOnClick(this);
    }
  }

  /**
   * click callback for short click event
   *
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onClick(int btnId) {
    Log.i("TAG", "onClick: " + btnId);
    Log.i("TAG", "onClick: deleteButton " + deleteButton.controls.getId());
    Log.i("TAG", "onClick: editButton " + editButton.controls.getId());
    if(deleteButton.controls.getId() == btnId){
      Toast.makeText(pLayout.getContext(), "delete", Toast.LENGTH_SHORT).show();
      return;
    }
    if(editButton.controls.getId() == btnId){
      Toast.makeText(pLayout.getContext(), "edit", Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * click callback for long click event
   *
   * @param btnId - element unique id that fired event
   */
  @Override
  public void onLongClick(int btnId) {

  }
}