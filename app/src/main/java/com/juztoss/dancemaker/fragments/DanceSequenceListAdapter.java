package com.juztoss.dancemaker.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.model.DanceSequence;

import java.util.List;

interface SequenceDeleteListener {
    void onDelete(DanceSequence Sequence);
}

/**
 * Created by Kirill on 2/27/2016.
 */
public class DanceSequenceListAdapter extends BaseAdapter implements ListAdapter {

    private List<DanceSequence> mDanceSequences;
    private Context context;
    private SequenceDeleteListener mOnSequenceDeleteListener;


    public DanceSequenceListAdapter(Context context, List<DanceSequence> danceSequences) {
        mDanceSequences = danceSequences;
        this.context = context;
    }

    public void setDeleteListener(SequenceDeleteListener onSequenceDeleteListener) {
        mOnSequenceDeleteListener = onSequenceDeleteListener;
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
        lengthField.setText("Length " + seq.getLength());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnSequenceDeleteListener != null) {
                    mOnSequenceDeleteListener.onDelete(seq);
                }
                mDanceSequences.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }


}
