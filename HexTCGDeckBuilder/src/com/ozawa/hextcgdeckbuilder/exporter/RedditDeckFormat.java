package com.ozawa.hextcgdeckbuilder.exporter;

import java.util.ArrayList;
import java.util.List;

import com.ozawa.hextcgdeckbuilder.UI.customdeck.Deck;
import com.ozawa.hextcgdeckbuilder.database.DatabaseHandler;
import com.ozawa.hextcgdeckbuilder.hexentities.AbstractCard;
import com.ozawa.hextcgdeckbuilder.hexentities.Card;
import com.ozawa.hextcgdeckbuilder.hexentities.GemResource;
import com.ozawa.hextcgdeckbuilder.hexentities.ResourceCard;

public class RedditDeckFormat implements IDeckFormat {
	
	private DatabaseHandler databaseHandler;
	private Deck deck;
	
	public RedditDeckFormat(DatabaseHandler databaseHandler_in,Deck deckin){
		databaseHandler=databaseHandler_in;
		deck = deckin;
	}

	@Override
	public String formatDeck() {
		StringBuilder builder = new StringBuilder();
		ArrayList<ResourceCard> resources = new ArrayList<ResourceCard>();
		ArrayList<Card> troops = new ArrayList<Card>();
		ArrayList<AbstractCard> others = new ArrayList<AbstractCard>();
		
		for(AbstractCard card:deck.getDeckCardList()){
			if(card instanceof ResourceCard){
				resources.add((ResourceCard)card);
			}else if(card instanceof Card&&((Card)card).isTroop()){
				troops.add((Card)card);
			}else{
				others.add(card);
			}
		}
		
		for (ResourceCard card : resources){
			appendCardRedditFormat(builder,card);
		}
		builder.append("\n");
		for (Card card : troops){
			appendCardRedditFormat(builder,card);
		}
		builder.append("\n");
		for (AbstractCard card : others){
			appendCardRedditFormat(builder,card);
		}
		builder.append("\n");
		
		return builder.toString();
	}

	@Override
	public String getName() {
		return "Deck name would go here if I could find it";
	}
	
	@Override
	public String toString(){
		return "Reddit";
	}
	
	
	private void appendCardRedditFormat(StringBuilder builder, AbstractCard card){
		
		List<GemResource> gems = databaseHandler.getAllGemResourcesForDeck(deck.getCurrentDeck().getID());
		//gems.get(0).
		
		builder.append(deck.getDeckData().get(card));
		builder.append(" X **");
		builder.append(card.name);
		builder.append("**");
		//if(card)
		builder.append("  \n");
	}

}
