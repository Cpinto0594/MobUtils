package com.mobutils.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;

import com.mobutils.dialog.handlers.IDialogListeners;


/**
 * Created by CarlosPinto on 5/06/2017.
 */

public class DialogBuilder {

    private Handler handler;
    private volatile Dialog dialog;
    private volatile ProgressDialog pDIalog;
    private Context context;
    public static String DEFAULT_TITLE = "Informacion...!";
    public static String DEFAULT_MESSAGE = "Mensaje de Informacion";
    public boolean isShowingProgress;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public static DialogBuilder getInstance(Context context, Handler handler) {
        return new DialogBuilder(context, handler);
    }

    DialogBuilder(Context context, Handler handler) {
        this.setContext(context);
        this.setHandler(handler);
    }

    public DialogBuilder showConfirmation(final String message, final IDialogListeners listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog = buildAlertDialog(context, handler, message, DEFAULT_TITLE, DialogType.CONFIRMATION, listener);
                } catch (Exception e) {
                    Log.w("Show Error", e.getMessage());
                }
            }
        });
        return this;
    }

    public DialogBuilder showInfo(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog = buildAlertDialog(context, handler, message, null, DialogType.INFO, null);
                } catch (Exception e) {
                    Log.w("Show Error", e.getMessage());
                }
            }
        });
        return this;
    }

    public DialogBuilder showInfo(final String message, final IDialogListeners listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog = buildAlertDialog(context, handler, message, DEFAULT_TITLE, DialogType.INFO, listener);
                } catch (Exception e) {
                    Log.w("Show Error", e.getMessage());
                }
            }
        });
        return this;
    }

    public DialogBuilder showInfo(final String message, final String title, final IDialogListeners listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog = buildAlertDialog(context, handler, message, title, DialogType.INFO, listener);
                } catch (Exception e) {
                    Log.w("Show Error", e.getMessage());
                }
            }
        });
        return this;
    }

    public DialogBuilder showError(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog = buildAlertDialog(context, handler, message, null, DialogType.ERROR, null);
                } catch (Exception e) {
                    Log.w("Show Error", e.getMessage());
                }
            }
        });

        return this;
    }


    public DialogBuilder showError(final String message, final IDialogListeners listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog = buildAlertDialog(context, handler, message, null, DialogType.ERROR, listener);
                } catch (Exception e) {
                    Log.w("Show Error", e.getMessage());
                }
            }
        });

        return this;
    }

    public DialogBuilder showError(final String message, final String title, final IDialogListeners listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    dialog = buildAlertDialog(context, handler, message, title, DialogType.ERROR, listener);
                } catch (Exception e) {
                    Log.w("Show Error", e.getMessage());
                }
            }
        });

        return this;
    }

    public DialogBuilder showProgress(final String message, final boolean closable, final boolean showSpinner, DialogInterface.OnCancelListener cancelListener) {

        try {
            pDIalog = BuildProgress(context, message, null, closable, showSpinner, cancelListener);
            pDIalog.show();
            isShowingProgress = true;
        } catch (Exception e) {
            isShowingProgress = false;
            return null;
        }
        return this;
    }

    public DialogBuilder showProgress(final String message, final boolean closable, final boolean showSpinner) {

        try {
            pDIalog = BuildProgress(context, message, null, closable, showSpinner, null);
            pDIalog.show();
            isShowingProgress = true;
        } catch (Exception e) {
            isShowingProgress = false;
            return null;
        }
        return this;
    }

    public DialogBuilder showProgress(final String message, final String title, final boolean closable, final boolean showSpinner) {
        try {
            pDIalog = BuildProgress(context, message, title, closable, showSpinner, null);
            pDIalog.show();
            isShowingProgress = true;
        } catch (Exception e) {
            isShowingProgress = false;
            return null;
        }
        return this;
    }


    public void ShowDialog(final Dialog dialogo_) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                dialogo_.show();
            }
        });
    }

    public void ShowDialog() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }


    public void CloseProgress() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (pDIalog != null && pDIalog.isShowing()) {
                    pDIalog.dismiss();
                    isShowingProgress = false;
                }
            }
        });
    }

    public void CloseProgress(final DialogInterface.OnDismissListener listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null && pDIalog != null) {
                    pDIalog.setOnDismissListener(listener);
                }
                if (pDIalog != null && pDIalog.isShowing()) {
                    pDIalog.dismiss();
                    isShowingProgress = false;
                }
            }
        });
    }

    public void CloseProgress(final ProgressDialog dialog, final DialogInterface.OnDismissListener listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null && dialog != null) {
                    dialog.setOnDismissListener(listener);
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    isShowingProgress = false;
                }
            }
        });
    }

    public enum DialogType {
        CONFIRMATION, INFO, ERROR;
    }


    public static AlertDialog buildAlertDialog(Context context, Handler handler, String message, String title, DialogType type,
                                               final IDialogListeners listeners
    ) throws Exception {
        if (DialogUtils.isEmpty(context)) throw new Exception("No se ha especificado el contexto.");
        if (DialogUtils.isEmpty(handler)) throw new Exception("No se ha especificado el contexto.");
        message = DialogUtils.isEmpty(message) ? null : message;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        DialogInterface.OnClickListener negativo = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!DialogUtils.isEmpty(listeners)) {
                    listeners.NegativeButton(dialog, which);
                } else {
                    dialog.cancel();
                }
            }
        };
        DialogInterface.OnClickListener positivo = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!DialogUtils.isEmpty(listeners)) {
                    listeners.PositiveButton(dialog, which);
                } else {
                    dialog.cancel();
                }
            }
        };

        switch (type) {

            case CONFIRMATION:
                builder.setPositiveButton("CONFIRM", positivo);
                builder.setNegativeButton("CANCEL", negativo);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(false);
                break;
            case INFO:
                builder.setPositiveButton("OK", positivo);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setCancelable(true);
                break;
            case ERROR:
                builder.setPositiveButton("OK", positivo);
                builder.setIcon(android.R.drawable.stat_notify_error);
                builder.setCancelable(true);
                break;
            default:
                throw new Exception("Tipo no identificado.");
        }

        return builder.create();
    }

    public static ProgressDialog BuildProgress(Context context, String message, String title, boolean closable, boolean showSpinner, DialogInterface.OnCancelListener cancelListener) throws Exception {
        if (DialogUtils.isEmpty(context)) throw new Exception("No se ha especificado el contexto.");
        ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage(message);
        progress.setTitle(DialogUtils.isEmpty(title) ? null : message);
        progress.setCancelable(closable);
        progress.setIndeterminate(true);
        progress.setOnCancelListener(cancelListener);
        if (showSpinner)
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return progress;

    }

}

