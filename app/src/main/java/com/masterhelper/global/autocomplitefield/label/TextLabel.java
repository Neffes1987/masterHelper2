package com.masterhelper.global.autocomplitefield.label;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.klinker.android.link_builder.TouchableMovementMethod;
import com.masterhelper.global.autocomplitefield.editField.MultiAutofillEditTextField;
import com.masterhelper.global.autocomplitefield.repository.AutoFillRepository;
import com.masterhelper.locations.PageControlsListener;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.npc.NPCPage;
import com.masterhelper.npc.repository.NPCModel;
import com.masterhelper.ux.resources.ResourceColors;

import java.util.ArrayList;
import java.util.List;

public class TextLabel {
  TextView label;
  int MIM_IMAGE_WIDTH = 500;
  int MIM_IMAGE_HEIGHT = 500;
  int MAX_IMAGE_HEIGHT = 500;

  final int resourceColor = ResourceColors.getColor(ResourceColors.ResourceColorType.musicStarted);

  public TextLabel(int textFieldId, Activity context) {
    label = context.findViewById(textFieldId);
  }

  public void hide() {
    label.setVisibility(View.GONE);
  }

  public void show() {
    label.setVisibility(View.VISIBLE);
  }

  public void setLabel(String text, boolean hasTokens) {
    if (!hasTokens) {
      label.setText(text);
      return;
    }
    setInteractiveText(text, label);
  }

  String getNPCDescription(NPCModel npc) {
    String npcDescription = "";
    npcDescription += "\r\n\r\n" + npc.getAgeLocalesString();
    npcDescription += "\r\n\r\n" + npc.getRelationLocalesString();
    npcDescription += "\r\n\r\n" + npc.getGoalLocalesString();
    npcDescription += "\r\n\r\n" + npc.getCharacterLocalesString();
    return npcDescription;
  }

  void setInteractiveText(String labelText, TextView label) {
    ArrayList<String> tokensList = MultiAutofillEditTextField.findTokensText(labelText);
    ArrayList<String> tokensNamesList = MultiAutofillEditTextField.findTokensNames(labelText);
    List<Link> links = new ArrayList<>();
    for (String tokensName : tokensNamesList) {
      int previewNameIndex = tokensNamesList.indexOf(tokensName);

      labelText = labelText.replace(tokensList.get(previewNameIndex), tokensName);
      label.setText(labelText);

      Link link = new Link(tokensName)
        .setTextColor(resourceColor)
        .setTextColorOfHighlightedLink(resourceColor)
        .setUnderlined(false)
        .setBold(true)
        .setOnClickListener(clickedText -> {
          int clickedIndex = tokensNamesList.indexOf(clickedText);
          String tokenId = MultiAutofillEditTextField.getTokenId(tokensList.get(clickedIndex));

          NPCModel npc = AutoFillRepository.getNPCDataById(tokenId);
          if (npc != null && npc.id.toString().equals(tokenId)) {
            Intent npcPage = NPCPage.getIntent((Activity) label.getContext(), tokenId);
            getTokenPopup(npc.name.get(), npc.previewUrl.get(), getNPCDescription(npc), npcPage);
            return;
          }

          LocationModel location = AutoFillRepository.getLocationDataById(tokenId);
          if (location != null && location.id.toString().equals(tokenId)) {
            Intent locationIntent = PageControlsListener.getIntent((Activity) label.getContext(), tokenId);
            getTokenPopup(location.name.get(), location.previewUrl.get(), location.description.get(), locationIntent);
          }

        });
      links.add(link);
    }
    if (links.size() > 0) {
      LinkBuilder.on(label).addLinks(links).build();
      label.setMovementMethod(TouchableMovementMethod.getInstance());
    } else {
      label.setText(labelText);
    }
  }


  void getTokenPopup(String tokenName, String tokenPreviewUrl, String description, Intent tokenIntent) {
    Context context = label.getContext();
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(tokenName);

    LinearLayout linearLayout = new LinearLayout(context);
    ScrollView view = new ScrollView(context);

    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.MATCH_PARENT);
    linearLayout.setLayoutParams(layoutParams);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    linearLayout.setPaddingRelative(4, 4, 4, 4);
    linearLayout.setVerticalScrollBarEnabled(true);
    view.addView(linearLayout);
    builder.setView(view);

    if (tokenPreviewUrl != null && tokenPreviewUrl.length() > 0) {
      ImageView preview = new ImageView(context);
      preview.setImageURI(Uri.parse(tokenPreviewUrl));
      preview.setScaleType(ImageView.ScaleType.FIT_CENTER);
      preview.setMinimumWidth(MIM_IMAGE_WIDTH);
      preview.setMinimumHeight(MIM_IMAGE_HEIGHT);
      preview.setMaxHeight(MAX_IMAGE_HEIGHT);
      linearLayout.addView(preview);
    }

    if (description != null && description.length() > 0) {
      TextView descriptionField = new TextView(context);
      descriptionField.setText(description);
      linearLayout.addView(descriptionField);
    }

    if (tokenIntent != null) {
      builder.setPositiveButton("Open", (dialog, which) -> {
        context.startActivity(tokenIntent);
      });
    }
    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
    builder.create().show();
  }
}