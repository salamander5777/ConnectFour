package com.example.connectfour;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.Objects;

public class BoardFragment extends Fragment {
    private final String GAME_STATE = "gameState";
    private ConnectFourGame mGame;
    private GridLayout mGrid;

    //Initializes the game and sets up the UI
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_board, container, false);

        mGame = new ConnectFourGame();

        //Adding the same click handler to all grid buttons.
        mGrid = parentView.findViewById(R.id.board_grid);

        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button gridButton = (Button) mGrid.getChildAt(i);
            gridButton.setOnClickListener(this::onButtonClick);
        }

        //Restarting the game if there's no saved instance.
        if (savedInstanceState == null) {
            startGame();
        } else {
            String gameState = savedInstanceState.getString(GAME_STATE);

            //Setting the game board to what's in the saved instance.
            mGame.setState(gameState);
            setDisc();
        }

        return parentView;
    }

    //Called when button is clicked.
    private void onButtonClick(View view) {
        //.Find the button's row and col.
        int buttonIndex = mGrid.indexOfChild(view);
        int row = buttonIndex / ConnectFourGame.ROW;
        int col = buttonIndex % ConnectFourGame.COL;

        mGame.selectDisc(row, col);
        setDisc();

        if (mGame.isGameOver()) {
            // Display a toast congratulating the player
            String winner = (mGame.getPlayer() == ConnectFourGame.BLUE) ? "Blue" : "Red";
            Toast.makeText(requireContext(), winner + " player wins!", Toast.LENGTH_SHORT).show();

            // Start a new game
            mGame.newGame();
            setDisc(); // Update the board after starting a new game
        }
    }

    //Setting up new game,.
    public void startGame() {
        mGame.newGame();

        setDisc();
    }

    public void setDisc() {
        for (int buttonIndex = 0; buttonIndex < mGrid.getChildCount(); buttonIndex++) {
            //Instantiate an instance of class Button for the current member of the collection.
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);

            //Find the button’s row and column.
            int row = buttonIndex / ConnectFourGame.COL;
            int col = buttonIndex % ConnectFourGame.COL;

            //Instantiate an instance of class Drawable for the three drawable discs.
            Drawable circleWhite = DrawableCompat.wrap(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.circle_white)));
            Drawable circleBlue = DrawableCompat.wrap(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.circle_blue)));
            Drawable circleRed = DrawableCompat.wrap(Objects.requireNonNull(ContextCompat.getDrawable(requireActivity(), R.drawable.circle_red)));

            //Setting each Drawable object based on the value of the element in the ConnectFourGame.
            int disc = mGame.getDisc(row, col);
            if (disc == ConnectFourGame.BLUE) {
                gridButton.setBackground(circleBlue);
            } else if (disc == ConnectFourGame.RED) {
                gridButton.setBackground(circleRed);
            } else {
                gridButton.setBackground(circleWhite);
            }
        }
    }

    //Saves current game.
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }
}