package com.masterhelper.ux.components.core;

public interface SetBtnLocation {
  /** click callback for short click event
   * @param btnId - element unique id that fired event
   * @param tag - element unique tag for searching in list
   * */
  void onClick(int btnId, String tag);
  /** click callback for long click event
   * @param btnId - element unique id that fired event
   * */
  void onLongClick(int btnId);
}
