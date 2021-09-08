package com.masterhelper.ux;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.masterhelper.R;

public class PropertyBarFragment extends Fragment {
  String[] menuItems;
  int tag;
  protected OnPropertyBarClick listener;
  TextView labelView;
  TextView titleView;
  TextView descriptionView;
  ImageView previewView;
  ImageButton editButtonView;
  CardView cardView;

  private final View.OnClickListener onTapOnCardListener = v -> {
    ContextPopupMenu menu = new ContextPopupMenu(v.getContext(), editButtonView);

    for (String title : this.menuItems) {
      menu.popupMenu.getMenu().add(title);
    }

    menu.setPopupMenuClickHandler(item -> {
      if (listener != null) {
        listener.onClick(this.tag, item.getTitle());
      }
      return false;
    });

    menu.show();
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View fragment = inflater.inflate(R.layout.fragment_property_bar, container, false);

    titleView = fragment.findViewById(R.id.PROPERTY_TITLE_ID);
    descriptionView = fragment.findViewById(R.id.PROPERTY_DESCRIPTION_ID);
    previewView = fragment.findViewById(R.id.PROPERTY_PREVIEW_ID);
    editButtonView = fragment.findViewById(R.id.PROPERTY_EDIT_BTN_ID);
    labelView = fragment.findViewById(R.id.PROPERTY_LABEL_ID);
    cardView = fragment.findViewById(R.id.PROPERTY_CARD_ID);
    cardView.setOnClickListener(onTapOnCardListener);

    setDescription("");
    setTitle("");
    setLabel(0, CardStatus.Active);
    setImage(null);

    return fragment;
  }

  private void setTextColor(CardStatus status, TextView view) {
    switch (status) {
      case Success:
        view.setTextColor(getResources().getColor(R.color.neutralGreen));
        break;
      case Danger:
        view.setTextColor(getResources().getColor(R.color.neutralRed));
        break;
      case Attention:
        view.setTextColor(getResources().getColor(R.color.neutralYellow));
        break;
      case Disabled:
        view.setTextColor(getResources().getColor(R.color.accentGray));
      case Active:
      default:
        view.setTextColor(getResources().getColor(R.color.accentDarkBlue));
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

  private void toggleViewVisibility(View view, Boolean isVisible) {
    if (isVisible) {
      view.setVisibility(View.VISIBLE);
    } else {
      view.setVisibility(View.GONE);
    }
  }

  public void setOnPropertyBarClick(OnPropertyBarClick listener, int tag, String[] menuItems) {
    this.tag = tag;
    this.menuItems = menuItems;
    this.listener = listener;
  }

  public interface OnPropertyBarClick {
    void onClick(int tag, CharSequence menuItemName);
  }

  public enum CardStatus {
    Active,
    Attention,
    Danger,
    Success,
    Disabled
  }
}