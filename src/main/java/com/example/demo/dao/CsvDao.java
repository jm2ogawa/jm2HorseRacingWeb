package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.Race;
import com.example.demo.mapper.CsvMapper;

/**
 * CSV機能DAO
 */
@Repository
public class CsvDao {

	/**
	 * CSV機能マッパー
	 */
	@Autowired
	private CsvMapper csvMapper;

	/**
	 * 日付を条件にレースデータ検索
	 * @param Race
	 * @return
	 */
	public List<Race> selectRacesForExistsByDate(String year, String month, String day) {
		return csvMapper.selectRacesByDate(year, month, day);
	}

	/**
	 * レース一覧CSV情報登録
	 * @param Race
	 * @return
	 */
	public int raceCsvInfoRegister(List<Race> raceCsvInfoList) {
		return csvMapper.insertBulk(raceCsvInfoList);
	}
}
