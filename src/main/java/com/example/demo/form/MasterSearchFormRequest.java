package com.example.demo.form;

import java.io.Serializable;

import lombok.Data;
/**
 * 券種マスタ 検索フォームリクエストデータ
 */
@Data
public class MasterSearchFormRequest implements Serializable {
	/**
	 * 券種コード
	 */
	private String code;
}
