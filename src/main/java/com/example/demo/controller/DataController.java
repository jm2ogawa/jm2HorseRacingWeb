package com.example.demo.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.utils.ViewPartsUtil;
import com.example.demo.dto.Race;
import com.example.demo.form.DataRaceDisplayReaponseForm;
import com.example.demo.form.DataViewFixRequestForm;
import com.example.demo.form.DataViewRequestForm;
import com.example.demo.form.DataViewResponseForm;
import com.example.demo.service.CsvService;
import com.example.demo.service.DataService;

/**
 * データ更新機能 Controller
 */
@Controller
public class DataController {

	private static final String MESSAGE_ID_DATA_VIEW_FIX_SUCCESS = "レース結果を修正しました";

	private static final String MESSAGE_ID_DATA_VIEW_RESIST_SUCCESS = "レース結果を登録しました";

	/**
	 * ロガー
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataController.class);

	/**
	 * データ更新機能 Service
	 */
	@Autowired
	private DataService dataService;

	/**
	 * データ更新機能 Service
	 */
	@Autowired
	private CsvService csvService;

	/**
	 * HTTPセッション
	 */
	@Autowired
	HttpSession session;

	/**
	 * 現在値更新画面を表示
	 * @param model Model
	 * @return 現在値更新画面
	 */
	@GetMapping(value = "/data/view")
	public String displaySearch(Model model) {
		DataViewResponseForm responseForm = (DataViewResponseForm)session.getAttribute("sessionKeyDataViewResponseForm");
		if(Objects.isNull(responseForm)) {
			responseForm = new DataViewResponseForm();
			responseForm.setProgressRow(1);
		}
		List<Race> raceList = dataService.getDisplayContents();
		Map<String,String> acdPullDownMap = ViewPartsUtil.getAcdPulDown();
		int rowCnt = 0;
		int progressRowCnt = dataService.getRegisterdRows();
		//TODO 最新レースファイルを読み込んでいないと現在値更新画面は開けないようにしている
		if(raceList.size()==0) {
			responseForm.setMessage("データベースにあるレース情報を確認して下さい");
			session.setAttribute("sessionKeyDataViewResponseForm", responseForm);
			model.addAttribute("responseForm", responseForm);
		}else {
			//入力画面のフォームコントロールを先頭行から設定する
			for(Race race : raceList) {
				if(StringUtils.isEmpty(race.getWinner()) || StringUtils.equals(race.getWinner(), "")) {
					//レース結果がまだ未登録の行が来た場合、活性
					if(rowCnt == progressRowCnt) {
						race.setInput1FormStatus(false);
						race.setInput2FormStatus(false);
						race.setInput3FormStatus(false);
						race.setResisterButtonStatus(false);
						race.setFixButtonStatus(true);
						race.setReactCheckBox(false);
						race.setInputAcdStatus(false);
					}else {
						//レース結果が空の行は非活性にする
						race.setInput1FormStatus(true);
						race.setInput2FormStatus(true);
						race.setInput3FormStatus(true);
						race.setResisterButtonStatus(true);
						race.setFixButtonStatus(true);
						race.setReactCheckBox(false);
						race.setInputAcdStatus(true);
					}
				}else {
					//レース結果が既に登録されている行は非活性にする
					race.setInput1FormStatus(true);
					race.setInput2FormStatus(true);
					race.setInput3FormStatus(true);
					race.setResisterButtonStatus(true);
					race.setFixButtonStatus(true);
					race.setReactCheckBox(false);
					race.setInputAcdStatus(true);
				}
				rowCnt = rowCnt + 1;
			}
			//初回判定
			if(StringUtils.isEmpty(raceList.get(0).getWinner()) || StringUtils.equals(raceList.get(0).getWinner(), "")){
				//初回は先頭行を活性にする
				raceList.get(0).setInput1FormStatus(false);
				raceList.get(0).setInput2FormStatus(false);
				raceList.get(0).setInput3FormStatus(false);
				raceList.get(0).setResisterButtonStatus(false);
				raceList.get(0).setFixButtonStatus(true);
				raceList.get(0).setReactCheckBox(false);
				raceList.get(0).setInputAcdStatus(false);
			}
			responseForm.setAcdPullDownMap(acdPullDownMap);
			responseForm.setRaceList(raceList);
			responseForm.setMessage("レース結果を入力して下さい");
			session.setAttribute("sessionKeyDataViewResponseForm", responseForm);
			session.setAttribute("sessionKeyRaceList", raceList);
			session.setAttribute("sessionKeyAcdPulldown", acdPullDownMap);
			model.addAttribute("responseForm", responseForm);
		}
		return "data/view";
	}

