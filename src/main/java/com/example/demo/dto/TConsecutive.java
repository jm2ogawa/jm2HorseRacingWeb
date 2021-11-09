package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Xの次テーブル DTO
 * @author hirofumi.ogawa
 * @param
 */
@Data
public class TConsecutive {
    /**
     * ID
     */
	private int id;
    /**
     * 発送順テーブルID
     */
	private int tsoId;
    /**
     * Xの次種別コード
     */
	private String consecutiveCode;
    /**
     * ハマリ次の値
     */
	private String fitValue;
    /**
     * 2連次の値
     */
	private String secondValue;
    /**
     * 3連次の値
     */
	private String thirdValue;
    /**
     * MAX値
     */
	private String maxValue;
    /**
     * limitValue
     */
	private String limitValue;
    /**
     * 追いの値
     */
	private String chase;
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
