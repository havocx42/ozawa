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
package com.ozawa.hextcgdeckbuilder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ozawa.hextcgdeckbuilder.UI.CardsViewer;
import com.ozawa.hextcgdeckbuilder.UI.ImageGetter;
import com.ozawa.hextcgdeckbuilder.enums.ImageGetterType;
import com.ozawa.hextcgdeckbuilder.hexentities.AbstractCard;

public class ImageAdapter extends BaseAdapter {
	private Context		mContext;
	public CardsViewer	cardViewer;
	protected Bitmap	back;
	private static int	numberOfColumns		= 3;
	private static int	differenceInHeight	= 26;

	public boolean		isListView			= false;

	public ImageAdapter(Context c, CardsViewer viewer) {
		mContext = c;
		this.cardViewer = viewer;
		back = BitmapFactory.decodeResource(c.getResources(), R.drawable.back);
	}

	@Override
	public int getCount() {
		return cardViewer.getFilteredCardList().size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			// Find the width and height of the screen and set the card
			// dimensions
			WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics metrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(metrics);
			int cardWidth = (metrics.widthPixels / numberOfColumns) - (metrics.widthPixels / 20);
			int cardHeight = cardWidth + differenceInHeight;

			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(cardWidth, cardHeight));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		buildCardImage(cardViewer.getFilteredCardList().get(position), imageView);
		return imageView;
	}

	protected void buildCardImage(AbstractCard card, ImageView imageView) {
		if (imageView.getTag() != null) {
			((ImageGetter) imageView.getTag()).cancel(true);
		}
		imageView.setImageBitmap(back);
		ImageGetter task = new ImageGetter(imageView, mContext, ImageGetterType.CARDTHUMBNAIL, cardViewer.getDeckData());
		task.execute(card);
		imageView.setTag(task);
	}

	public int getDPIFromPixels(Context context, float pixels) {
		float density = context.getResources().getDisplayMetrics().density;

		return (int) (pixels / density);
	}

}
