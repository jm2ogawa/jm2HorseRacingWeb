package com.example.demo.form;

import java.io.Serializable;
import java.util.List;

import com.example.demo.dto.MBettingTickets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MainteTicketResponseForm implements Serializable  {
	/**
	 * メッセージ
	 */
	private String message;
	/**
	 * 券種マスタ情報一覧
	 */
	private List<MBettingTickets> mbtList;
	/**
	 * チェックボックス
	 */
	private List<String> chk;

}
