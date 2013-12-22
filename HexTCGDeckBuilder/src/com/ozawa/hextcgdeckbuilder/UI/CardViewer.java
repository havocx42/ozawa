package com.ozawa.hextcgdeckbuilder.UI;

import android.content.Context;

import com.ozawa.hextcgdeckbuilder.DeckListViewActivity;
import com.ozawa.hextcgdeckbuilder.DeckListViewAdapter;
import com.ozawa.hextcgdeckbuilder.ImageAdapter;
import com.ozawa.hextcgdeckbuilder.MasterDeckActivity;
import com.ozawa.hextcgdeckbuilder.enums.Attribute;
import com.ozawa.hextcgdeckbuilder.enums.CardEnum;
import com.ozawa.hextcgdeckbuilder.enums.CardType;
import com.ozawa.hextcgdeckbuilder.enums.ColorFlag;
import com.ozawa.hextcgdeckbuilder.filter.Filter;
import com.ozawa.hextcgdeckbuilder.hexentities.AbstractCard;

import java.util.List;

/**
 * Created by Laurence on 19/12/13.
 */
public class CardViewer {
    private Filter filter;
    public static List<AbstractCard> cards;
    private ImageAdapter adapter;

    public CardViewer(Context context,List<AbstractCard> abstractCards){
        filter = new Filter();
        cards=abstractCards;
        adapter = new ImageAdapter(context,filter.filter(cards));
    }

    public void toggleAttribute(Attribute attribute) {
        if(filter.isActive(attribute)){
            filter.removeAttribute(attribute);
        }else{
            filter.addAttribute(attribute);
        }
        adapter.updateDeck(filter.filter(cards));
    }

    public void toggleColor(ColorFlag color) {
        if(filter.isActive(color)){
            filter.removeColor(color);
        }else{
            filter.addColor(color);
        }
        adapter.updateDeck(filter.filter(cards));
    }

    public void toggleFilter(CardEnum e){
        if(filter.isActive(e)){
            filter.removeFilter(e);
        }else{
            filter.addFilter(e);
        }
        adapter.updateDeck(filter.filter(cards));
    }

    public void toggleType(CardType type) {
        if(filter.isActive(type)){
            filter.removeType(type);
        }else{
            filter.addType(type);
        }
        adapter.updateDeck(filter.filter(cards));
    }

    public boolean isActive(CardEnum e) {
        return filter.isActive(e);
    }

    public ImageAdapter getAdapter() {
        return adapter;
    }
}
