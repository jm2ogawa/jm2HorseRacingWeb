package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Xの次マスタ DTO
 * @author hirofumi.ogawa
 * @param
 */
@Data
public class MConsecutive {
	/**
     * Xの次種別コード
     */
	private String code;
    /**
     * Xの次名称
     */
	private String name;
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
