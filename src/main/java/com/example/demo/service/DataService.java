package com.example.demo.service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.utils.DateUtil;
import com.example.demo.controller.DataController;
import com.example.demo.dao.DataDao;
import com.example.demo.dto.MBettingTickets;
import com.example.demo.dto.Official;
import com.example.demo.dto.Race;
import com.example.demo.dto.THistories;
import com.example.demo.dto.TIntervalValues;
import com.example.demo.dto.TShippingOrder;
import com.example.demo.dto.master.TRaceResults;
import com.example.demo.dto.master.TRelations;
import com.example.demo.form.DataViewDisplayRequestForm;
import com.example.demo.form.DataViewFixRequestForm;
import com.example.demo.form.DataViewRequestForm;

/**
 * データ更新機能 Service
 */
@Service
public class DataService {

	//当選処理
	private static final String COMMAND_ID_DATA_RESISTER_WINNING = "COMMAND_ID_WIN";
	//選外時処理
	private static final String COMMAND_ID_DATA_RESISTER_NOT_SELECTED = "COMMAND_ID_NOT_SELECTED";
	//通常の入線
//	private static final String CODE_RESULT = "CODE_RESULT";
	//出走取消、競走除外
	private static final String CODE_SCRATCH = "CODE_SCRATCH";
	//降着
//	private static final String CODE_DEMOTION = "CODE_DEMOTION";
	//同着
//	private static final String CODE_DEADHEAT = "CODE_DEADHEAT";
	//人気同値、人気かぶり
	private static final String CODE_SAME  = "CODE_SAME";
	//複勝2着まで
//	private static final String CODE_PLACE = "CODE_PLACE";

	/**
	 * ロガー
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataController.class);

	/**
	 * データ更新機能 DAO
	 */
	@Autowired
	private DataDao dataDao;

	/**
	 * 券種マスタサービス
	 */
	@Autowired
	private MBettingTicketsService mbtService;

	/**
	 * 現在レース結果を何行登録しているか行数を取得する
	 */
	public int getRegisterdRows(){
		Calendar c = DateUtil.createToday();
		return dataDao.countRaceResultRow(
				new DataViewDisplayRequestForm(
						DateUtil.getMonthOfCalendar(c),
						DateUtil.getNextTargetDay(c)));//TODO　祝日対応
	}

	/**
	 * 現在値更新画面の表示項目を取得する
	 */
	public List<Race> getDisplayContents(){
		Calendar c = DateUtil.createToday();
		return dataDao.getRaceTableInfos(
				new DataViewDisplayRequestForm(
						DateUtil.getMonthOfCalendar(c),
						DateUtil.getNextTargetDay(c)));//TODO　祝日対応
	}

