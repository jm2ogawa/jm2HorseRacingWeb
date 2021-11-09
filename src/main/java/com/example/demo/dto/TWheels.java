package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Xの輪テーブル DTO
 * @author hirofumi.ogawa
 * @param
 */
@Data
public class TWheels {
    /**
     * ID
     */
	private int id;
    /**
     * 発送順テーブルID
     */
	private int tsoId;
    /**
     * 2の輪
     */
	private String two;
    /**
     * 3の輪
     */
	private String three;
    /**
     * 4の輪
     */
	private String four;
    /**
     * 5の輪
     */
	private String five;
    /**
     * 6の輪
     */
	private String six;
    /**
     * 7の輪
     */
	private String seven;
    /**
     * 8の輪
     */
	private String eight;
    /**
     * 9の輪
     */
	private String nine;
    /**
     * 10の輪
     */
	private String ten;
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
