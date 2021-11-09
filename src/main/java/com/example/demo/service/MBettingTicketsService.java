package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MBettingTickets;
import com.example.demo.dto.THistories;
import com.example.demo.form.DataViewRequestForm;
import com.example.demo.form.MasterSearchFormRequest;
import com.example.demo.mapper.MBettingTicketsMapper;
/**
 * 券種マスタ Service
 */
@Service
public class MBettingTicketsService {
    /**
     * 券種マスタ Mapper
     */
    @Autowired
    private MBettingTicketsMapper mapper;
    /**
     * 券種マスタ検索
     * @param masterSearchFormRequest リクエストデータ
     * @return 券種マスタ
     */
    public MBettingTickets search(MasterSearchFormRequest masterSearchFormRequest) {
        return mapper.search(masterSearchFormRequest);
    }
    /**
     * 券種マスタ一覧取得
     * @param なし
     * @return 券種マスタ一覧
     */
    public List<MBettingTickets> searchAll() {
        return mapper.searchAll();
    }

    /**
     * 人気番号から単勝の券種情報を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	MBettingTickets selectWin(DataViewRequestForm form){
		return mapper.selectWin(form);
	}

    /**
     * アクシデントがあった単勝の券種情報を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	MBettingTickets selectWinByAcd(DataViewRequestForm form){
		return mapper.selectWinByAcd(form);
	}

    /**
     * 作業履歴から単勝の券種情報を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	MBettingTickets selectWin(THistories history){
		return mapper.selectWinByKindAndNo(history.getBettingTicketsKind(), history.getAccident());
	}

    /**
     * 人気番号から複勝の券種情報一覧を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectShow(DataViewRequestForm form){
		return mapper.selectShow(form);
	}

    /**
     * アクシデントがあった複勝の券種情報一覧を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectShowByAcd(DataViewRequestForm form){
		return mapper.selectShowByAcd(form);
	}

    /**
     * 作業履歴から複勝の券種情報を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	MBettingTickets selectShow(THistories history){
		return mapper.selectShowByKindAndNo(history.getBettingTicketsKind(), history.getAccident());
	}

    /**
     * 種別と着順を条件に複勝の券種情報一覧を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectShow(String kind, THistories history){
		return mapper.selectShowByKindAndOfficial(kind, history.getWinner(), history.getRunnerUp(), history.getThirdPlace());
	}

    /**
     * 人気番号からワイドの券種情報一覧を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectWide(DataViewRequestForm form){
		return mapper.selectWide(form);
	}

    /**
     * アクシデントがあった人気番号からワイドの券種情報一覧を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectWideByAcd(DataViewRequestForm form){
		return mapper.selectWideByAcd(form);
	}

    /**
     * 作業履歴からワイドの券種情報一覧を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectWide(THistories history){
		return mapper.selectWideByByKindAndNo(history.getBettingTicketsKind(), history.getAccident());
	}

    /**
     * 種別と着順を条件にワイドの券種情報一覧を検索して返却する
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectWide(String kind, THistories history){
		return mapper.selectWideByKindAndOfficial(kind, history.getWinner(), history.getRunnerUp(), history.getThirdPlace());
	}
}
