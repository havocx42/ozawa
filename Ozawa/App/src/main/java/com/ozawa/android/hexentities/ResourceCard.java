package com.ozawa.android.hexentities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.annotations.SerializedName;

/**
 * A Resource Card.
 * 
 * @author Chad Kinsella
 */
public class ResourceCard extends AbstractCard {
	
	@SerializedName("m_ResourceThresholdGranted")
	public String resourceThresholdGranted;
	@SerializedName("m_CurrentResourcesGranted")
	public int currentResourcesGranted;
	@SerializedName("m_MaxResourcesGranted")
	public int maxResourcesGranted;

    @Override
    public Bitmap getCardBitmap(Context context) {
        if (image != null) {
            return image;
        }
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(cardImagePath.split("\\.")[0], "drawable",
                context.getPackageName());

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resourceId, o);
        int scale = 1;
        while (o.outWidth / scale / 2 >= 200)
            scale *= 2;
        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap fg = BitmapFactory.decodeResource(resources, resourceId, o2);
        image = fg;
        return fg;
    }
}
