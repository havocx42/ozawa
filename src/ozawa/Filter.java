package ozawa;

import hexentities.AbstractCard;
import hexentities.Card;
import hexentities.ResourceCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import enums.Attribute;
import enums.CardType;
import enums.ColorFlag;

public class Filter {
	private String						filterString;
	private final EnumSet<Attribute>	attributes;
	private final EnumSet<ColorFlag>	colors;
	private final EnumSet<CardType>		cardTypes;
	private int							minCost;
	private int							maxCost;

	public Filter() {
		attributes = EnumSet.noneOf(Attribute.class);
		colors = EnumSet.allOf(ColorFlag.class);
		cardTypes = EnumSet.allOf(CardType.class);
		minCost = -1;
		maxCost = -1;
	}

	public String getFilterString() {
		return filterString;
	}

	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}

	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}

	public void removeAttribute(Attribute attribute) {
		attributes.remove(attribute);
	}

	public void addColor(ColorFlag color) {
		colors.add(color);
	}

	public void removeColor(ColorFlag color) {
		colors.remove(color);
	}

	public void addType(CardType type) {
		cardTypes.add(type);
	}

	public void removeType(CardType type) {
		cardTypes.remove(type);
	}

	public boolean isActive(Attribute attribute) {
		return attributes.contains(attribute);
	}

	public boolean isActive(CardType type) {
		return cardTypes.contains(type);
	}

	public boolean isActive(ColorFlag color) {
		return colors.contains(color);
	}

	public ArrayList<AbstractCard> filter(AbstractCard[] cards) {
		return (filter(Arrays.asList(cards)));
	}

	public ArrayList<AbstractCard> filter(List<AbstractCard> cards) {
		ArrayList<AbstractCard> result = new ArrayList<AbstractCard>();
		for (AbstractCard abstractCard : cards) {
			if (filterCard(abstractCard)) {
				result.add(abstractCard);
			}

		}
		return result;
	}

	private boolean filterCard(AbstractCard abstractCard) {
		if (colors.size() != 6) {
			boolean include = false;
			if (abstractCard.colorFlags != null) {
				for (ColorFlag cardColor : abstractCard.colorFlags) {
					if (colors.contains(cardColor)) {
						include = true;
					}
				}
			}
			if (!include)
				return false;
		}
		if (abstractCard instanceof hexentities.Card) {
			hexentities.Card card;
			card = (Card) abstractCard;
			if (!attributes.isEmpty()) {
				for (Attribute att : attributes) {
					if (!Arrays.asList(card.attributeFlags).contains(att)) {
						return false;
					}
				}
			}
		}
		return true;

	}

}
