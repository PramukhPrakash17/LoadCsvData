package com.pramukh.loaddatatodb.Repository;

import com.pramukh.loaddatatodb.Model.LoadDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadDataRepository extends JpaRepository<LoadDataModel,Integer> {

}
