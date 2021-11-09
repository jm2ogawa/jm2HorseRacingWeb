package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.MBettingTickets;
import com.example.demo.form.MainteTicketResponseForm;
import com.example.demo.service.MBettingTicketsService;
/**
 * メンテナンス機能 Controller
 */
@Controller
public class MaintenanceController {

	/**
	 * ロガー
	 */
//	private static final Logger logger = LoggerFactory.getLogger(MaintenanceController.class);

	/**
	 * HTTPセッション
	 */
	@Autowired
	HttpSession session;

	/**
	 * 券種マスタ Service
	 */
	@Autowired
	MBettingTicketsService mbtService;;
	/**
	 * 券種マスタ管理画面を表示
	 * @param model Model
	 * @return 券種マスタ管理画面
	 */
	@GetMapping(value = "/maintenance/ticket")
	public String ticketsDisplaySearch(Model model) {
		MainteTicketResponseForm responseForm = new MainteTicketResponseForm();
		try {
			List<MBettingTickets> mbtList = mbtService.searchAll();
			responseForm.setMbtList(mbtList);
			responseForm.setMessage("券種マスタを読み込みました");
		} catch (MyBatisSystemException mbse) {
			mbse.printStackTrace();
			responseForm.setMessage("データベース接続エラー");
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			responseForm.setMessage("券種マスタ読み込みエラー");
		} catch (Exception e) {
			e.printStackTrace();
			responseForm.setMessage("[致命的なエラーが発生しました]直ちにシステム管理者に報告して下さい");
		}
		model.addAttribute("res", responseForm);
		session.setAttribute("sesKeyMainteTicketResForm", responseForm);
		return "maintenance/ticket";
	}
	/**
	 * 券種マスタ一覧検索
	 * @param model Model
	 * @return 券種マスタ検索画面
	 */
	@RequestMapping(value = "/master/all_search", method = RequestMethod.POST)
	public String searchAll(Model model) {
	  List<MBettingTickets> mBettingTicket = mbtService.searchAll();
	  model.addAttribute("mBettingTicketsInfo", mBettingTicket);
	  return "master/search";
	}
}
