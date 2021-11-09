package com.example.demo.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.THistories;
import com.example.demo.form.dashboard.DashBoardCheckRequestForm;
/**
 * 作業履歴テーブルマッパー
 */
@Mapper
public interface THistoriesMapper {

	/**
	 * 作業履歴テーブルにレコードを登録する
	 * @param THistories
	 * @return
	 */
	int insertHistory(@Param("th")THistories th);

	/**
	 * 作業履歴テーブルを削除フラグを立てて更新する
	 * @param THistories
	 * @return
	 */
	int updateHistory(@Param("th")THistories th);

	/**
	 * 作業履歴テーブルのレコードにアクシデント内容を更新する
	 * @param THistories
	 * @return
	 */
	int updateHistoryByAccident(@Param("th")THistories th);

	/**
	 * 作業履歴テーブルから作業履歴一覧を取得する
	 * @param
	 * @return
	 */
	List<THistories> selectHistories();

	/**
	 * 作業履歴テーブルからレース日時を条件として作業履歴一覧を取得する
	 * @param
	 * @return
	 */
	List<THistories> selectHistoriesByRaceday(String raceday);

	/**
	 * 作業履歴テーブルから当日とレース番号を条件にレコードを取得する
	 * @param
	 * @return
	 */
	List<THistories> selectHistoriesByRacedayAndRownum(@Param("req")DashBoardCheckRequestForm requestForm);
}
