package edu.sjsu.cmpe275.termproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sjsu.cmpe275.termproject.model.SystemDate;

public interface DateRepository  extends JpaRepository<SystemDate,Integer>{

}
