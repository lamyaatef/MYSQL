package com.mobinil.mcss.article.controller;

import com.mobinil.mcss.article.model.Article;
import com.mobinil.mcss.article.service.ArticleService;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/articles")
public class ArticleController  {

	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public ModelAndView saveArticle(@ModelAttribute("article") Article  article,
			BindingResult result) {
		 articleService.addArticle( article);
		return new ModelAndView("redirect:/articles.htm");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listArticles() {
            System.out.println("here in list ");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("articles",  articleService.listArticles());

		return new ModelAndView("articlesList", model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addArticle(@ModelAttribute("article") Article article,
			BindingResult result) {
		return new ModelAndView("addArticle");
	}

}