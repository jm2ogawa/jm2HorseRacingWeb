package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 発送順テーブル DTO
 * @author hirofumi.ogawa
 * @param
 */
@Data
public class TShippingOrder {
    /**
     * ID
     */
	private int id;
    /**
     * 券種マスタコード
     */
	private String bettingTicketsCode;
    /**
     * 10倍
     */
	private int odds;
    /**
     * 23回　追い
     */
	private int timeChase;
    /**
     * 48か　10万
     */
	private int timeChaseSub;
    /**
     * 現在値
     */
	private int presentValue;
    /**
     * 連続不当最大
     */
	private String continuousInjusticeMax;
    /**
     * 連続不当カウントD
     */
	private int continuousInjusticeCount;
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
