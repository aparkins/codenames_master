package com.personal.altik_0.codenamesmaster;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;


public class EnterWordsActivityFragment extends Fragment {

    public EnterWordsActivityFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        final View parentView = inflater.inflate(R.layout.fragment_enter_words, container, false);
        enterEditTextContext(parentView);
        return parentView;
    }

    private void enterEditTextContext(final View parentView) {
        final GridLayout wordGrid = (GridLayout)parentView.findViewById(R.id.word_grid_layout);
        final Context context = parentView.getContext();
        for (int i = 0; i < wordGrid.getChildCount(); i++) {
            final Button button = (Button)wordGrid.getChildAt(i);
            button.setOnClickListener(getEditTextOnClickListener(context));
            button.setOnLongClickListener(getSwapTextOnLongClickListener(parentView));
        }
    }

    private void enterSwapTextContext(final View parentView, final Button otherButton) {
        final GridLayout wordGrid = (GridLayout)parentView.findViewById(R.id.word_grid_layout);
        for (int i = 0; i < wordGrid.getChildCount(); i++) {
            final Button button = (Button)wordGrid.getChildAt(i);
            button.setOnClickListener(getSwapTextOnClickListener(parentView, otherButton));
            button.setOnLongClickListener(null);
        }
    }

    private Button.OnClickListener getEditTextOnClickListener(final Context context) {
        return b -> {
            final EditText editText = new EditText(context);
            new AlertDialog.Builder(context)
                    .setTitle("Enter Word")
                    .setView(editText)
                    .setPositiveButton(
                            "Ok",
                            (dialog, which) -> ((Button)b).setText(editText.getText()))
                    .setCancelable(true)
                    .show();
        };
    }

    private Button.OnLongClickListener getSwapTextOnLongClickListener(final View parentView) {
        return b -> {
            Button button = (Button)b;
            button.setEnabled(false);
            enterSwapTextContext(parentView, button);
            return true;
        };
    }

    private Button.OnClickListener getSwapTextOnClickListener(
            final View parentView,
            final Button otherButton) {
        return b -> {
            Button button = (Button)b;
            CharSequence tmp = otherButton.getText();
            otherButton.setText(button.getText());
            button.setText(tmp);
            otherButton.setEnabled(true);
            enterEditTextContext(parentView);
        };
    }
}
