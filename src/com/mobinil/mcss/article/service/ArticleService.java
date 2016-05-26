package com.mobinil.mcss.article.service;

import com.mobinil.mcss.article.model.Article;
import java.util.List;



public interface ArticleService {

	public void addArticle(Article article);

	public List<Article> listArticles();
}