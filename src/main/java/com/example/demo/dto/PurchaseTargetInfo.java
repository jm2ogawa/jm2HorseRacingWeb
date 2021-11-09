package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 購入情報　DTO
 * @author hirofumi.ogawa
 * @param
 */
@Data
public class PurchaseTargetInfo {
    /**
     * 発送順テーブルID
     */
	private int tsoId;
    /**
     * 券種コード
     */
	private String bettingTicketsCode;
    /**
     * 券種名称
     */
	private String bettingTicketsName;
    /**
     * 現在値
     */
	private int presentVal;
    /**
     * 間隔値テーブルID
     */
	private int tcId;
    /**
     * Xの次種別
     */
	private String consecutiveCode;
	/**
	 * Xの次名称
	 */
	private String consecutiveName;
    /**
     * ハマリ次の値
     */
	private int fitVal;
    /**
     * 2連次の値
     */
	private int secondVal;
    /**
     * 3連次の値
     */
	private int thirdVal;
    /**
     * MAX値
     */
	private int maxVal;
    /**
     * レーススタート値
     */
	private int limitVal;
    /**
     * 最新の間隔値
     */
	private int vmax1;
    /**
     * 最新の間隔値の1つ前
     */
	private int vmax2;
    /**
     * 最新の間隔値の2つ前
     */
	private int vmax3;
    /**
     * 更新日時
     */
	private Timestamp updatedAt;
    /**
     * 削除フラグ
     */
	private String isDeleted;
}
