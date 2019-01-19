package edu.sjsu.cmpe275.termproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.termproject.DAO.DAO;
import edu.sjsu.cmpe275.termproject.model.Keyword;

@Service
public class KeywordServiceImpl implements KeywordService{

	@Autowired
	DAO dao;
	
	@Override
	public void addKeyword(Keyword keyword) {
		dao.addKeyword(keyword);
		
	}

	@Override
	public void deleteKeyword(Integer bookId) {
		// TODO Auto-generated method stub
		dao.removeKeyword(bookId);
		
		
	}

	@Override
	public List<Keyword> getBookIds(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateKeyword(Keyword keyword) {
		// TODO Auto-generated method stub
		dao.updateKeyword(keyword);
	}

	/*@Override
	public List<Keyword> getBookIds(String keyword) {
		List<Keyword> keywords = dao.findBooksWithKeyword(keyword);
		return keywords;
	}*/

}
