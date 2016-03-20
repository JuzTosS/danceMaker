package com.juztoss.dancemaker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.model.DanceElement;
import com.juztoss.dancemaker.model.DanceSequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kirill on 2/27/2016.
 */
public class DanceSequenceViewListAdapter extends BaseAdapter implements ListAdapter {

    final int INVALID_ID = -1;

    private List<DanceElement> mTempElementsIds;
    private DanceSequence mDanceSequence;
    private Context context;
    private SequenceViewDeleteListener mOnSequenceDeleteListener;


    public DanceSequenceViewListAdapter(Context context, DanceSequence danceSequence) {
        mDanceSequence = danceSequence;
        mTempElementsIds = new ArrayList<>();
        mTempElementsIds.addAll(mDanceSequence.getElements());

        this.context = context;
    }

    public void setDeleteListener(SequenceViewDeleteListener onSequenceDeleteListener) {
        mOnSequenceDeleteListener = onSequenceDeleteListener;
    }

    @Override
    public int getCount() {
        return mDanceSequence.getElements().size();
    }

    @Override
    public Object getItem(int position) {
        return mDanceSequence.getElements().get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_sequence_element, null);
        }

        TextView nameField = (TextView) view.findViewById(R.id.nameField);
        TextView lengthField = (TextView) view.findViewById(R.id.lengthField);
        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.buttonDelete);

        final DanceElement el = (DanceElement) getItem(position);
        nameField.setText(el.getName());
        String original = view.getContext().getString(R.string.length_with_number);
        lengthField.setText(String.format(original, el.getLength()));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnSequenceDeleteListener != null) {
                    mOnSequenceDeleteListener.onDelete(el);
                }
                mDanceSequence.delete(el);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void swapElements(int first, int second) {
        DanceElement temp = mDanceSequence.getElements().get(first);
        mDanceSequence.getElements().set(first, mDanceSequence.getElements().get(second));
        mDanceSequence.getElements().set(second, temp);
    }

    public interface SequenceViewDeleteListener {
        void onDelete(DanceElement element);
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mDanceSequence.getElements().size()) {
            return INVALID_ID;
        }
        DanceElement el = mDanceSequence.getElements().get(position);
        return  mTempElementsIds.indexOf(el);
    }

//    @Override
//    public boolean hasStableIds() {
//        return true;
//    }
}
