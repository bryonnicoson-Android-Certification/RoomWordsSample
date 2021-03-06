package com.bryonnicoson.roomwordssample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This is the adapter for the RecyclerView that displays the list of words.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Word> mWords;  // cached copy of words

    WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.wordItemView.setText(current.getWord());
        } else {
            holder.wordItemView.setText(R.string.no_word);
        }
    }

    /**
     * Associate a list of words with this adapater
     */
    void setWords(List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null)             // mWords is initially null
            return mWords.size();
        else return 0;
    }

    /**
     * Get the word at a given position.
     * This method is useful for identifying which word was clicked or swiped in methods
     * that handle user events.
     *
     * @param position
     * @return The words at the given position
     */
    public Word getWordAtPosition(int position) {
        return mWords.get(position);
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}
