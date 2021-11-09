package com.example.demo.form.dashboard;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 行データ登録チェックリクエストフォーム
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashBoardCheckRequestForm implements Serializable {
	private String raceday;
	private String rownum;
}
