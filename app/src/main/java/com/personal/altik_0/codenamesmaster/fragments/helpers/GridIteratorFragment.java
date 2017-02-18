package com.personal.altik_0.codenamesmaster.fragments.helpers;

import android.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.personal.altik_0.codenamesmaster.R;

import java.util.function.Consumer;

public abstract class GridIteratorFragment extends Fragment {
    protected static final int GRID_WIDTH  = 5;
    protected static final int GRID_HEIGHT = 5;

    protected void iterateOverButtonGrid(
            final View parentView,
            Consumer<GridIteratorPayload> loopAction) {
        final GridLayout wordGrid = (GridLayout)parentView.findViewById(R.id.word_grid_layout);
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                int i = x + (y * GRID_WIDTH);
                final Button button = (Button) wordGrid.getChildAt(i);
                loopAction.accept(new GridIteratorPayload(button, x, y));
            }
        }
    }
}
