package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 間隔値テーブル DTO
 * @author hirofumi.ogawa
 * @param
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TIntervalValues {
    public TIntervalValues(int tsoId, int sortNumber, int presentValue) {
    	this.tsoId = tsoId;
    	this.sortNumber = sortNumber;
    	this.intervalValue = presentValue;
	}
	/**
     * ID
     */
	private int id;
    /**
     * 発送順テーブルID
     */
	private int tsoId;
    /**
     * 間隔値昇順採番
     */
	private int sortNumber;
    /**
     * 間隔値
     */
	private int intervalValue;
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
