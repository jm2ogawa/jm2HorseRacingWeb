package com.example.demo.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.MBettingTickets;
import com.example.demo.dto.Race;
import com.example.demo.dto.TIntervalValues;
import com.example.demo.dto.TShippingOrder;
import com.example.demo.dto.master.TRaceResults;
import com.example.demo.form.DataViewDisplayRequestForm;
/**
 * データ更新機能マッパー
 */
@Mapper
public interface DataMapper {

	/**
	 * レーステーブル情報一覧を取得する
	 * @param Race
	 * @return List<Race>
	 */
	List<Race> getRaceTableInfos(@Param("form") DataViewDisplayRequestForm form);

	/**
	 * レース結果テーブル情報一覧を取得する
	 * @param month
	 * @param day
	 * @return List<Race>
	 */
	List<TRaceResults> selectRaceResultsInfos(@Param("raceday") String raceday);

	/**
	 * 日付を条件にレース結果テーブルの行数をカウントする
	 * @param Race
	 * @return List<Race>
	 */
	int countRaceResultRow(@Param("form") DataViewDisplayRequestForm form);

	/**
	 * レース結果テーブルにレコードを登録する
	 * @param Race
	 * @return List<Race>
	 */
	int insertRaceResults(@Param("trr") TRaceResults trr);

	/**
	 * レース結果テーブルのレコードを削除フラグを立てて更新する
	 * @param Race
	 * @return List<Race>
	 */
	int updateRaceResults(@Param("trr") TRaceResults trr);

	/**
	 * 主キーとキーとして発送順テーブル情報を取得する
	 * @param bettingTicketsCode
	 * @return TShippingOrder
	 */
	TShippingOrder selectShippingOrderByPrimaryKey(int id);

	/**
	 * 券種コードを条件に発送順テーブル情報を取得する
	 * @param bettingTicketsCode
	 * @return TShippingOrder
	 */
	TShippingOrder selectShippingOrder(String bettingTicketsCode);

	/**
	 * 単勝の選外である発送順テーブル情報一覧を取得する
	 * @param bettingTicketsCode
	 * @return List<TShippingOrder>
	 */
	List<TShippingOrder> selectShippingOrderOfNotSelectedAtWin(String bettingTicketsCode);

	/**
	 * 単勝の選外である発送順テーブル情報一覧を取得する
	 * @param bettingTicketsCode
	 * @return List<TShippingOrder>
	 */
	List<TShippingOrder> selectShippingOrdersOfNotSelectedAtWin(@Param("codeList") List<String> bettingTicketsCodes);

	/**
	 * 複勝の当選リストから選外である発送順テーブル情報一覧を取得する
	 * @param List<String> bettingTicketsCodes
	 * @return List<TShippingOrder>
	 */
	List<TShippingOrder> selectShippingOrdersOfNotSelectedAtShow(@Param("codeList") List<MBettingTickets> bettingTicketsCodes);

	/**
	 * ワイドの当選リストから選外である発送順テーブル情報一覧を取得する
	 * @param List<String> bettingTicketsCodes
	 * @return List<TShippingOrder>
	 */
	List<TShippingOrder> selectShippingOrdersOfNotSelectedAtWide(@Param("codeList") List<MBettingTickets> bettingTicketsCodes);

	/**
	 * 発送順テーブルのレコードを更新する
	 * @param DataViewRequestForm
	 * @return int
	 */
	int updateShippingOrder(TShippingOrder tso);

	/**
	 * 発送順テーブルの複数のレコードを更新する
	 */
	int updateBatchShippingOrder(List<String> list);

	/**
	 * 間隔値テーブルにレコードを登録する
	 * @param TIntervalValues
	 * @return int
	 */
	int resisterIntervalValue(@Param("tiv") TIntervalValues tiv);

	/**
	 * 主キーをキーとして間隔値テーブルを更新する
	 */
	int updateIntervalValue(@Param("tiv") TIntervalValues tiv);

	/**
	 * 対象の券種を条件に最新ソート番号の間隔値テーブル情報を取得する
	 * @param bettingTicketsCode
	 * @return TIntervalValues
	 */
	TIntervalValues selectIntervalValues(int tsoId);

	/**
	 * 主キーを条件に間隔値テーブル情報を取得する
	 * @param bettingTicketsCode
	 * @return TIntervalValues
	 */
	TIntervalValues selectIntervalValueByPrimaryKey(int id);

}
