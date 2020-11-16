package com.masterhelper.ux.components.library.buttons.floating;

import android.view.View;
import com.masterhelper.ux.resources.ResourceColors;
import com.masterhelper.ux.resources.ResourceIcons;

import static com.masterhelper.ux.resources.ResourceColors.ResourceColorType.primary;

public class FloatingButtonsPreset implements IFloatingButtonsPreset {
  public static void setPreset(Presets buttonPreset, ComponentUIFloatingButton button){
    switch (buttonPreset){
      case music:
        button.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.music));
        button.controls.setIconColor(primary);
        break;
      case addNewItem:
        button.controls.setIcon(ResourceIcons.getIcon(ResourceIcons.ResourceColorType.add));
        button.controls.setIconColor(ResourceColors.ResourceColorType.common);
        break;
      default:
        throw new Error("FloatingButtonsPreset: setPreset - wrong preset type");
    }
    button.controls.setId(View.generateViewId());
  }

  public enum Presets {
    music,
    addNewItem
  }
}
