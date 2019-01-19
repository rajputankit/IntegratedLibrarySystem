package edu.sjsu.cmpe275.termproject.service;

import java.util.List;

import edu.sjsu.cmpe275.termproject.model.Keyword;

public interface KeywordService {

	void addKeyword(Keyword keyword);
	void updateKeyword(Keyword keyword);
	void deleteKeyword(Integer bookId);
	List<Keyword> getBookIds(String keyword);
}