	/**
	 * 指定のレースの人気により、対象のレースの現在値を更新する
	 * @param
	 * @return 現在値更新画面
	 */
	@PostMapping(value = "/data/register")
	@ResponseBody
	public String resister(@Validated @RequestBody DataViewRequestForm requestForm) {

		logger.info("[resister]RequestBody requestForm:" + requestForm.toString());
		dataService.resisterFacade(requestForm);

		String message = MESSAGE_ID_DATA_VIEW_RESIST_SUCCESS;
		DataViewResponseForm responseForm = (DataViewResponseForm)session.getAttribute("sessionKeyDataViewResponseForm");
		if(Objects.isNull(responseForm)) {
			responseForm = new DataViewResponseForm();
			responseForm.setRaceList(dataService.getDisplayContents());
			responseForm.setAcdPullDownMap(ViewPartsUtil.getAcdPulDown());
		}
		if(Objects.isNull(responseForm.getRaceList())) {
			responseForm.setRaceList((List<Race>)session.getAttribute("sessionKeyRaceList"));
		}
		if(Objects.isNull(responseForm.getAcdPullDownMap())) {
			responseForm.setAcdPullDownMap((Map<String,String>)session.getAttribute("sessionKeyAcdPulldown"));
		}
		List<Race> raceList = responseForm.getRaceList();
		int rownum = Integer.parseInt(requestForm.getRownum())-1;
		Race race = raceList.get(rownum);
		race.setInput1FormStatus(true);
		race.setInput2FormStatus(true);
		race.setInput3FormStatus(true);
		race.setResisterButtonStatus(true);
		race.setFixButtonStatus(false);
		race.setReactCheckBox(false);
		race.setInputAcdStatus(true);
		race.setRowColor("background-color: #888;");
		raceList.set(rownum, race);
		if(rownum < raceList.size()) {
			Race nextRace = raceList.get(rownum+1);
			nextRace.setInput1FormStatus(false);
			nextRace.setInput2FormStatus(false);
			nextRace.setInput3FormStatus(false);
			nextRace.setResisterButtonStatus(false);
			nextRace.setFixButtonStatus(true);
			race.setReactCheckBox(false);
			race.setInputAcdStatus(false);
			raceList.set(rownum+1, nextRace);
		} else {
			message =  "全てのレース結果を入力しました";
			responseForm.setMessage(message);
		}
		responseForm.setRaceList(raceList);
		responseForm.setProgressRow(rownum);
		responseForm.setAcdPullDownMap(ViewPartsUtil.getAcdPulDown());
		session.setAttribute("sessionKeyDataViewResponseForm", responseForm);
		session.setAttribute("sessionKeyRaceList", raceList);

		return message;
	}

	/**
	 * 入力されたレース結果の修正を行う
	 * データ登録画面で修正ボタンを押下した行を対象する
	 * 最新のレース確定行から順番に対象行まで巻き戻す
	 * @param
	 * @return 現在値更新画面
	 */
	@PostMapping(value = "/data/fix")
	@ResponseBody
	public String fix(@Validated @RequestBody DataViewFixRequestForm requestForm) {

		logger.info("[fix]RequestBody requestForm:" + requestForm.toString());
		dataService.fixFacade(requestForm);

		return MESSAGE_ID_DATA_VIEW_FIX_SUCCESS;
	}

	/**
	 * レースCSV管理画面を表示
	 * @param model Model
	 * @return レースファイル管理画面
	 */
	@GetMapping(value = "/data/race")
	public String raceDisplaySearch(Model model) {

		File[] files = csvService.findCsvFiles();
		DataRaceDisplayReaponseForm responseForm = new DataRaceDisplayReaponseForm();
		responseForm.setCsvRows(csvService.createCsvRows(files));
		List<Race> races = csvService.existsRaadNextRace();
		responseForm.setMessage(createDisplayMessage(races));
		model.addAttribute("responseForm", responseForm);
		return "data/race";
	}

	/**
	 *	画面に表示するメッセージを作成
	 */
	private String createDisplayMessage(List<Race> races) {
		if(races.size()==0) {
			return "次回のレースファイルが読み込まれていません";
		}
		return "次回のレースファイルは読み込み済みです";
	}

	/**
	 * 指定したレースCSVファイルを読み込む
	 * @param model Model
	 * @return メッセージ
	 */
	@PostMapping(value = "/data/csvread")
	@ResponseBody
	public String readCsvFile(@RequestBody String filename) {
		String message = "";
		ResourceBundle rb =ResourceBundle.getBundle("application");
		String dir = rb.getString("default.dir.path");
		if(csvService.readDataFromCsv(dir.concat(filename)) == 0) {
			message = "レースCSVファイルの中身が0行の可能性がありました";
		}else {
			message = "レースCSVファイルを読み込みました";
		}
		return message;
	}
}
