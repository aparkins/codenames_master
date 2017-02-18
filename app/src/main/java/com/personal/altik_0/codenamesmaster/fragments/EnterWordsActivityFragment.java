package com.personal.altik_0.codenamesmaster.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.personal.altik_0.codenamesmaster.R;
import com.personal.altik_0.codenamesmaster.fragments.helpers.GridIteratorFragment;
import com.personal.altik_0.codenamesmaster.model.IncompleteWordGrid;

import java.util.Optional;


public class EnterWordsActivityFragment extends GridIteratorFragment {

    private IncompleteWordGrid wordGridModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        final View parentView = inflater.inflate(R.layout.fragment_enter_words, container, false);
        wordGridModel = new IncompleteWordGrid(GRID_WIDTH, GRID_HEIGHT);
        enterEditTextContext(parentView);
        return parentView;
    }

    private void updateButtonText(final View parentView) {
        iterateOverButtonGrid(parentView, payload -> {
            String blankWord = getResources().getString(R.string.blank_word);
            payload.button.setText(wordGridModel
                    .getPosition(payload.x,payload.y)
                    .orElse(blankWord));
        });
    }

    private void enterEditTextContext(final View parentView) {
        iterateOverButtonGrid(parentView, payload -> {
            payload.button.setOnClickListener(
                    getEditTextOnClickListener(parentView, payload.x, payload.y));
            payload.button.setOnLongClickListener(
                    getSwapTextOnLongClickListener(parentView, payload.x, payload.y));
        });
    }

    private void enterSwapTextContext(
            final View parentView,
            final Button otherButton,
            int x1, int y1) {
        iterateOverButtonGrid(parentView, payload -> {
            int x2 = payload.x;
            int y2 = payload.y;
            payload.button.setOnClickListener(getSwapTextOnClickListener(
                    parentView,
                    otherButton,
                    x1, y1,
                    x2, y2));
            payload.button.setOnLongClickListener(null);
        });
    }

    private Button.OnClickListener getEditTextOnClickListener(
            final View parentView,
            int x, int y) {
        final Context context = parentView.getContext();
        return b -> {
            final EditText editText = new EditText(context);
            new AlertDialog.Builder(context)
                    .setTitle("Enter Word")
                    .setView(editText)
                    .setPositiveButton("Ok", (dialog, which) -> {
                        wordGridModel.setPosition(x, y, Optional.of(editText.getText().toString()));
                        updateButtonText(parentView);
                    })
                    .setCancelable(true)
                    .show();
        };
    }

    private Button.OnLongClickListener getSwapTextOnLongClickListener(
            final View parentView,
            int x, int y) {
        return b -> {
            Button button = (Button)b;
            button.setEnabled(false);
            enterSwapTextContext(parentView, button, x, y);
            return true;
        };
    }

    private Button.OnClickListener getSwapTextOnClickListener(
            final View parentView,
            final Button otherButton,
            int x1, int y1,
            int x2, int y2) {
        return b -> {
            wordGridModel.swapPositions(x1, y1, x2, y2);
            updateButtonText(parentView);
            otherButton.setEnabled(true);
            enterEditTextContext(parentView);
        };
    }
}
