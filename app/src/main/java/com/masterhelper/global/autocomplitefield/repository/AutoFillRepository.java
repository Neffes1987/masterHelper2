package com.masterhelper.global.autocomplitefield.repository;

import com.masterhelper.global.GlobalApplication;
import com.masterhelper.locations.repository.LocationModel;
import com.masterhelper.locations.repository.LocationRepository;
import com.masterhelper.npc.repository.NPCModel;
import com.masterhelper.npc.repository.NPCRepository;

import java.util.HashMap;

public class AutoFillRepository {
  static HashMap<String, String> cache = new HashMap<>();
  static String lastQuery = "";

  static HashMap<String, String> getLocationList(String searchStr) {
    LocationRepository locationRepository = GlobalApplication.getAppDB().locationRepository;
    return locationRepository.getDropdownList(searchStr);
  }

  static HashMap<String, String> getNPCList(String searchStr) {
    NPCRepository locationRepository = GlobalApplication.getAppDB().npcRepository;
    return locationRepository.getDropdownList(searchStr);
  }

  public static HashMap<String, String> getAutofillList(String searchStr, int threshold) {
    if (searchStr == null || searchStr.length() == 0 || searchStr.length() < threshold) {
      return cache;
    }

    String bdSearchRequest = searchStr.substring(0, threshold);

    if (lastQuery.equalsIgnoreCase(bdSearchRequest)) {
      return cache;
    }

    lastQuery = bdSearchRequest;
    cache = new HashMap<>();
    cache.putAll(getLocationList(bdSearchRequest));
    cache.putAll(getNPCList(bdSearchRequest));
    return cache;
  }

  public static NPCModel getNPCDataById(String id) {
    NPCRepository locationRepository = GlobalApplication.getAppDB().npcRepository;
    return locationRepository.getRecord(id);
  }

  public static LocationModel getLocationDataById(String id) {
    LocationRepository locationRepository = GlobalApplication.getAppDB().locationRepository;
    return locationRepository.getRecord(id);
  }
}
