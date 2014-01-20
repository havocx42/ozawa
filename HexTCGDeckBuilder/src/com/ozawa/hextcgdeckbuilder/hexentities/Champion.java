package com.ozawa.hextcgdeckbuilder.hexentities;

import com.google.gson.annotations.SerializedName;

public class Champion {

	@SerializedName("m_Name")
	public String name;
	@SerializedName("m_SetId")
	public GlobalIdentifier setID;
	@SerializedName("m_HudPortrait")
	public String hudPortrait;
	@SerializedName("m_HudSmall")
	public String hudPortraitSmall;
	@SerializedName("m_GameText")
	public String gameText;
	
	public String getSetID(){
		return this.setID.gUID;
	}
}
