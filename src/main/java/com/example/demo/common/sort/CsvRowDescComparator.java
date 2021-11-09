package com.example.demo.common.sort;

import java.util.Comparator;

import com.example.demo.dto.CsvRow;
/**
 * 未使用
 * @author jm2og
 *
 */
public class CsvRowDescComparator implements Comparator<CsvRow>  {
	//compare メソッドで、負の数、0、正の数 (int) を返してその大小を定義
	//第一引数を小さいとする場合は負の数、大きいとする場合は正の数を返せばOK
	@Override
	public int compare(CsvRow cr1, CsvRow cr2) {
		return cr1.getNo() < cr2.getNo()  ? 1 : 1;
	}
}
