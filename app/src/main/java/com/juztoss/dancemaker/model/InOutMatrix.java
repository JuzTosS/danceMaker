package com.juztoss.dancemaker.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kirill on 3/20/2016.
 */
public class InOutMatrix implements Parcelable {

    public boolean outLeft = false;
    public boolean outHello = false;
    public boolean outBoth = false;
    public boolean inLeft = false;
    public boolean inHello = false;
    public boolean inBoth = false;

    byte getByte(boolean value) {
        return (byte) (value ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(getByte(outLeft));
        dest.writeByte(getByte(outHello));
        dest.writeByte(getByte(outBoth));
        dest.writeByte(getByte(inLeft));
        dest.writeByte(getByte(inHello));
        dest.writeByte(getByte(inBoth));
    }

    public static final Parcelable.Creator<InOutMatrix> CREATOR
            = new Parcelable.Creator<InOutMatrix>() {
        @Override
        public InOutMatrix createFromParcel(Parcel source) {
            InOutMatrix m = new InOutMatrix();
            m.outLeft = source.readByte() != 0;
            m.outHello = source.readByte() != 0;
            m.outBoth = source.readByte() != 0;
            m.inLeft = source.readByte() != 0;
            m.inHello = source.readByte() != 0;
            m.inBoth = source.readByte() != 0;
            return m;
        }

        @Override
        public InOutMatrix[] newArray(int size) {
            return new InOutMatrix[size];
        }
    };
}
