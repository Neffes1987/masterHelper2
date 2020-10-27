package com.masterhelper.db.contracts.journeys;

import com.masterhelper.db.contracts.BaseColumn;
import com.masterhelper.db.contracts.ContractsUtilities;
import com.masterhelper.db.contracts.GeneralContract;
import com.masterhelper.db.contracts.IContract;

public class JourneyContract implements IContract<JourneyModel> {
  private final String TABLE_NAME = "journeys";
  private final BaseColumn JourneyId = new BaseColumn("journeyId", BaseColumn.ColumnTypes.CharType, 200, false);
  private final BaseColumn JourneyTitle = new BaseColumn("journeyTitle", BaseColumn.ColumnTypes.CharType, 200, false);
  private final GeneralContract contract = new GeneralContract(TABLE_NAME, new BaseColumn[]{JourneyId, JourneyTitle});

  @Override
  public String createTable() {
    return ContractsUtilities.generateTableQuery(TABLE_NAME, contract.getInitialColumnsProps());
  }

  @Override
  public String insertRecord(JourneyModel record) {
    return ContractsUtilities.generateInsertQuery(TABLE_NAME, contract.getColumnsTitles(), new String[]{record.id.get().toString(), record.name.get()});
  }

  @Override
  public String updateRecord(JourneyModel record) {
    return null;
  }

  @Override
  public String deleteRecord(JourneyModel record) {
    return null;
  }
}
