package edu.sjsu.cmpe275.termproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.sjsu.cmpe275.termproject.model.Keyword;;

public interface KeywordRepository extends JpaRepository<Keyword,Integer>{

	  @Query("select k from Keyword k where k.keyword = ?1")
	  List<Keyword> findKeyword(String keyword);
	  
}
