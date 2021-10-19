package com.masterhelper.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chinalwb.are.AREditText;
import com.chinalwb.are.Constants;
import com.chinalwb.are.styles.toolbar.IARE_Toolbar;
import com.chinalwb.are.styles.toolitems.*;
import com.masterhelper.R;
import org.jetbrains.annotations.NotNull;

public class TextEditor extends CommonScreen {
  public static final String INTENT_TEXT_EDIT_TITLE = "INTENT_TEXT_EDIT_TITLE";
  public static final String INTENT_TEXT_EDIT_VALUE = "INTENT_TEXT_EDIT_VALUE";
  public static final String INTENT_TEXT_EDIT_MAX_VALUE_LENGTH = "INTENT_TEXT_EDIT_MAX_VALUE_LENGTH";

  private final Integer[] toolbarButtons = new Integer[]{
    R.mipmap.done
  };

  private AREditText arEditor;

  private IARE_Toolbar getToolbar() {
    IARE_Toolbar mToolbar = this.findViewById(R.id.EDIT_SCREEN_CONTROL_BAR_ID);
    IARE_ToolItem[] controls = new IARE_ToolItem[]{
      new ARE_ToolItem_Bold(),
      new ARE_ToolItem_Italic(),
      new ARE_ToolItem_Underline(),
      new ARE_ToolItem_Strikethrough(),
      new ARE_ToolItem_Quote(),
      new ARE_ToolItem_ListNumber(),
      new ARE_ToolItem_ListBullet(),
      new ARE_ToolItem_Hr(),
      new ARE_ToolItem_Link(),
      new ARE_ToolItem_AlignmentLeft(),
      new ARE_ToolItem_AlignmentCenter(),
      new ARE_ToolItem_AlignmentRight(),
      new ARE_ToolItem_Image(),
      new ARE_ToolItem_At()
    };

    for (IARE_ToolItem control : controls) {
      control.setToolItemUpdater(new ARE_ToolItem_UpdaterDefault(control, getColor(R.color.neutralYellow), Constants.UNCHECKED_COLOR));
      mToolbar.addToolbarItem(control);
    }

    return mToolbar;
  }

  private void initEditor() {
    arEditor = this.findViewById(R.id.EDIT_SCREEN_HTML_EDITOR);

    arEditor.fromHtml(getIntent().getStringExtra(INTENT_TEXT_EDIT_VALUE));

    arEditor.setToolbar(getToolbar());
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_text_editor);

    initEditor();

    showBackButton(true);
    setTitle(getIntent().getStringExtra(INTENT_TEXT_EDIT_TITLE));

    addContextMenuItems(toolbarButtons, true);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
    int maxTextLength = getIntent().getIntExtra(INTENT_TEXT_EDIT_MAX_VALUE_LENGTH, 0);
    String content = arEditor.getHtml();

    if (maxTextLength < content.length()) {
      Toast.makeText(this, R.string.max_content_length, Toast.LENGTH_LONG).show();

      return true;
    } else if (content.length() == 0) {
      Toast.makeText(this, R.string.min_content_length, Toast.LENGTH_LONG).show();

      return true;
    }

    Intent result = new Intent();

    result.putExtra(INTENT_TEXT_EDIT_VALUE, content);
    setResult(RESULT_OK, result);
    finish();

    return true;
  }

  @Override
  protected void onInitScreen() {
  }

  public static String getIntentTextEditValue(@Nullable Intent intent) {
    if (intent == null) {
      return "";
    }

    return intent.getStringExtra(INTENT_TEXT_EDIT_VALUE);
  }

  public static Intent getIntent(Context context, String title, String text, int length) {
    Intent intent = new Intent(context, TextEditor.class);
    intent.putExtra(INTENT_TEXT_EDIT_TITLE, title);
    intent.putExtra(INTENT_TEXT_EDIT_VALUE, text);
    intent.putExtra(INTENT_TEXT_EDIT_MAX_VALUE_LENGTH, length);

    return intent;
  }
}