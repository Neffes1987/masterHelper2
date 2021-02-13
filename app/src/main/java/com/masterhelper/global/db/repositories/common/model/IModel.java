package com.masterhelper.global.db.repositories.common.model;

import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.fields.GeneralField;

public interface IModel<Repository> {
  DataID id = new DataID();
  GeneralField<String> name = new GeneralField<>();
  GeneralField<String> description = new GeneralField<>();
  void save();
}
