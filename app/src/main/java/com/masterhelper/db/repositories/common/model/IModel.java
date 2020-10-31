package com.masterhelper.db.repositories.common.model;

import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;

public interface IModel<Repository> {
  DataID id = new DataID();
  GeneralField<String> name = new GeneralField<>();
  GeneralField<String> description = new GeneralField<>();
  void save();
}