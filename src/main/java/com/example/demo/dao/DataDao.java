package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.MBettingTickets;
import com.example.demo.dto.Race;
import com.example.demo.dto.THistories;
import com.example.demo.dto.TIntervalValues;
import com.example.demo.dto.TShippingOrder;
import com.example.demo.dto.master.TRaceResults;
import com.example.demo.dto.master.TRelations;
import com.example.demo.form.DataViewDisplayRequestForm;
import com.example.demo.form.DataViewRequestForm;
import com.example.demo.form.dashboard.DashBoardCheckRequestForm;
import com.example.demo.mapper.DataMapper;
import com.example.demo.mapper.THistoriesMapper;
import com.example.demo.mapper.TRelationsMapper;

/**
 * データ更新機能DAO
 */
@Repository
public class DataDao {

	/**
	 * データ更新機能マッパー
	 */
	@Autowired
	private DataMapper dataMapper;

	/**
	 * 作業履歴テーブルマッパー
	 */
	@Autowired
	private THistoriesMapper historiesMapper;

	/**
	 * リレーションテーブルマッパー
	 */
	@Autowired
	private TRelationsMapper relationsMapper;

	/**
	 * レーステーブル情報一覧を取得する
	 */
	public List<Race> getRaceTableInfos(DataViewDisplayRequestForm requestForm){
		return dataMapper.getRaceTableInfos(requestForm);
	}

	/**
	 * レース結果テーブル情報一覧を取得する
	 */
	public List<TRaceResults> getRaceResultsInfos(String raceday){
		return dataMapper.selectRaceResultsInfos(raceday);
	}

	/**
	 * 日付を条件にレース結果テーブルの行数をカウントする
	 */
	public int countRaceResultRow(DataViewDisplayRequestForm requestForm){
		return dataMapper.countRaceResultRow(requestForm);
	}

	/**
	 * レース結果テーブルに結果を1件書き込む
	 */
	public int writeRaceResult(TRaceResults raceResult) {
		return dataMapper.insertRaceResults(raceResult);
	}

	/**
	 * レース結果テーブルに削除フラグを立てる
	 */
	public int removeRaceResult(TRaceResults raceResult) {
		return dataMapper.updateRaceResults(raceResult);
	}

	/**
	 * 主キーをキーとして発送順テーブル情報を取得する
	 */
	public TShippingOrder selectShippingOrder(int id){
		return dataMapper.selectShippingOrderByPrimaryKey(id);
	}

	/**
	 * 券種コードを条件に発送順テーブル情報を取得する
	 */
	public TShippingOrder selectShippingOrder(String bettingTicketsCode){
		return dataMapper.selectShippingOrder(bettingTicketsCode);
	}

	/**
	 * 単勝で選外になった発送順テーブル情報を取得する
	 */
	public List<TShippingOrder> selectShippingOrderOfNotSelectedAtWin(String bettingTicketsCode){
		return dataMapper.selectShippingOrderOfNotSelectedAtWin(bettingTicketsCode);
	}

	/**
	 * 単勝で選外になった発送順テーブル情報を取得する
	 */
	public List<TShippingOrder> selectShippingOrderOfNotSelectedAtWin(List<String> bettingTicketsCode){
		return dataMapper.selectShippingOrdersOfNotSelectedAtWin(bettingTicketsCode);
	}

	/**
	 * 複勝で選外になった発送順テーブル情報一覧を取得する
	 */
	public List<TShippingOrder> selectShippingOrdersOfNotSelectedAtShow(List<MBettingTickets> bettingTicketsCodes){
		return dataMapper.selectShippingOrdersOfNotSelectedAtShow(bettingTicketsCodes);
	}

	/**
	 * ワイドで選外になった発送順テーブル情報一覧を取得する
	 */
	public List<TShippingOrder> selectShippingOrdersOfNotSelectedAtWide(List<MBettingTickets> bettingTicketsCodes){
		return dataMapper.selectShippingOrdersOfNotSelectedAtWide(bettingTicketsCodes);
	}

	/**
	 * 券種コードを条件に発送順テーブルの現在値を更新する
	 */
	public int updateShippingOrder(TShippingOrder tso){
		return dataMapper.updateShippingOrder(tso);
	}

	/**
	 * 間隔値テーブルに最新の間隔値を登録する
	 */
	public int resisterIntervalValue(TIntervalValues tiv){
		return dataMapper.resisterIntervalValue(tiv);
	}

	/**
	 * 主キーをキーとして間隔値テーブルを更新する
	 */
	public int updateIntervalValue(TIntervalValues tiv){
		return dataMapper.updateIntervalValue(tiv);
	}

	/**
	 * 対象の券種の最新ソート番号の間隔値テーブル情報を取得する
	 */
	public TIntervalValues selectIntervalValues(int tsoId){
		return dataMapper.selectIntervalValues(tsoId);
	}

	/**
	 * 間隔値テーブルから間隔値テーブル情報を取得する
	 */
	public TIntervalValues selectIntervalValueByPrimaryKey(int intervalValueId){
		return dataMapper.selectIntervalValueByPrimaryKey(intervalValueId);
	}

	/**
	 * 作業履歴テーブルに当選処理、選外時処理の履歴を書き込む
	 */
	public int writeHitory(THistories history) {
		return historiesMapper.insertHistory(history);
	}

	/**
	 * 作業履歴テーブルに削除フラグを立てて更新する
	 */
	public int removeHistory(THistories history) {
		return historiesMapper.updateHistory(history);
	}

	/**
	 * 作業履歴テーブルのレコードにアクシデント内容を更新する
	 */
	public int updateHistoryByAccident(THistories history) {
		return historiesMapper.updateHistoryByAccident(history);
	}

	/**
	 * 作業履歴テーブルから履歴一覧を取得する
	 */
	public List<THistories> selectHistories() {
		return historiesMapper.selectHistories();
	}

	/**
	 * 作業履歴テーブルからレース当日の履歴一覧を取得する
	 */
	public List<THistories> selectHistories(String raceday) {
		return historiesMapper.selectHistoriesByRaceday(raceday);
	}

	/**
	 * リレーションテーブルにレース結果の登録を終えた行を書き込む
	 */
	public int insertRelations(TRelations relations) {
		return relationsMapper.insertRelations(relations);
	}

	/**
	 * リレーションテーブルにレース結果の登録を終えた行番号を更新する
	 */
	public int updateRelations(TRelations relations) {
		return relationsMapper.updateRelations(relations);
	}

	/**
	 * リレーションテーブルから購入一覧機能でレース開催日を条件に行番号を取得する
	 */
	public List<TRelations> selectRelationsByRaceday(DashBoardCheckRequestForm requestForm) {
		return relationsMapper.selectRelationsByRacedayOfCheck(requestForm);
	}

	/**
	 * リレーションテーブルからデータ登録機能でレース開催日を条件に行番号を取得する
	 */
	public List<TRelations> selectRelationsByRaceday(DataViewRequestForm requestForm) {
		return relationsMapper.selectRelationsByRacedayOfRegister(requestForm);
	}
}
