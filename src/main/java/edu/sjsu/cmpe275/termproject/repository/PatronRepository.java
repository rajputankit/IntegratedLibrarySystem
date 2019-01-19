package edu.sjsu.cmpe275.termproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.termproject.model.Patron;

@Repository
public interface PatronRepository extends JpaRepository<Patron,String> {
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Modifying
	@Query("update Patron p set p.verified = 1 where p.email = ?1")
	void setPatronVerifiedFlag(String email);
	
	@Query("select p from Patron p where p.sjsuId = ?1")
	Patron findPatronBySjsuId(String sjsuId);
}
