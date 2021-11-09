package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Race;
import com.example.demo.form.MenuResponseForm;
/**
 * メニューサービス
 */
@Service
public class MenuService {
	/**
	 * CSVサービス
	 */
	@Autowired
	private CsvService csvService;
	/**
	 * 表示初期処理
	 * 画面コントロールのレスポンスを生成して返却する
	 * @param
	 * @return 検索結果
	 */
	public MenuResponseForm createMenuResponse() {
		MenuResponseForm responseForm = new MenuResponseForm();
		List<Race> races = csvService.existsRaadNextRace();
		responseForm.setDataUpdateButtonStatus(checkDisplayButtonStatus(races));
		responseForm.setMessage(createDisplayMessage(races));
	    return responseForm;
	}

	/**
	 *	画面のボタンの活性/非活性を判定して返却する
	 * @param races
	 * @return
	 */
	private boolean checkDisplayButtonStatus(List<Race> races) {
		if(races.size()==0) {
			return true;
		}
		return false;
	}

	/**
	 *	画面に表示するメッセージを作成
	 */
	private String createDisplayMessage(List<Race> races) {
		if(races.size()==0) {
			return "次回のレースファイルが読み込まれていません -> 【レースCSV読込】";
		}
		return "レース結果を入力可能です -> 【現在値更新】";
	}
}
