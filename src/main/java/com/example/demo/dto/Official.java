package com.example.demo.dto;

import java.io.Serializable;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レースの人気番号の着順―覧
 * @author hirofumi.ogawa
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Official implements Serializable  {
	/**
	 * 1着馬
	 */
    @NonNull
	private String winner;
	/**
	 * 2着馬
	 */
    @NonNull
	private String runnerUp;
	/**
	 * 3着馬
	 */
	private String thirdPlace;
}