	/**
	 * レース結果修正処理のファサード
	 */
	public int fixFacade(DataViewFixRequestForm requestForm){

		//最新の作業履歴を取得、データ更新画面の行番号順に遡る
		List<THistories> histories = dataDao.selectHistories(requestForm.getRaceday());
		for(THistories history : histories) {
			//単勝の当選処理の巻き戻し
			if(history.getCommandId().equals(COMMAND_ID_DATA_RESISTER_WINNING) && history.getBettingTicketsKind().equals("win")) {
				rollbackByWinning(history);
			}
			//単勝の選外時処理
			if(history.getCommandId().equals(COMMAND_ID_DATA_RESISTER_NOT_SELECTED) && history.getBettingTicketsKind().equals("win")) {
				List<TShippingOrder> notselectedListOfWin = dataDao.selectShippingOrderOfNotSelectedAtWin(history.getBettingTicketsCode());
				MBettingTickets acdMbt = mbtService.selectWin(history);
				notselectedListOfWin.forEach(t -> acdCheckByNotSelected(t,history,acdMbt));
			}
			//複勝の当選処理の巻き戻し
			if(history.getCommandId().equals(COMMAND_ID_DATA_RESISTER_WINNING) && history.getBettingTicketsKind().equals("show")) {
				rollbackByWinning(history);
			}
			//複勝の選外時処理
			if(history.getCommandId().equals(COMMAND_ID_DATA_RESISTER_NOT_SELECTED) && history.getBettingTicketsKind().equals("show")) {
				List<MBettingTickets> mbtShowList = createBettingCodesForShow(history);
				List<TShippingOrder> notselectedListOfShow = dataDao.selectShippingOrdersOfNotSelectedAtShow(mbtShowList);
				MBettingTickets acdMbt = mbtService.selectShow(history);
				notselectedListOfShow.forEach(t -> acdCheckByNotSelected(t,history,acdMbt));
			}
			//ワイドの当選処理の巻き戻し
			if(history.getCommandId().equals(COMMAND_ID_DATA_RESISTER_WINNING) && history.getBettingTicketsKind().equals("wide")) {
				rollbackByWinning(history);
			}
			//ワイドの選外時処理
			if(history.getCommandId().equals(COMMAND_ID_DATA_RESISTER_NOT_SELECTED) && history.getBettingTicketsKind().equals("wide")) {
				List<MBettingTickets> mbtWideList = createBettingCodesForWide(history);
				List<TShippingOrder> notselectedListOfWide = dataDao.selectShippingOrdersOfNotSelectedAtWide(mbtWideList);
				List<MBettingTickets> acdWideList = mbtService.selectWide(history);
				notselectedListOfWide.forEach(t -> acdListCheckByNotSelected(t,history,acdWideList));
			}

			//巻き戻した履歴の削除
			removeHistory(history);
		}
		//レース結果テーブルを巻き戻す
		List<TRaceResults> raceResults = dataDao.getRaceResultsInfos(requestForm.getRaceday());
		removeRaceResult(Integer.valueOf(requestForm.getRownum()), raceResults);
		//最後に対象行の登録処理を行う
		rewriteResister(requestForm);

		return 0;
	}

	//レース結果に削除フラグを立てて巻き戻す
	private int removeRaceResult(int fixRownum, List<TRaceResults> raceResults) {
		for(TRaceResults trr : raceResults) {
			if(Integer.valueOf(trr.getRownum()) >= fixRownum) {
				trr.setIsDeleted("1");
				dataDao.removeRaceResult(trr);
			}
		}
		return 0;//TODO
	}

	//最後に対象行の登録処理を行う
	private int rewriteResister(DataViewFixRequestForm requestForm) {
		DataViewRequestForm rewriteForm = new DataViewRequestForm();
		BeanUtils.copyProperties(requestForm, rewriteForm);
		return resisterFacade(rewriteForm);
	}

	/**
	 * 巻き戻した履歴に削除フラグを立てる
	 * @param history
	 * @return
	 */
	private int removeHistory(THistories history) {
		history.setIsDeleted("1");
		return dataDao.removeHistory(history);
	}

	/**
	 * 修正機能による当選処理の巻き戻し
	 * @param history
	 * @return
	 */
	private int rollbackByWinning(THistories history) {
		logger.info("[rollbackByWinning]tivId="+ history.getTivId()+
				" id=" + history.getId() +
				" btCode=" + history.getBettingTicketsCode() +
				" btKind=" + history.getBettingTicketsKind() +
				" command=" + history.getCommandId());
		TIntervalValues tiv = dataDao.selectIntervalValueByPrimaryKey(history.getTivId());//TODO null対応
		logger.info("[rollbackByWinning]tiv="+ tiv);
		if(Objects.nonNull(tiv)) {
			tiv.setIsDeleted("1");
			dataDao.updateIntervalValue(tiv);
			TShippingOrder tso = dataDao.selectShippingOrder(tiv.getTsoId());
			tso.setPresentValue(tiv.getIntervalValue());
			dataDao.updateShippingOrder(tso);
		}
		return 0;//TODO
	}

	/**
	 * 作業履歴を書き込む処理
	 */
	private int writeHistory(DataViewRequestForm form, MBettingTickets mbt, String commandId, TIntervalValues tiv) {
		THistories h = historyBuilder(form, mbt, commandId, tiv);
		if(StringUtils.equals(CODE_SCRATCH, form.getAcdPullDownCd()) && StringUtils.equals(COMMAND_ID_DATA_RESISTER_NOT_SELECTED, commandId)) {
			h.setAccidentCode(form.getAcdPullDownCd());
			h.setAccident(form.getAcdPullDownVal());
		} else if(StringUtils.equals(CODE_SAME, form.getAcdPullDownCd())) {
			h.setAccidentCode(form.getAcdPullDownCd());
			h.setAccident(form.getAcdPullDownVal());
		} else {
			h.setAccident(null);
			h.setAccidentCode(null);
		}
		return dataDao.writeHitory(h);
	}

