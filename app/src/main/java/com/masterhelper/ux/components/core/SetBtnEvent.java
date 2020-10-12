package com.masterhelper.ux.components.core;

public interface SetBtnEvent {
  /** click callback for short click event
   * @param btnId - element unique id that fired event
   * */
  void onClick(int btnId, String tag);
  /** click callback for long click event
   * @param btnId - element unique id that fired event
   * */
  void onLongClick(int btnId);
}
