package com.example.demo.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataViewDisplayRequestForm {

	/**
	 * データ更新画面に表示させる月
	 * レーステーブルを引っ張ってくる条件に使う
	 */
	private int month;

	/**
	 * データ更新画面に表示させる日
	 * レーステーブルを引っ張ってくる条件に使う
	 */
	private int day;
}
