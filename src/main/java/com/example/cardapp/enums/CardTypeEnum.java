package com.example.cardapp.enums;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public enum CardTypeEnum {

	SAVING, CURRENT, FIXED;

	public static List<CardTypeEnum> getEnums() {
		return Arrays.asList(CardTypeEnum.values());
	}

	public static String getEnumFromName(String cardTypeName) {
		return getEnums().stream().filter(x -> StringUtils.equalsAnyIgnoreCase(x.name(), cardTypeName)).findFirst()
				.get().name();

	}
}
