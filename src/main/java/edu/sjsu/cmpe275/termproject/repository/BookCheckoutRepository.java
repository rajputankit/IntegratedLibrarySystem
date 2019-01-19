package edu.sjsu.cmpe275.termproject.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.termproject.model.Book;
import edu.sjsu.cmpe275.termproject.model.BookCheckout;

@Repository
public interface BookCheckoutRepository extends JpaRepository<BookCheckout, Integer> {
	
	@Query("select b from BookCheckout b where b.checkoutPatron.email = ?1")
	List<BookCheckout> findBooksCheckedoutByPatron(String patronEmail);
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Modifying
	@Query("delete from BookCheckout b where b.checkoutPatron.email = ?1 AND b.checkoutBook.bookId = ?2")
	void returnBook(String patronEmail, Integer bookId);

	@Query("select b from BookCheckout b where b.checkoutPatron.email = ?1 AND b.checkoutBook.bookId = ?2")
	BookCheckout getBookCheckout(String patronEmail, Integer bookId);

	@Query("SELECT b FROM BookCheckout b WHERE b.returnDate BETWEEN ?1 AND ?2")
	List<BookCheckout> getDueBooks(Date startDate, Date endDate);
	
	}
