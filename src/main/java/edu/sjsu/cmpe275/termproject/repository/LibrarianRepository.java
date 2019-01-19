package edu.sjsu.cmpe275.termproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.termproject.model.Librarian;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, String> {

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Modifying
	@Query("update Librarian l set l.verified = 1 where l.sjsuEmail = ?1")
	void setLibrarianVerifiedFlag(String email);	
	
	@Query("select l from Librarian l where l.sjsuId = ?1")
	Librarian findLibrarianBySjsuId(String sjsuId);	
}
