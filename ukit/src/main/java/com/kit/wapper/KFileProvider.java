package com.kit.wapper;

import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.kit.UIKit;
import com.kit.utils.Logger;

import java.io.File;

public class KFileProvider extends FileProvider {
    public final static String AUTHORITY_PATH = ".uikit.fileprovider";

    /**
     * @param file
     * @return Uri
     */
    public static Uri getUriForFile(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = UIKit.getContext().getApplicationInfo().packageName + AUTHORITY_PATH;
            uri = getUriForFile(UIKit.getContext(), authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        Logger.e("KFileProvider", "uri = " + (null == uri ? "null" : uri.toString()));
        return uri;
    }
}