	/**
	 * 作業履歴を生成するビルダー
	 */
	private THistories historyBuilder(DataViewRequestForm form, MBettingTickets mbt, String commandId, TIntervalValues tiv) {
		THistories history = new THistories();
		BeanUtils.copyProperties(form, history);
		Official official = form.getOfficial();
		BeanUtils.copyProperties(official, history);
		if(StringUtils.equals("win", mbt.getKind())) {
			history.setRunnerUp("");
			history.setThirdPlace("");
		}
		if(StringUtils.equals(COMMAND_ID_DATA_RESISTER_WINNING, commandId) && StringUtils.equals("show", mbt.getKind())) {
			if(StringUtils.equals(form.getOfficial().getWinner(), mbt.getP2())){
				history.setRunnerUp("");
				history.setThirdPlace("");
			}
			if(StringUtils.equals(form.getOfficial().getRunnerUp(), mbt.getP2())){
				history.setWinner("");
				history.setThirdPlace("");
			}
			if(StringUtils.equals(form.getOfficial().getThirdPlace(), mbt.getP2())){
				history.setWinner("");
				history.setRunnerUp("");
			}
		}
		if(StringUtils.equals(COMMAND_ID_DATA_RESISTER_WINNING, commandId) && StringUtils.equals("wide", mbt.getKind())) {
			if(StringUtils.equals(form.getOfficial().getWinner(), mbt.getP1())
					&& StringUtils.equals(form.getOfficial().getRunnerUp(), mbt.getP2())){
				history.setThirdPlace("");
			}
			if(StringUtils.equals(form.getOfficial().getRunnerUp(),  mbt.getP1())
					&& StringUtils.equals(form.getOfficial().getThirdPlace(),  mbt.getP2())){
				history.setWinner("");
			}
			if(StringUtils.equals(form.getOfficial().getWinner(),  mbt.getP1())
					&& StringUtils.equals(form.getOfficial().getThirdPlace(),  mbt.getP2())){
				history.setRunnerUp("");
			}
		}
		history.setBettingTicketsKind(mbt.getKind());
		if(!StringUtils.equals(COMMAND_ID_DATA_RESISTER_NOT_SELECTED, commandId)) {
			history.setBettingTicketsCode(mbt.getCode());
		}
		history.setCommandId(commandId);
		if(Objects.nonNull(tiv)) {
			history.setTivId(tiv.getId());
		}
		history.setPresentValue(tiv.getIntervalValue());
		return history;
	}

	/**
	 * 修正機能により選外時処理の巻き戻し
	 * 選外の券種の巻き戻しについて現在値を-１する更新処理
	 */
	private int rollbackByNotSelected(TShippingOrder tso) {
		tso.setPresentValue(tso.getPresentValue()-1);
		return dataDao.updateShippingOrder(tso);
	}

	/**
	 * アクシデント項目リストにチェックを行う
	 * 選外時処理で巻き戻さない
	 */
	private int acdListCheckByNotSelected(TShippingOrder tso, THistories history, List<MBettingTickets> acdMbtList) {
		for(MBettingTickets mbt : acdMbtList) {
			//選外時処理で巻き戻す対象に、除外番号があれば巻き戻さない
			if(StringUtils.equals(tso.getBettingTicketsCode(), mbt.getCode())) {
				return 0;//TODO 雑
			}
		}
		return acdCheckByNotSelected(tso, history, new MBettingTickets());
	}

