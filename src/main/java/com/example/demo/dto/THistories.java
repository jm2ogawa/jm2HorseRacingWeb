package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 作業履歴テーブル TDTO
 * @author hirofumi.ogawa
 * @param
 */
@Data
public class THistories {
    /**
     * 作業履歴ID
     */
	private int id;
    /**
     * レース日時
     */
	private String raceday;
    /**
     * データ登録画面の行番号
     */
	private String rownum;
    /**
     * 馬場状態
     */
	private String place;
    /**
     * レース番号
     */
	private String no;
    /**
     * 券種コード
     */
	private String bettingTicketsKind;
    /**
     * 券種コード
     */
	private String bettingTicketsCode;
    /**
     * 処理種別ID
     */
	private String commandId;
    /**
     * アクシデントコード
     */
	private String accidentCode;
    /**
     * 1着の人気
     */
	private String winner;
    /**
     * 2着の人気
     */
	private String runnerUp;
    /**
     * 3着の人気
     */
	private String thirdPlace;
    /**
     * アクシデントがあった人気
     */
	private String accident;
    /**
     * 間隔値テーブルID
     */
	private Integer tivId;
    /**
     * 作業時の現在値（過去）
     */
	private Integer presentValue;
    /**
     * 頭数
     */
	private String horse;
    /**
     * 作成日時
     */
	private Timestamp createdAt;
    /**
     * 更新日時
     */
	private Timestamp updatedAt;
    /**
     * 削除フラグ
     */
	private String isDeleted;
}
