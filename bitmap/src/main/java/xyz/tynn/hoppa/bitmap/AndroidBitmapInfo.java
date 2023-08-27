//  Copyright 2023 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.bitmap;

import static android.graphics.Bitmap.Config.ALPHA_8;
import static android.graphics.Bitmap.Config.ARGB_4444;
import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.graphics.Bitmap.Config.RGB_565;
import static java.lang.System.loadLibrary;
import static java.util.Objects.hash;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class AndroidBitmapInfo {

    static {
        loadLibrary("bitmapinfo");
    }

    private final Bitmap.Config format;
    private final int height, width, stride;

    AndroidBitmapInfo(int width, int height, int stride, int format) {
        this.format = formatToConfig(format);
        this.width = width;
        this.height = height;
        this.stride = stride;
    }

    @Nullable
    public Bitmap.Config getFormat() {
        return format;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getStride() {
        return stride;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndroidBitmapInfo that = (AndroidBitmapInfo) o;
        return width == that.width && height == that.height && stride == that.stride
                && format == that.format;
    }

    @Override
    public int hashCode() {
        return hash(format, height, width, stride);
    }

    @NonNull
    @Override
    public String toString() {
        return "AndroidBitmapInfo{format=" + format +
                ", width=" + width + ", height=" + height +
                ", stride=" + stride + '}';
    }

    private static Bitmap.Config formatToConfig(int format) {
        switch (format) {
            case 0:
                return null;
            case 1:
                return ARGB_8888;
            case 4:
                return RGB_565;
            case 7:
                return ARGB_4444;
            case 8:
                return ALPHA_8;
            default:
                throw new IllegalArgumentException("format=" + format);
        }
    }

    @NonNull
    public static native AndroidBitmapInfo of(@NonNull Bitmap bitmap);
}
