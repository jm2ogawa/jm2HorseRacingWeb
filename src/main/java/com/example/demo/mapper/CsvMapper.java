package com.example.demo.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.Race;
/**
 * CSV機能マッパー
 */
@Mapper
public interface CsvMapper {

	/**
	 * 日付を条件にレースデータ検索
	 */
	List<Race> selectRacesByDate(
			@Param("year") String year,
			@Param("month") String month,
			@Param("day") String day);

	/**
	 * レースデータ挿入
	 * @param Race
	 * @return
	 */
	int insertBulk(@Param("raceList") List<Race> raceCsvInfoList);
}
