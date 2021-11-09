package com.example.demo.form;

import java.util.List;

import com.example.demo.dto.CsvRow;

import lombok.Data;
/**
 * レースCSV管理画面のレスポンスクラス
 */
@Data
public class DataRaceDisplayReaponseForm {
	private String message;
	private List<CsvRow> csvRows;
}
