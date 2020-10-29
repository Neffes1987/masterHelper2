package com.masterhelper.baseclasses.repository;

import com.masterhelper.baseclasses.fields.DataID;
import com.masterhelper.baseclasses.fields.GeneralField;
import com.masterhelper.baseclasses.model.GeneralModel;
import com.masterhelper.db.contracts.common.AbstractContract;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<Model extends GeneralModel> implements IRepository<Model> {
  private final ArrayList<Model> mRecordsList;
  private final AbstractContract<Model> contract;
  protected GeneralField<String> repositoryName = new GeneralField<>();

  public AbstractContract<Model> getContract() {
    return contract;
  }

  public AbstractRepository(AbstractContract<Model> contract, String repositoryName){
    mRecordsList = new ArrayList<>();
    this.repositoryName.set(repositoryName);
    this.contract = contract;
  }

  @Override
  public Model getRecord(DataID elementId) {
    return findRecordById(elementId);
  }

  @Override
  public void removeRecord(DataID deletedElementId) {
    Model record = findRecordById(deletedElementId);
    if(record == null){
      return;
    }
    mRecordsList.remove(record);
    contract.deleteRecord(deletedElementId);
  }

  @Override
  public void saveRecord(Model updatedElement) {
    Model record = findRecordById(updatedElement.id);
    if(record.type.get() != this.repositoryName.get()){
      throw new Error("BaseRepository:update - updated element does not exist in repository");
    }
    int recordIndex = mRecordsList.indexOf(record);
    contract.updateRecord(updatedElement);
    if(recordIndex > -1){
      mRecordsList.add(recordIndex, record);
    } else {
      mRecordsList.add(0, record);
    }
  }

  public List<Model> getCacheList(int offset, int limit) {
    if(mRecordsList.size() > offset + limit + 1){
      ArrayList<Model> result = new ArrayList<>(mRecordsList);
      return result.subList(offset, limit + 1);
    }
    return null;
  }

  public void setItemsToCache(ArrayList<Model> records, int offset) {
    mRecordsList.addAll(offset, records);
  }

  public Model findRecordById(DataID recordId) {
    Model foundedRecord = null;
    for (Model record : mRecordsList) {
      if (record.id == recordId) {
        foundedRecord = record;
        break;
      }
    }
    return foundedRecord;
  }
}
