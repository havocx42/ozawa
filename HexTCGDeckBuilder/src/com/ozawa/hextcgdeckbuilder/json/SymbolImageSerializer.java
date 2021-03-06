/*******************************************************************************
 * Hex TCG Deck Builder
 *     Copyright ( C ) 2014  Chad Kinsella, Dave Kerr and Laurence Reading
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.ozawa.hextcgdeckbuilder.json;

import java.lang.reflect.Type;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.ozawa.hextcgdeckbuilder.UI.SymbolTemplate;


public class SymbolImageSerializer implements JsonDeserializer<SymbolTemplate> {
	private Resources	resources;
	private Context		androidContext;

	public SymbolImageSerializer(Context i_androidContext) {
		androidContext = i_androidContext;
		resources = androidContext.getResources();
	}

	@Override
	public SymbolTemplate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		Gson gson = new Gson();
		SymbolTemplate template = gson.fromJson(json, SymbolTemplate.class);

		template.templateId = resources.getIdentifier(template.imageName, "drawable", androidContext.getPackageName());
		return template;
	}

}
