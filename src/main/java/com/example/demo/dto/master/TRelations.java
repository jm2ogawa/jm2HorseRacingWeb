package com.example.demo.dto.master;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 現在値更新機能と購入一覧を連携するリレーションテーブル
 * @author hirofumi.ogawa
 * @param
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="t_relations")
public class TRelations {
	private String raceday;
	private String rownum;
	private String isDeleted;
}