	/**
	 * アクシデント項目にチェックを行う
	 * 選外時処理で巻き戻さない
	 */
	private int acdCheckByNotSelected(TShippingOrder tso, THistories history, MBettingTickets acdMbt) {
		if(acdMbt != null) {
			//選外時処理で巻き戻す対象に、除外番号があれば巻き戻さない
			if(StringUtils.equals(CODE_SCRATCH, history.getAccidentCode())
					&& StringUtils.equals(tso.getBettingTicketsCode(), acdMbt.getCode())) {
				return 0;//TODO　雑
			}
			if(StringUtils.equals(CODE_SAME, history.getAccidentCode())
					&& StringUtils.equals(tso.getBettingTicketsCode(), acdMbt.getCode())) {
				return 0;//TODO　雑
			}
		}
		return rollbackByNotSelected(tso);
	}

	/**
	 * 現在値更新処理のファサード
	 */
	public int resisterFacade(DataViewRequestForm requestForm){

		try {
			if(requestForm.getOfficial()!=null) {
				String acdCd = requestForm.getAcdPullDownCd();

				//単勝の当選処理
				MBettingTickets mbtWin = createBettingCodesForWin(requestForm);
				//人気かぶりの場合、処理しない
				if(!StringUtils.equals(CODE_SAME, acdCd)) {
					winningOfBettingTicket(requestForm, mbtWin);
				}

				//単勝の選外時処理
				List<TShippingOrder> notselectedList = dataDao.selectShippingOrderOfNotSelectedAtWin(mbtWin.getCode());
				if(StringUtils.equals(CODE_SCRATCH, acdCd)) {
					//出走除外の場合
					MBettingTickets mbt = createBettingCodesForWin(requestForm);
					removeElementInTsoList(notselectedList, mbt);
				} else if(StringUtils.equals(CODE_SAME, acdCd)) {
					//人気かぶりの場合
					MBettingTickets mbt = createBettingCodesForWin(requestForm);
					removeElementInTsoList(notselectedList, mbt);
				}
				notselectedList.forEach(t -> notSelectedOfBettingTicket(requestForm, t));
				writeHistory(requestForm, mbtWin, COMMAND_ID_DATA_RESISTER_NOT_SELECTED, new TIntervalValues());

				//複勝の当選処理
				List<MBettingTickets> mbtShowList = createBettingCodesForShow(requestForm);
				if(StringUtils.equals(CODE_SAME, acdCd)) {
					//人気かぶりの場合
					MBettingTickets mbt = createBettingCodesForWinOfAcd(requestForm);
					mbtShowList.removeIf(m -> StringUtils.equals(m.getCode(), mbt.getCode()));
				}
				mbtShowList.forEach(mbt -> winningOfBettingTicket(requestForm, mbt));

				//複勝の選外時処理
				List<TShippingOrder> notselectedListOfShow = dataDao.selectShippingOrdersOfNotSelectedAtShow(mbtShowList);
				if(StringUtils.equals(CODE_SCRATCH, acdCd)) {
					//出走除外の場合
					List<MBettingTickets> mbtList = createBettingCodesForShowOfAcd(requestForm);
					removeElementInTsoList(notselectedListOfShow, mbtList.get(0));
				} else if(StringUtils.equals(CODE_SAME, acdCd)) {
					//人気かぶりの場合
					List<MBettingTickets> mbtList = createBettingCodesForShowOfAcd(requestForm);
					removeElementInTsoList(notselectedListOfShow, mbtList.get(0));
				}
				notselectedListOfShow.forEach(t -> notSelectedOfBettingTicket(requestForm, t));
				writeHistory(requestForm, mbtShowList.get(0), COMMAND_ID_DATA_RESISTER_NOT_SELECTED, new TIntervalValues());

				//ワイドの当選処理
				List<MBettingTickets> mbtWideList = createBettingCodesForWide(requestForm);
				if(StringUtils.equals(CODE_SAME, acdCd)){
					//人気かぶりの場合
					List<MBettingTickets> mbtList = createBettingCodesForWideOfAcd(requestForm);
					mbtList.forEach(mbt -> removeElementInMbtList(mbtWideList, mbt));
				}
				mbtWideList.forEach(mbt -> winningOfBettingTicket(requestForm, mbt));

				//ワイドの選外時処理
				List<TShippingOrder> notselectedListOfWide = dataDao.selectShippingOrdersOfNotSelectedAtWide(mbtWideList);
				if(StringUtils.equals(CODE_SCRATCH, acdCd)) {
					//出走除外の場合
					List<MBettingTickets> mbtList = createBettingCodesForWideOfAcd(requestForm);
					mbtList.forEach(mbt -> removeElementInTsoList(notselectedListOfWide, mbt));//TODO　強引
				} else if(StringUtils.equals(CODE_SAME, acdCd)) {
					//人気かぶりの場合
					List<MBettingTickets> mbtList = createBettingCodesForWideOfAcd(requestForm);
					mbtList.forEach(mbt -> removeElementInTsoList(notselectedListOfWide, mbt));//TODO　強引
				}
				notselectedListOfWide.forEach(t -> notSelectedOfBettingTicket(requestForm, t));
				writeHistory(requestForm, mbtWideList.get(0), COMMAND_ID_DATA_RESISTER_NOT_SELECTED, new TIntervalValues());

				//レース結果テーブルに書き込む
				TRaceResults raceResult = createRaceResults(requestForm);
				dataDao.writeRaceResult(raceResult);
				//正常であればidにオートインクリメントされている
				if(raceResult.getId() == 0 || Objects.isNull(raceResult.getId())) {
					logger.info("[resisterFacade][writeRaceResult][登録した主キーがオートインクリメントされていない可能性]"+ raceResult.toString());
				}
				//TODO　いる？
				//ダッシュボードの自動更新機能に連携するテーブル書き込み
				List<TRelations> relations = dataDao.selectRelationsByRaceday(requestForm);
				if(relations.size() == 0){
					dataDao.insertRelations(createRelation(requestForm));
				}else {
					dataDao.updateRelations(createRelation(requestForm));
				}
			}
		} catch(NullPointerException npe) {
			//追加したばかりの券種です　間隔値がない
			npe.printStackTrace();
		} finally {

		}
		return 0;
	}

