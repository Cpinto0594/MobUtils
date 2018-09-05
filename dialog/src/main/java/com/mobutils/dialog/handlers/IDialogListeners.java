package com.mobutils.dialog.handlers;

import android.content.DialogInterface;

/**
 * Created by carlospinto on 29/10/17.
 */

public interface IDialogListeners {


    public void PositiveButton(DialogInterface dialog, int which);

    public void NegativeButton(DialogInterface dialog, int which);
}
