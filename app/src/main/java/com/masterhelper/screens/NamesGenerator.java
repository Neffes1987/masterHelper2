package com.masterhelper.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.NonNull;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.masterhelper.R;
import com.masterhelper.ux.ApplyButtonFragment;
import com.masterhelper.ux.ContextPopupMenuBuilder;
import com.masterhelper.ux.list.ListAdapter;
import com.masterhelper.ux.list.ListFragment;
import com.masterhelper.ux.list.propertyBar.PropertyBarContentModel;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class NamesGenerator extends CommonScreen {
  public static final String INTENT_NAMES_GENERATOR_NAME_VALUE = "INTENT_NAMES_GENERATOR_NAME_VALUE";
  public static final String INTENT_NAMES_GENERATOR_NAME_VALUE_LENGTH = "INTENT_NAMES_GENERATOR_NAME_VALUE_LENGTH";

  private final String API_URL = "https://plarium.com/services/api/nicknames/new/create";
  private final Integer[] toolbarButtons = new Integer[]{
    R.mipmap.done
  };

  private EditText nameField;
  private LinearLayout filtersBlock;
  private ContextPopupMenuBuilder builder;
  private ListAdapter namesAdapter;
  private ArrayList<PropertyBarContentModel> listItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_names_generator);

    nameField = findViewById(R.id.NAMES_GENERATOR_INPUT_FIELD_ID);
    nameField.setText(getIntent().getStringExtra(INTENT_NAMES_GENERATOR_NAME_VALUE));

    filtersBlock = findViewById(R.id.NAMES_GENERATOR_FILTERS_BLOCK_ID);
    ImageButton filtersButton = findViewById(R.id.NAMES_GENERATOR_FILTERS_BTN_ID);
    filtersButton.setOnClickListener(v -> {
      View view = this.getCurrentFocus();
      if (view != null) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      }

      filtersBlock.setVisibility(filtersBlock.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    });

    addContextMenuItems(toolbarButtons, true);

    setActionBarTitle(R.string.names_generator_title);
    showBackButton(true);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
    int maxTextLength = getIntent().getIntExtra(INTENT_NAMES_GENERATOR_NAME_VALUE_LENGTH, 0);
    String content = nameField.getText().toString();

    if (maxTextLength < content.length()) {
      Toast.makeText(this, R.string.max_name_length, Toast.LENGTH_LONG).show();

      return true;
    }

    if (0 == content.length()) {
      Toast.makeText(this, R.string.min_content_length, Toast.LENGTH_LONG).show();

      return true;
    }

    Intent result = new Intent();

    result.putExtra(INTENT_NAMES_GENERATOR_NAME_VALUE, content);
    setResult(RESULT_OK, result);
    finish();

    return true;
  }

  @Override
  protected void onInitScreen() {
    builder = new ContextPopupMenuBuilder(new int[]{
      R.string.apply
    });

    builder.setPopupMenuClickHandler((tag, menuItemIndex) -> {
      nameField.setText(listItems.get(Integer.parseInt(tag)).getTitle());

      return true;
    });

    ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.NAMES_GENERATOR_LIST_ID);
    assert listFragment != null;

    ApplyButtonFragment applyButtonFragment = (ApplyButtonFragment) getSupportFragmentManager().findFragmentById(R.id.NAMES_GENERATOR_APPLY_FILTERS_BTN_ID);
    if (applyButtonFragment != null) {
      applyButtonFragment.setListener(v -> {
        int groupNumber = 0;

        RadioGroup groups = findViewById(R.id.NAMES_GENERATOR_GROUPS_ID);
        switch (groups.getCheckedRadioButtonId()) {
          case R.id.NAMES_GENERATOR_GROUP_NICKS_ID:
            groupNumber = 2;
            break;
          case R.id.NAMES_GENERATOR_GROUP_FANTASY_ID:
            groupNumber = 6;
            break;
          case R.id.NAMES_GENERATOR_GROUP_ELFS_ID:
            groupNumber = 7;
            break;
          case R.id.NAMES_GENERATOR_GROUP_DND_ID:
            groupNumber = 8;
            break;
          case R.id.NAMES_GENERATOR_GROUP_PLAYERS_ID:
            groupNumber = 5;
            break;
          case R.id.NAMES_GENERATOR_GROUP_CLANS_ID:
            groupNumber = 9;
            break;
          case R.id.NAMES_GENERATOR_GROUP_HEROS_ID:
            groupNumber = 3;
            break;
          case R.id.NAMES_GENERATOR_GROUP_RPG_ID:
            groupNumber = 1;
            break;
        }

        RadioGroup gender = findViewById(R.id.NAMES_GENERATOR_GENDER_ID);
        int genderNumber = gender.getCheckedRadioButtonId() == R.id.NAMES_GENERATOR_GENDER_MALE_ID ? 0 : 1;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
          Request.Method.POST,
          API_URL + "?group=" + groupNumber + "&gender=" + genderNumber,
          new JSONArray(),
          response -> {
            listItems = new ArrayList<>();

            for (int itemIndex = 0; itemIndex < response.length(); itemIndex++) {
              try {
                listItems.add(new PropertyBarContentModel(itemIndex + "", response.getString(itemIndex)));
              } catch (JSONException ignored) {
              }
            }

            namesAdapter = new ListAdapter(builder);
            namesAdapter.setValues(listItems);
            listFragment.setAdapter(namesAdapter);
          },
          error -> Toast.makeText(this, R.string.names_generator_api_error, Toast.LENGTH_LONG).show()
        );

        requestQueue.add(jsonObjectRequest);

        filtersBlock.setVisibility(View.GONE);
      });
    }
  }

  public static String getIntentTextEditValue(Intent data) {
    if (data == null) {
      return "";
    }

    return data.getStringExtra(INTENT_NAMES_GENERATOR_NAME_VALUE);
  }

  public static Intent getIntent(Context context, String name, int maxLength) {
    Intent intent = new Intent(context, NamesGenerator.class);
    intent.putExtra(INTENT_NAMES_GENERATOR_NAME_VALUE, name);
    intent.putExtra(INTENT_NAMES_GENERATOR_NAME_VALUE_LENGTH, maxLength);

    return intent;
  }
}