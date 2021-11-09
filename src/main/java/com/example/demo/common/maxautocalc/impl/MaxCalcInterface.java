package com.example.demo.common.maxautocalc.impl;

import java.util.List;

import com.example.demo.dto.TIntervalValues;

public interface MaxCalcInterface extends ConsectiveInterface {

	//基準値より大きい間隔値をMAX値リストにして返却する
	List<TIntervalValues> createMaxValueList(List<TIntervalValues> values, List<TIntervalValues> tags, int consective);

	//MAX値リストから最も大きい値を返却する
	TIntervalValues getMaxValueInList(List<TIntervalValues> maxList);

	//段下げのMAX値を生成する
	TIntervalValues createStepDownMaxValue(List<TIntervalValues> values, List<TIntervalValues> tags, int stepdown, int tag);

	//基準値の終了判定を行う
	boolean jugdeTags(List<TIntervalValues> tags, int consective);

	//基準値を0未満にならないように減算する
	int subTag(int tag);

	//段下げリストからMAX値を返却する
	TIntervalValues getMaxValueInStepdownList(List<TIntervalValues> stepdownList, int stepdown);
}
