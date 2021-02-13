package com.masterhelper.global.db.repositories.common.repositories;

import com.masterhelper.global.fields.DataID;
import com.masterhelper.global.fields.GeneralField;
import com.masterhelper.global.db.repositories.common.model.GeneralModel;
import com.masterhelper.global.db.repositories.common.contracts.AbstractContract;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<Model extends GeneralModel> implements IRepository<Model> {
  private final ArrayList<Model> mRecordsList;
  private final AbstractContract<Model> contract;
  public GeneralField<String> repositoryName = new GeneralField<>();

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
    if(updatedElement.type.get() != this.repositoryName.get()){
      throw new Error("BaseRepository:update - updated element does not exist in repository");
    }
    int recordIndex = mRecordsList.indexOf(updatedElement);

    if(recordIndex > -1){
      mRecordsList.add(recordIndex, updatedElement);
      contract.updateRecord(updatedElement);
    } else {
      mRecordsList.add(0, updatedElement);
      contract.insertRecord(updatedElement);
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
  public void setItemToCache(Model record, int offset) {
    mRecordsList.add(offset, record);
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

  @Override
  public void createTable() {
    getContract().createTable();
  }
}