	/**
	 * リレーションテーブルに登録、更新するエンティティを生成する
	 * @param requestForm
	 * @return
	 */
	private TRaceResults createRaceResults(DataViewRequestForm req) {
		TRaceResults trr = new TRaceResults();
		trr.setRownum(req.getRownum());
		trr.setRaceday(req.getRaceday());
		trr.setYear(req.getRaceday().substring(0, 4));
		trr.setMonth(DateUtil.trimDateZeroSuppress(
				req.getRaceday().substring(4, 6)));
		trr.setDay(DateUtil.trimDateZeroSuppress(
				req.getRaceday().substring(6)) );
		trr.setRacenum(req.getNo());
		trr.setPlace(req.getPlace());
		trr.setWinner(req.getOfficial().getWinner());
		trr.setRunnerUp(req.getOfficial().getRunnerUp());
		trr.setThirdPlace(req.getOfficial().getThirdPlace());
		trr.setAccidentCode(req.getAcdPullDownCd());
		trr.setAccidentValue(req.getAcdPullDownVal());
		return trr;
	}

	/**
	 * リレーションテーブルに登録、更新するエンティティを生成する
	 * @param requestForm
	 * @return
	 */
	private TRelations createRelation(DataViewRequestForm requestForm) {
		return new TRelations(
						requestForm.getRaceday(),
						requestForm.getRownum(),
						"0");

	}

	/**
	 * 出走除外の券種を券種リストから削除する
	 * @param 発送前リスト
	 * @param bettingTicketsCode
	 */
	private void removeElementInMbtList(List<MBettingTickets> list, MBettingTickets mbt) {
		list.removeIf(t -> StringUtils.equals(t.getCode(), mbt.getCode()));
	}

	/**
	 * 出走除外の券種を発送前リストから削除する
	 * @param 発送前リスト
	 * @param bettingTicketsCode
	 */
	private void removeElementInTsoList(List<TShippingOrder> list, MBettingTickets mbt) {
		list.removeIf(t -> StringUtils.equals(t.getBettingTicketsCode(), mbt.getCode()));
	}

