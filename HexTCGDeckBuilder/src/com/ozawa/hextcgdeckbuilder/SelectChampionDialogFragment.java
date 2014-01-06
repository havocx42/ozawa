package com.ozawa.hextcgdeckbuilder;

import java.lang.reflect.Field;
import java.util.List;

import com.ozawa.hextcgdeckbuilder.R.drawable;
import com.ozawa.hextcgdeckbuilder.UI.SelectChampionArrayAdapter;
import com.ozawa.hextcgdeckbuilder.database.DatabaseHandler;
import com.ozawa.hextcgdeckbuilder.hexentities.Champion;
import com.ozawa.hextcgdeckbuilder.hexentities.Deck;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SelectChampionDialogFragment extends DialogFragment {
	
	private ListView listView;
	private RelativeLayout relativeLayout;
	private LinearLayout linearLayout;
	private Deck currentCustomDeck;
	private Champion selectedChampion;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		final Dialog dialog = new Dialog(getActivity());
		DatabaseHandler dbHandler = new DatabaseHandler(getActivity());
		final List<Champion> allChampions = dbHandler.allChampions;
		currentCustomDeck = ((DeckUIActivity) getActivity()).currentCustomDeck;
		
		if(currentCustomDeck != null && currentCustomDeck.champion != null){
			selectedChampion = currentCustomDeck.champion;
		}else{
			selectedChampion = allChampions.get(0);
		}
		
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.select_champion_popup);
		
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setTitle("Load Deck");
		
		SelectChampionArrayAdapter adapter = new SelectChampionArrayAdapter(getActivity(), R.layout.select_champion_popup, allChampions);
		
		relativeLayout = (RelativeLayout) dialog.findViewById(R.id.relLayoutChampionSelected);
		
		final TextView selectedChampionName = (TextView) relativeLayout.findViewById(R.id.tvChampionName);
		selectedChampionName.setText(selectedChampion.name);
		final TextView selectedChampionGameText = (TextView) relativeLayout.findViewById(R.id.tvChampionGameText);
		selectedChampionGameText.setText(selectedChampion.gameText);
		final ImageView selectedChampionPortrait = (ImageView) relativeLayout.findViewById(R.id.imageChampionPortrait);		
		if(selectedChampion.hudPortrait != null){
			selectedChampionPortrait.setImageResource(getResourceID(selectedChampion.hudPortrait));						
		}else if(selectedChampion.hudPortraitSmall != null){
			selectedChampionPortrait.setImageResource(getResourceID(selectedChampion.hudPortraitSmall));
		}
		
		int portraitDimensions = getScreenWidth(getActivity())/3;
		selectedChampionPortrait.getLayoutParams().width = portraitDimensions;
		selectedChampionPortrait.getLayoutParams().height = (int) (portraitDimensions * 1.22);
		
		listView = (ListView) dialog.findViewById(R.id.championList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Champion champion = (Champion) listView.getItemAtPosition(position);
				if(champion != null){
					selectedChampionName.setText(champion.name);
					selectedChampionGameText.setText(champion.gameText);
					
					int championPortraitID = -1;
					if(champion.hudPortrait != null){
						championPortraitID = getResourceID(champion.hudPortrait);						
					}else if(champion.hudPortraitSmall != null){
						championPortraitID = getResourceID(champion.hudPortraitSmall);
					}
					
					if(championPortraitID != -1){
						selectedChampionPortrait.setImageResource(championPortraitID);
					}
					selectedChampion = allChampions.get(position);
				}
			}

       }); 
		
		linearLayout = (LinearLayout) dialog.findViewById(R.id.linLayoutSelectChampButtons);
		
		Button selectChampion = (Button) linearLayout.findViewById(R.id.buttonSaveSelectedChampion);
		Button cancel = (Button) linearLayout.findViewById(R.id.buttonCancelSelectChampion);
		
		selectChampion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((DeckUIActivity) getActivity()).currentCustomDeck.champion = selectedChampion;
				 if(((DeckUIActivity) getActivity()).currentCustomDeck.champion == selectedChampion){
					 Toast.makeText(getActivity().getApplicationContext(), "Champion selected." , Toast.LENGTH_SHORT).show();
					 ((DeckUIActivity) getActivity()).updateCustomDeckData();
					 dialog.dismiss();
				 }else{
					 Toast.makeText(getActivity().getApplicationContext(), "Failed to select champion. Please try again." , Toast.LENGTH_SHORT).show();
				 }
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	
		return dialog;
	}
	
	private int getResourceID(String resourceName){
		int id = -1;
		try {
		    Class<drawable> res = R.drawable.class;
		    Field field = res.getField(resourceName);
		    id = field.getInt(null);
		}
		catch (Exception e) {
			Toast.makeText(getActivity().getApplicationContext(), "Could not find champion portrait." , Toast.LENGTH_SHORT).show();
		}
		
		return id;
	}
	
	@SuppressLint("NewApi")
    private int getScreenWidth(Context context){
    	int measuredWidth = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        //different methods based on SDK version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            wm.getDefaultDisplay().getSize(size);
            measuredWidth = size.x;
        } else {
            Display d = wm.getDefaultDisplay();
            measuredWidth = d.getWidth();
        }
        return measuredWidth;
    }
}