package com.example.demo.form;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.dto.Official;
import com.example.demo.dto.Race;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 現在値更新画面、レース結果入力項目レスポンスフォームクラス
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataViewResponseForm implements Serializable  {
    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;
	/**
	 * メッセージ
	 */
	private String message;
	/**
	 * 現在値更新画面表示項目、レース情報一覧
	 */
	private List<Race> raceList;
	/**
	 * 当日の各レースについて人気番号の着順―覧
	 */
	private List<Official> officials;
	/**
	 * 現在処理中の行
	 */
	private Integer progressRow;
	/**
	 * アクシデントプルダウンマップ
	 */
	private Map<String,String> acdPullDownMap;
}
