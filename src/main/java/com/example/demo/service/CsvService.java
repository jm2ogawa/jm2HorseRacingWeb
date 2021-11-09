package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.utils.DateUtil;
import com.example.demo.dao.CsvDao;
import com.example.demo.dto.CsvRow;
import com.example.demo.dto.Race;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * CSV機能サービス
 */
@Service
public class CsvService {

	/**
	 * システム構成サービス
	 */
//	@Autowired
//	private ConfigService configService;

	/**
	 * CSV機能DAO
	 */
	@Autowired
	private CsvDao csvDao;

	/**
	 * 次回レースCSVファイルの読み込み確認
	 */
	public List<Race> existsRaadNextRace() {

		Calendar c = DateUtil.createToday();
		int day = DateUtil.getNextTargetDay(c);

		return csvDao.selectRacesForExistsByDate(
				String.valueOf(c.get(Calendar.YEAR)),
				String.valueOf(c.get(Calendar.MONTH)+1),
				String.valueOf(day));
	}

	/**
	 * レースCSVファイル一覧取得
	 * @param
	 * @return 検索結果
	 */
	public File[] findCsvFiles() {
		String dirPath = ResourceBundle.getBundle("application").getString("default.dir.path");
		File f = new File(dirPath);
		File[] files = f.listFiles();
	    return files;
	}

	/**
	 * レースCSVファイル一覧取得
	 * @param
	 * @return 検索結果
	 */
	public List<CsvRow> createCsvRows(File[] files) {
		List<CsvRow> csvRows = new ArrayList<CsvRow>();
		for(File file : files) {
			CsvRow csvRow  = new CsvRow(csvRows.size(), file, "", "");
			String filename = file.getName();
			String raceYYYYMMDD = filename.substring(0,filename.lastIndexOf('.'));
			String yyyyMMdd = raceYYYYMMDD.substring(4);
			String yyyy = yyyyMMdd.substring(0,4);
			String mm = DateUtil.trimDateZeroSuppress(yyyyMMdd.substring(4,6));
			String dd = DateUtil.trimDateZeroSuppress(yyyyMMdd.substring(6));
			List<Race> races =  csvDao.selectRacesForExistsByDate(yyyy, mm, dd);
			if(races.size() == 0) {
				//検索結果がなければ、読み込んでおらずボタンの設定を活性
				csvRow.setCsvReadButtonStatus("false");
			} else {
				//検索結果があった場合、読み込み済みのためボタンの設定を非活性
				csvRow.setCsvReadButtonStatus("true");
			}
			csvRows.add(csvRow);
		}
		//降順ソート、最新のレースファイルが上に来るように
		Collections.sort(csvRows, Comparator.comparing(CsvRow::getNo).reversed());
	    return csvRows;
	}

	/**
	 * レースCSVファイル読み込み
	 * @param
	 * @return 検索結果
	 */
	public int readDataFromCsv(String filepath) {
		int rslt = 0;
		try {
			Reader reader = Files.newBufferedReader(Paths.get(filepath));
	        CsvToBean<Race> csvToBean = new CsvToBeanBuilder<Race>(reader).withType(Race.class).build();
	        List<Race> items = csvToBean.parse();
			rslt = csvDao.raceCsvInfoRegister(items);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return rslt;
	}

}
