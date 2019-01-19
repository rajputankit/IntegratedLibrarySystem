package edu.sjsu.cmpe275.termproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.termproject.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

	@Query("select b from Book b where b.addedBy = ?1 or b.updatedBy = ?1")
	List<Book> findBookByLibrarian(String librarianEmail);
	
	@Query("select b from Book b where b.ISBN = ?1")
	Book findBookByISBN(String ISBN);	
	
	@Query("select b from Book b where b.currentStatus = 2")
	List<Book> getReservedBooks();	
	
}
