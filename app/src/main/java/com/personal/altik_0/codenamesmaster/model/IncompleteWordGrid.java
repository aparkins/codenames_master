package com.personal.altik_0.codenamesmaster.model;

import java.util.Optional;

public class IncompleteWordGrid {
    private int gridWidth = 5;
    private int gridHeight = 5;
    private String[] data;

    public IncompleteWordGrid(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        data = new String[gridWidth * gridHeight];
    }

    public Optional<String> getPosition(int x, int y) {
        return Optional.ofNullable(data[getIndex(x, y)]);
    }

    public void setPosition(int x, int y, Optional<String> value) {
        data[getIndex(x, y)] = value.orElse(null);
    }

    public void swapPositions(int x1, int y1, int x2, int y2) {
        Optional<String> tmp = getPosition(x1, y1);
        setPosition(x1, y1, getPosition(x2, y2));
        setPosition(x2, y2, tmp);
    }

    private int getIndex(int x, int y) {
        assertValidPosition(x, y);
        return x + (y * gridWidth);
    }

    private void assertValidPosition(int x, int y) {
        if (x < 0 || x > gridWidth) {
            throw new RuntimeException("Illegal x position: " + x + " for width: " + gridWidth);
        }
        if (y < 0 || y > gridHeight) {
            throw new RuntimeException("Illegal y position: " + y + " for height: " + gridHeight);
        }
    }
}
