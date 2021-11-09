package com.example.demo.dto;

import java.io.File;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レースCSV管理画面の行管理クラス
 * @author hirofumi.ogawa
 * @param
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CsvRow implements Serializable {
	/**
	 * 項番
	 */
	private int no;
	/**
	 * ファイルパス
	 */
	private File file;
	/**
	 * CSV読み込みボタンの活性/非活性
	 */
	private String csvReadButtonStatus;
	/**
	 * 行の背景色
	 */
	private String rowColor;
}
