package com.juztoss.dancemaker.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.model.DanceSequence;

import java.util.List;

interface SequenceListListener {
    void onDelete(DanceSequence Sequence);
    void onClick(DanceSequence Sequence);
}

/**
 * Created by Kirill on 2/27/2016.
 */
public class DanceSequenceListAdapter extends BaseAdapter implements ListAdapter {

    private List<DanceSequence> mDanceSequences;
    private Context context;
    private SequenceListListener mOnSequenceListListener;


    public DanceSequenceListAdapter(Context context, List<DanceSequence> danceSequences) {
        mDanceSequences = danceSequences;
        this.context = context;
    }

    public void setListener(SequenceListListener onSequenceListListener) {
        mOnSequenceListListener = onSequenceListListener;
    }

    @Override
    public int getCount() {
        return mDanceSequences.size();
    }

    @Override
    public Object getItem(int position) {
        return mDanceSequences.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_sequence, null);
        }

        TextView nameField = (TextView) view.findViewById(R.id.nameField);
        TextView lengthField = (TextView) view.findViewById(R.id.lengthField);
        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.buttonDelete);

        final DanceSequence seq = (DanceSequence) getItem(position);
        nameField.setText(seq.getName());
        String original = view.getContext().getString(R.string.length_with_number);
        lengthField.setText(String.format(original, seq.getLength()));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSequenceListListener != null)
                    mOnSequenceListListener.onDelete(seq);

                mDanceSequences.remove(position);
                notifyDataSetChanged();
            }
        });

        final SwipeLayout swipeLayout = (SwipeLayout)view.findViewById(R.id.swipe_list_element);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSequenceListListener != null && swipeLayout.getOpenStatus() == SwipeLayout.Status.Close)
                    mOnSequenceListListener.onClick(seq);
            }
        });

        return view;
    }


}
