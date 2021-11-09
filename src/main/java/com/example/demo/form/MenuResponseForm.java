package com.example.demo.form;

import java.io.Serializable;

import lombok.Data;
/**
 * メニューのレスポンスクラス
 */
@Data
public class MenuResponseForm implements Serializable  {
	/**
	 * 画面に表示するメッセージ
	 */
	private String message;
	/**
	 * 現在値更新ボタンの活性/非活性
	 */
	private boolean dataUpdateButtonStatus;

}
