package com.mobinil.mcss.article.dao;

import com.mobinil.mcss.article.model.Article;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;




import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("articleDao")
public class ArticleDaoImpl implements ArticleDao {

	@Autowired
	private SessionFactory sessionFactory;

	// To Save the article detail
    @Override
	public void saveArticle(Article article) {        
		article.setAddedDate(new Date());
//                System.out.println("1. "+sessionFactory);
//                System.out.println("1. "+sessionFactory.getCurrentSession());
                
                Session s = sessionFactory.openSession();
                s.saveOrUpdate(article);
                Transaction tx=s.beginTransaction();                
                tx.commit();
                s.close();
//		sessionFactory.getCurrentSession().saveOrUpdate(article);
	}
	
	// To get list of all articles
	@SuppressWarnings("unchecked")
         @Override
	public List<Article> listArticles() {		
            Session s = sessionFactory.openSession();
            List<Article> ll = (List<Article>) s.createCriteria(Article.class).list();
            s.close();
		return /*sessionFactory.getCurrentSession().createCriteria(Article.class).list()*/ll;
	}
}