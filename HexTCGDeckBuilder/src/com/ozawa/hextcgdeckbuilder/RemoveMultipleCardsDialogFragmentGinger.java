package com.ozawa.hextcgdeckbuilder;

import java.util.HashMap;

import com.ozawa.hextcgdeckbuilder.hexentities.AbstractCard;
import com.ozawa.hextcgdeckbuilder.util.HexUtil;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RemoveMultipleCardsDialogFragmentGinger extends AbstractMultipleCardsDialogFragment {
	EditText	text;

	public RemoveMultipleCardsDialogFragmentGinger() {
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity());

		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.add_multiple_cards_popup_ginger);
		dialog.setTitle("Remove Multiple Cards");

		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xaa000000));

		affirmButton = (Button) dialog.findViewById(R.id.buttonAddCards);
		affirmButton.setText("Remove Cards");
		cancelButton = (Button) dialog.findViewById(R.id.buttonCancelAddCards);
		text = (EditText) dialog.findViewById(R.id.editText1);

		HashMap<AbstractCard, Integer> customDeck = mainActivity.customDeck;

		affirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				int count = Integer.parseInt(text.getText().toString());
				animationArg.repeatCount = count - 1;
				HexUtil.moveImageAnimation(animationArg);
				dialog.dismiss();
				((CustomDeckFragment) fragment).removeCardFromCustomDeck(position, count);
			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				dialog.dismiss();
			}
		});

		return dialog;
	}

}