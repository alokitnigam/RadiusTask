package com.example.radiustask.DI.database;



import com.example.radiustask.DI.Models.ExclusionCombinations;
import com.example.radiustask.DI.Models.Facility;

import java.util.List;

import io.realm.Realm;


public interface DbHelper {
    void addAllFacilities(List<Facility> facilities);
    void addAllExclusionCombinations(List<ExclusionCombinations> exclusionCombinations);
    LiveRealmData<Facility> getAllFacilities();
    LiveRealmData<ExclusionCombinations> getAllExclusionCombinations();

    void closeRealm();
    Realm getRealm();

    void deleteAllExclusionCombinations();
}
