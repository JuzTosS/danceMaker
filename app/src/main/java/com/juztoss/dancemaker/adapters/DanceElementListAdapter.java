package com.juztoss.dancemaker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.juztoss.dancemaker.R;
import com.juztoss.dancemaker.model.DanceElement;

import java.util.List;

/**
 * Created by Kirill on 2/27/2016.
 */
public class DanceElementListAdapter extends BaseAdapter implements ListAdapter {

    private List<DanceElement> mDanceElements;
    private Context context;
    private ElementListListener mOnElementListListener;


    public DanceElementListAdapter(Context context, List<DanceElement> danceElements) {
        mDanceElements = danceElements;
        this.context = context;
    }

    public void setDeleteListener(ElementListListener onElementListListener) {
        mOnElementListListener = onElementListListener;
    }

    @Override
    public int getCount() {
        return mDanceElements.size();
    }

    @Override
    public Object getItem(int position) {
        return mDanceElements.get(position);
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
            view = inflater.inflate(R.layout.list_element, null);
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

                if (mOnElementListListener != null) {
                    mOnElementListListener.onDelete(el);
                }
                mDanceElements.remove(position);
                notifyDataSetChanged();
            }
        });

        final SwipeLayout swipeLayout = (SwipeLayout)view.findViewById(R.id.swipe_list_element);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnElementListListener != null && swipeLayout.getOpenStatus() == SwipeLayout.Status.Close)
                    mOnElementListListener.onClick(el);
            }
        });

        return view;
    }

    public interface ElementListListener {
        void onDelete(DanceElement element);
        void onClick(DanceElement element);
    }

}
