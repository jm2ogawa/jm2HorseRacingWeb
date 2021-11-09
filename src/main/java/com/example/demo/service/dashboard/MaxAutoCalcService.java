package com.example.demo.service.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.common.maxautocalc.WheelMaxCalc;
import com.example.demo.common.maxautocalc.impl.MaxCalcInterface;
import com.example.demo.dto.TIntervalValues;

/**
 * MAX値自動計算機能 Service
 */
@Service
public class MaxAutoCalcService {

	/**
	 * 次のMAX値を算出する
	 * @return
	 */
	public int getConsectiveMax(MaxCalcInterface calc, List<TIntervalValues> values, int consective) {
		//基準値を末尾からとる
		List<TIntervalValues> tags = calc.createTags(values, consective);
		//基準値と比較したMAX値リストをとる
		List<TIntervalValues> maxList = calc.createMaxValueList(values, tags, consective);
		//MAX値リストの最大値を返却する
		return calc.getMaxValueInList(maxList).getIntervalValue();
	}

	/**
	 * 次のX段下げMAX値を算出する
	 * @return
	 */
	public int getConsectiveStepDownMax(MaxCalcInterface calc, List<TIntervalValues> values, int consective, int stepdown) {
		//基準値を末尾からとる
		List<TIntervalValues> tags = calc.createTags(values, consective);
		//段下げのMAX値を生成する
		return calc.createStepDownMaxValue(values,tags,consective,stepdown).getIntervalValue();
	}

	/**
	 * 輪のMAX値を算出する
	 * @param values
	 * @param type
	 * @return
	 */
	public int getWheelMax(List<TIntervalValues> values, int type) {
		WheelMaxCalc calc = new WheelMaxCalc();
		//基準値をとる
		int tags = calc.createTags(values, type);
		//基準値と比較したMAX値リストをとる
		List<TIntervalValues> maxList = calc.createMaxValueList(values, tags, type);
		//MAX値リストの最大値を返却する
		return calc.getMaxValueInList(maxList).getIntervalValue();
	}


	/**
	 * 輪のX段下げMAX値を算出する
	 * @param values
	 * @param type
	 * @return
	 */
	public int getWheelStepdownMax(List<TIntervalValues> values, int type, int stepdown) {
		return 0;
	}
}
