package com.example.demo.dto;

import lombok.Data;

/**
 * システム構成エンティティ
 * @author hirofumi.ogawa
 * @param
 */
@Data
public class Config {
    /**
     * システムキー
     */
	private String id;
    /**
     * システム設定
     */
	private String param;
}
