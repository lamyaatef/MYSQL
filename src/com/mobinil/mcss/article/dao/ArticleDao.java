package com.mobinil.mcss.article.dao;

import com.mobinil.mcss.article.model.Article;
import java.util.Date;
import java.util.List;




public interface ArticleDao {
	// To Save the article detail
	public void saveArticle ( Article Article );
	
	// To get list of all articles
	public List<Article> listArticles();
}