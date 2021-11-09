package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.MBettingTickets;
import com.example.demo.form.DataViewRequestForm;
import com.example.demo.form.MasterSearchFormRequest;

/**
 * 券種情報マスタ Mapper
 */
@Mapper
public interface MBettingTicketsMapper {

    /**
     * 券種情報検索
     * @param masterSearchFormRequest 検索用リクエストデータ
     * @return 券種マスタ情報
     */
	MBettingTickets search(MasterSearchFormRequest masterSearchFormRequest);

    /**
     * 全ての券種情報を検索
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> searchAll();

    /**
     * 当選した単勝の券種情報を検索
     * @param なし
     * @return 券種マスタ情報
     */
	MBettingTickets selectWin(DataViewRequestForm form);

    /**
     * アクシデントがあった人気番号から単勝の券種情報を検索
     * @param なし
     * @return 券種マスタ情報
     */
	MBettingTickets selectWinByAcd(DataViewRequestForm form);

    /**
     * 種別と人気番号から当選した単勝の券種情報を検索
     * @param なし
     * @return 券種マスタ情報
     */
	MBettingTickets selectWinByKindAndNo(@Param("kind")String kind, @Param("no")String no);

    /**
     * 当選した複勝の券種情報一覧を検索
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectShow(DataViewRequestForm form);

    /**
     * アクシデントがあった人気番号から複勝の券種情報一覧を検索
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectShowByAcd(DataViewRequestForm form);

    /**
     * 種別と人気番号から複勝の券種情報一覧を検索
     * @param なし
     * @return 券種マスタ情報一覧
     */
	MBettingTickets selectShowByKindAndNo(@Param("kind")String kind, @Param("no")String no);

    /**
     * 種別と着順を条件に当選した複勝の券種情報一覧を検索
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectShowByKindAndOfficial(@Param("kind")String kind, @Param("winner")String winner, @Param("runnerUp")String runnerUp, @Param("thirdPlace")String thirdPlace);

    /**
     * 当選したワイドの券種情報一覧を検索
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectWide(DataViewRequestForm form);

    /**
     * アクシデントがあった人気番号からワイドの券種情報一覧を検索
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectWideByAcd(DataViewRequestForm form);

    /**
     * 作業履歴からワイドの券種情報一覧を検索
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectWideByByKindAndNo(@Param("kind")String kind, @Param("no")String no);

    /**
     * 種別と着順を条件に当選したワイドの券種情報一覧を検索
     * @param なし
     * @return 券種マスタ情報一覧
     */
	List<MBettingTickets> selectWideByKindAndOfficial(@Param("kind")String kind, @Param("winner")String winner, @Param("runnerUp")String runnerUp, @Param("thirdPlace")String thirdPlace);
}
