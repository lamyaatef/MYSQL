package com.mobinil.mcss.article.service;

import com.mobinil.mcss.article.dao.ArticleDao;
import java.util.List;


import com.mobinil.mcss.article.model.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("articleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public ArticleServiceImpl() {
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addArticle(Article article) {
		articleDao.saveArticle(article);
	}

	public List<Article> listArticles() {
		return articleDao.listArticles();
	}

}