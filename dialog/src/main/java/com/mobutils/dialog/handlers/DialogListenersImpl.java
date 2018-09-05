package com.mobutils.dialog.handlers;

import android.content.DialogInterface;

/**
 * Created by CarlosPinto on 5/06/2017.
 */


public abstract class DialogListenersImpl implements IDialogListeners {
    @Override
    public void PositiveButton(DialogInterface dialog, int which) {
        dialog.cancel();
    }

    @Override
    public void NegativeButton(DialogInterface dialog, int which) {
        dialog.cancel();
    }

}
