package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.form.MenuResponseForm;
import com.example.demo.service.MenuService;

/**
 * メニュー機能コントローラ
 */
@Controller
public class MenuController {

	/**
	 * ロガー
	 */
//	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	/**
	 * HTTPセッション
	 */
	@Autowired
	HttpSession session;

	/**
	 * メニュー機能サービス
	 */
	@Autowired
	MenuService menuService;

	/**
	 * 購入情報一覧画面を表示
	 * @param model Model
	 * @return 購入情報一覧画面
	 */
	@GetMapping(value = "/menu")
	public String displaySearch(Model model) {

//		MenuResponseForm sessionForm = (MenuResponseForm)session.getAttribute("sessionKeyMenuResponseForm");
		MenuResponseForm responseForm = menuService.createMenuResponse();
		session.setAttribute("sessionKeyMenuResponseForm", responseForm);
		model.addAttribute("responseForm", responseForm);
		return "menu";
	}

}
