package com.ozawa.hextcgdeckbuilder.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Each card colour.
 * 
 * @author Chad Kinsella
 */
public enum ColorFlag implements CardEnum {
	
	@SerializedName("Blood")
	BLOOD("Blood"),
	@SerializedName("Diamond")
	DIAMOND("Diamond"),
	@SerializedName("Ruby")
	RUBY("Ruby"),
	@SerializedName("Sapphire")
	SAPPHIRE("Sapphire"),
	@SerializedName("Wild")
	WILD("Wild"),
	@SerializedName("Colorless")
	COLORLESS("Colorless");
	
	private String color;
	
	private ColorFlag(String color){
		this.color = color;
	}
	
	public String getColorFlag(){
		return color;
	}
}
