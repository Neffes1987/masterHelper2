package com.masterhelper.ux.list.propertyBar;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.masterhelper.R;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import org.jetbrains.annotations.Nullable;

public class PropertyBar {
  private TextView labelView;
  private TextView titleView;
  private TextView descriptionView;
  private ImageView previewView;
  private ImageButton editButtonView;
  private CardView cardView;

  public PropertyBar(@Nullable Fragment fragment) {
    assert fragment != null;
    propertyBarInit(fragment.getView());
  }

  public PropertyBar(@Nullable View fragment) {
    propertyBarInit(fragment);
  }

  private void propertyBarInit(@Nullable View fragment) {
    assert fragment != null;

    titleView = fragment.findViewById(R.id.PROPERTY_TITLE_ID);
    descriptionView = fragment.findViewById(R.id.PROPERTY_DESCRIPTION_ID);
    previewView = fragment.findViewById(R.id.PROPERTY_PREVIEW_ID);
    editButtonView = fragment.findViewById(R.id.PROPERTY_EDIT_BTN_ID);
    labelView = fragment.findViewById(R.id.PROPERTY_LABEL_ID);
    cardView = fragment.findViewById(R.id.PROPERTY_CARD_ID);

    setDescription("");
    setTitle("");
    setLabel(0, CardStatus.Active);
    setImage(null);
    toggleViewVisibility(editButtonView, false);
  }

  public void setCardContextMenu(ContextPopupMenuBuilder popupMenuBuilder) {
    PopupMenu menu = popupMenuBuilder.create(cardView.getContext(), editButtonView);
    toggleViewVisibility(editButtonView, true);
    cardView.setOnClickListener(v -> {
      menu.show();
    });
  }

  public void setTextSettings(String title, CardStatus status, TextView titleView) {
    titleView.setText(title);
    setTextColor(status, titleView);
  }

  public void setLabel(int title) {
    setLabel(title, CardStatus.Active);
  }

  public void setLabel(int title, CardStatus status) {
    setTextSettings(title, status, labelView);
    toggleViewVisibility(labelView, labelView.length() > 0);
  }

  public void setTitle(String title, CardStatus status) {
    setTextSettings(title, status, titleView);
  }

  public void setTitle(String title) {
    setTitle(title, CardStatus.Active);
  }

  public void setDescription(String description) {
    toggleViewVisibility(descriptionView, description.length() > 0);
    setTextSettings(description, CardStatus.Active, descriptionView);
  }

  public void setImage(Uri imageUri) {
    previewView.setImageURI(imageUri);
    toggleViewVisibility(previewView, imageUri != null);
  }

  @SuppressLint("ResourceAsColor")
  private void setTextColor(CardStatus status, TextView view) {
    switch (status) {
      case Success:
        view.setTextColor(R.color.neutralGreen);
        break;
      case Danger:
        view.setTextColor(R.color.neutralRed);
        break;
      case Attention:
        view.setTextColor(R.color.neutralYellow);
        break;
      case Disabled:
        view.setTextColor(R.color.accentGray);
      case Active:
      default:
        view.setTextColor(R.color.accentDarkBlue);
    }
  }

  private void setTextSettings(int title, CardStatus status, TextView titleView) {
    if (title > 0) {
      titleView.setText(title);
    } else {
      titleView.setText("");
    }
    setTextColor(status, titleView);
  }

  private void toggleViewVisibility(View view, Boolean isVisible) {
    if (isVisible) {
      view.setVisibility(View.VISIBLE);
    } else {
      view.setVisibility(View.GONE);
    }
  }

  public enum CardStatus {
    Active,
    Attention,
    Danger,
    Success,
    Disabled
  }
}