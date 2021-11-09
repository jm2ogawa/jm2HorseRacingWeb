package com.example.demo.form;

import java.io.Serializable;

import com.example.demo.dto.Official;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * データ修正リクエストフォーム
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataViewFixRequestForm implements Serializable {
	//着順確定（1着人気～3着人気）
	private Official official;
	//作業種別ID
	private String commandId;
	//馬場状態
	private String kind;
	//行番号
	private String rownum;
	//頭数
	private String horse;
	//レース日時
	private String raceday;
	//年
	private String year;
	//月
	private String month;
	//日
	private String day;
	//会場
	private String place;
	//レース番号
	private String no;
	//プルダウンコード
	private String acdPullDownCd;
	//プルダウン値
	private String acdPullDownVal;
}