	/**
	 * 券種の当選処理を行う
	 * 現在値を最新に間隔値にコピーする
	 * 現在値をゼロクリアする
	 * @param bettingTicketsCode
	 */
	private void winningOfBettingTicket(DataViewRequestForm requestForm, MBettingTickets mbt) throws NullPointerException{

		TShippingOrder tso = dataDao.selectShippingOrder(mbt.getCode());
		//発送順テーブルにレコードが存在している券種について当選処理を行う
		if(Objects.nonNull(tso)) {
			TIntervalValues tivMaxSort  = dataDao.selectIntervalValues(tso.getId()); //TODO null対応　その券種が間隔値テーブルにまだ現在値が入っていない場合　デフォルト9999対応？
			TIntervalValues tiv = intervalValuesBuilderOfWinning(tso, tivMaxSort);
			dataDao.resisterIntervalValue(tiv);
			updateShippingOrderForWinning(tso);
			//作業履歴を書き込む
			writeHistory(requestForm, mbt, COMMAND_ID_DATA_RESISTER_WINNING, tiv);	//TODO  置き場所よくない　定数化
		}
	}

	/**
	 * 当選した券種の最新の間隔値情報を作成するビルダー
	 * @param tso
	 * @param tivMaxSort
	 * @return 最新の間隔値情報
	 */
	private TIntervalValues intervalValuesBuilderOfWinning(TShippingOrder tso, TIntervalValues tivMaxSort) {
		return new TIntervalValues(
					tso.getId(),
					tivMaxSort.getSortNumber()+1,
					tso.getPresentValue());
	}

	/**
	 * 当選の券種について現在値をゼロクリアする更新処理
	 */
	private int updateShippingOrderForWinning(TShippingOrder tso) {
		tso.setPresentValue(0);
		return dataDao.updateShippingOrder(tso);
	}

	/**
	 * 券種の選外時処理を行う
	 * 選外の券種について現在値を＋１する更新処理
	 */
	private int notSelectedOfBettingTicket(DataViewRequestForm requestForm, TShippingOrder tso) {
		tso.setPresentValue(tso.getPresentValue()+1);
		dataDao.updateShippingOrder(tso);
		return 0;
	}

	/**
	 * 単勝の着順を券種情報にして返却する
	 */
	private MBettingTickets createBettingCodesForWin(DataViewRequestForm requestForm) {
		requestForm.setKind("win");
		return mbtService.selectWin(requestForm);
	}

	/**
	 * プルダウン項目の例外結果を券種情報にして返却する
	 */
	private MBettingTickets createBettingCodesForWinOfAcd(DataViewRequestForm requestForm) {
		requestForm.setKind("win");
		return mbtService.selectWinByAcd(requestForm);
	}

	/**
	 * 複勝の着順を券種情報リストにして返却する
	 */
	private List<MBettingTickets> createBettingCodesForShow(DataViewRequestForm requestForm){
		requestForm.setKind("show");
		List<MBettingTickets> mbtList = mbtService.selectShow(requestForm);
		return mbtList;
	}

	/**
	 * プルダウン項目の例外結果を券種情報リストにして返却する
	 */
	private List<MBettingTickets> createBettingCodesForShowOfAcd(DataViewRequestForm requestForm){
		requestForm.setKind("show");
		List<MBettingTickets> mbtList = mbtService.selectShowByAcd(requestForm);
		return mbtList;
	}

	/**
	 * 複勝の着順を券種コードリストにして返却する
	 */
	private List<MBettingTickets> createBettingCodesForShow(THistories history){
		List<MBettingTickets> mbtList = mbtService.selectShow("show", history);
		return mbtList;
	}

	/**
	 * ワイドの着順を券種コードリストにして返却する
	 */
	private List<MBettingTickets> createBettingCodesForWide(DataViewRequestForm requestForm) {
		requestForm.setKind("wide");
		List<MBettingTickets> mbtList = mbtService.selectWide(requestForm);
		return mbtList;
	}

	/**
	 * プルダウン項目の例外結果をワイドの券種情報リストにして返却する
	 */
	private List<MBettingTickets> createBettingCodesForWideOfAcd(DataViewRequestForm requestForm){
		requestForm.setKind("wide");
		List<MBettingTickets> mbtList = mbtService.selectWideByAcd(requestForm);
		return mbtList;
	}

	/**
	 * ワイドの着順を券種コードリストにして返却する
	 */
	private List<MBettingTickets> createBettingCodesForWide(THistories history){
		List<MBettingTickets> mbtList = mbtService.selectWide("wide", history);
		return mbtList;
	}

}
