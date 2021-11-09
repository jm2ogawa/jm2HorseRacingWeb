package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 券種マスタ DTO
 * @author hirofumi.ogawa
 * @param
 */
@Data
public class MBettingTickets {
    /**
     * 券種コード
     */
	private String code;
    /**
     * 券種名称
     */
	private String name;
    /**
     * 種別
     */
	private String kind;
    /**
     * 1着の人気
     */
	private String p1;
    /**
     * 2着の人気
     */
	private String p2;
    /**
     * 3着の人気
     */
	private String p3;
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
