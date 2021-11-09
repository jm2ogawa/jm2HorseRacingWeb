package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.utils.DateUtil;
import com.example.demo.dto.PurchaseTargetInfo;
import com.example.demo.form.dashboard.DashBoardCheckRequestForm;
import com.example.demo.form.dashboard.DashBoardResponseForm;
import com.example.demo.service.ConfigService;
import com.example.demo.service.DashBoardService;

/**
 * ダッシュボード機能 Controller
 */
@Controller
public class DashBoardController {

	/**
	 * ダッシュボード機能 Service
	 */
	@Autowired
	private DashBoardService dashBoardService;

	/**
	 * システム構成サービス
	 */
	@Autowired
	private ConfigService confService;

	/**
	 * HTTPセッション
	 */
	@Autowired
	HttpSession session;

	/**
	 * 購入情報一覧画面を表示
	 * @param model Model
	 * @return 購入情報一覧画面
	 */
	@GetMapping(value = "/dashboard/list")
	public String displaySearch(Model model) {

		List<PurchaseTargetInfo> purTgtInfoList = dashBoardService.list();
		//ディープコピーを取る
		List<PurchaseTargetInfo> popupList = dashBoardService.list();

		DashBoardResponseForm responseForm = (DashBoardResponseForm)session.getAttribute("sesKeyDashBoardListRes");
		if(Objects.isNull(responseForm)) {
			responseForm = new DashBoardResponseForm();
			if(Objects.isNull(responseForm.getPurTgtInfoList())) {
				responseForm.setPurTgtInfoList(purTgtInfoList);
			}
		}
		//
		DashBoardCheckRequestForm check = new DashBoardCheckRequestForm(
				DateUtil.createDayByFormatInYYYYMMDD(2021, 11, 6),"0");
		//今回の購入リストをソートしておく
		purTgtInfoList.sort(Comparator.comparing(PurchaseTargetInfo::getTcId));
		purTgtInfoList.sort(Comparator.comparing(PurchaseTargetInfo::getBettingTicketsCode));
		//前回の購入リストと今回の購入リストを比較
		for(PurchaseTargetInfo compInfo : responseForm.getPurTgtInfoList()) {
			for(PurchaseTargetInfo ptInfo : popupList) {
				//リストの中身が一致した場合
				if(ptInfo.getTsoId() == compInfo.getTsoId() && ptInfo.getTcId() == compInfo.getTcId()) {
					popupList.remove(ptInfo);
					break;
				}
			}
		}
		if(popupList.size() > 0) {
			StringBuilder sb = new StringBuilder("購入対象に");
			popupList.forEach(pti -> sb.append(
												"["
												+ pti.getBettingTicketsName()
												+ ":"
												+ pti.getConsecutiveName()
												+ "]"
												+ "\r\n"));
			sb.append("が追加されました。");
			responseForm.setPopupMessage(sb.toString());
			responseForm.setNewPurchases(popupList);
			responseForm.setPurTgtInfoList(purTgtInfoList);
			//新規追加の行番号をセット
			//減った場合も新規追加を確認
		}else {
			responseForm.setPopupMessage("");
			responseForm.setNewPurchases(new ArrayList<PurchaseTargetInfo>());
		}
		responseForm.setRaceday(check.getRaceday());
		responseForm.setRownum(dashBoardService.check(check));
		responseForm.setMessage("開始します");
		responseForm.setConfKeyTimerMode(confService.getConfigOfString("configKeyTimerMode"));
		session.setAttribute("sesKeyDashBoardListRes", responseForm);
		model.addAttribute("res", responseForm);
		return "dashboard/list";
	}

	/**
	 * レースデータ更新チェック
	 * @param model Model
	 * @return 現在進行中のレースデータ更新行
	 */
	@PostMapping(value = "/dashboard/check")
	@ResponseBody
	public String check(@RequestBody DashBoardCheckRequestForm requestForm) {
		String rownum = dashBoardService.check(requestForm);
		return rownum;
	}
}
