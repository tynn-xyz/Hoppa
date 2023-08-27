//  Copyright 2023 Christian Schmitz
//  SPDX-License-Identifier: Apache-2.0

package xyz.tynn.hoppa.bitmap;

import static android.graphics.Bitmap.Config.ALPHA_8;
import static android.graphics.Bitmap.Config.ARGB_4444;
import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.graphics.Bitmap.Config.RGB_565;
import static android.graphics.Bitmap.createBitmap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class AndroidBitmapInfoTest {

    @Test
    public void init_should_throw_on_unknown_format() {
        assertThrows(IllegalArgumentException.class, () ->
                new AndroidBitmapInfo(0, 0, 0, -1));
        assertThrows(IllegalArgumentException.class, () ->
                new AndroidBitmapInfo(0, 0, 0, 2));
        assertThrows(IllegalArgumentException.class, () ->
                new AndroidBitmapInfo(0, 0, 0, 3));
        assertThrows(IllegalArgumentException.class, () ->
                new AndroidBitmapInfo(0, 0, 0, 5));
        assertThrows(IllegalArgumentException.class, () ->
                new AndroidBitmapInfo(0, 0, 0, 6));
        assertThrows(IllegalArgumentException.class, () ->
                new AndroidBitmapInfo(0, 0, 0, 9));
        assertThrows(IllegalArgumentException.class, () ->
                new AndroidBitmapInfo(0, 0, 0, 10));
        assertThrows(IllegalArgumentException.class, () ->
                new AndroidBitmapInfo(0, 0, 0, 11));
    }

    @Test
    public void format_should_map_to_config() {
        assertNull(new AndroidBitmapInfo(0, 0, 0, 0).getFormat());
        assertEquals(ARGB_8888, new AndroidBitmapInfo(0, 0, 0, 1).getFormat());
        assertEquals(RGB_565, new AndroidBitmapInfo(0, 0, 0, 4).getFormat());
        assertEquals(ARGB_4444, new AndroidBitmapInfo(0, 0, 0, 7).getFormat());
        assertEquals(ALPHA_8, new AndroidBitmapInfo(0, 0, 0, 8).getFormat());
    }

    @Test
    public void equal_should_include_all_fields() {
        AndroidBitmapInfo info = new AndroidBitmapInfo(2, 1, 3, 1);
        assertEquals(info, info);
        assertNotEquals(info, null);
        assertEquals(info, new AndroidBitmapInfo(2, 1, 3, 1));
        assertNotEquals(info, new AndroidBitmapInfo(3, 1, 3, 1));
        assertNotEquals(info, new AndroidBitmapInfo(2, 2, 3, 1));
        assertNotEquals(info, new AndroidBitmapInfo(2, 1, 4, 1));
        assertNotEquals(info, new AndroidBitmapInfo(2, 1, 3, 4));
    }

    @Test
    public void width_height_stride_should_not_adapt() {
        AndroidBitmapInfo info = new AndroidBitmapInfo(2, 1, 3, 0);
        assertEquals(2, info.getWidth());
        assertEquals(1, info.getHeight());
        assertEquals(3, info.getStride());
    }

    @Test
    public void toString_should_contain_all_fields() {
        assertEquals(
                "AndroidBitmapInfo{format=null, width=2, height=1, stride=2}",
                new AndroidBitmapInfo(2, 1, 2, 0).toString());
        assertEquals(
                "AndroidBitmapInfo{format=ARGB_8888, width=2, height=2, stride=3}",
                new AndroidBitmapInfo(2, 2, 3, 1).toString());
        assertEquals(
                "AndroidBitmapInfo{format=RGB_565, width=3, height=1, stride=3}",
                new AndroidBitmapInfo(3, 1, 3, 4).toString());
        assertEquals(
                "AndroidBitmapInfo{format=ARGB_4444, width=2, height=7, stride=3}",
                new AndroidBitmapInfo(2, 7, 3, 7).toString());
        assertEquals(
                "AndroidBitmapInfo{format=ALPHA_8, width=2, height=2, stride=2}",
                new AndroidBitmapInfo(2, 2, 2, 8).toString());
    }

    @Test
    public void info_should_reflect_createBitmap_ARGB_8888() {
        AndroidBitmapInfo info = new AndroidBitmapInfo(2, 1, 8, 1);
        assertEquals(info, AndroidBitmapInfo.of(createBitmap(2, 1, ARGB_8888)));
    }

    @Test
    public void info_should_reflect_createBitmap_RGB_565() {
        AndroidBitmapInfo info = new AndroidBitmapInfo(2, 1, 4, 4);
        assertEquals(info, AndroidBitmapInfo.of(createBitmap(2, 1, RGB_565)));
    }

    @Test
    public void info_should_reflect_createBitmap_ARGB_4444() {
        AndroidBitmapInfo info = new AndroidBitmapInfo(2, 1, 8, 1);
        assertEquals(info, AndroidBitmapInfo.of(createBitmap(2, 1, ARGB_4444)));
    }

    @Test
    public void info_should_reflect_createBitmap_ALPHA_8() {
        AndroidBitmapInfo info = new AndroidBitmapInfo(2, 1, 2, 8);
        assertEquals(info, AndroidBitmapInfo.of(createBitmap(2, 1, ALPHA_8)));
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    public void info_should_throw_on_null() {
        assertThrows(NullPointerException.class, () -> AndroidBitmapInfo.of(null));
    }
}
