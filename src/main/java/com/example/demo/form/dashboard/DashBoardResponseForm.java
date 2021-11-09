package com.example.demo.form.dashboard;

import java.io.Serializable;
import java.util.List;

import com.example.demo.dto.PurchaseTargetInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashBoardResponseForm implements Serializable {
	//画面上部メッセージ
	private String message;
	//購入リスト
	private List<PurchaseTargetInfo> purTgtInfoList;
	//レース日付
	private String raceday;
	//現在進行中のレースデータ登録行
	private String rownum;
	//ポップアップメッセージ、新規購入リスト追加
	private String popupMessage;
	//TODO　ポップアップするリスト、未使用
	private List<PurchaseTargetInfo> newPurchases;
	//システム設定：タイマーオン、オフ
	private String confKeyTimerMode;
}
