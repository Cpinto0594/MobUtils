package com.mobutils.async;

import android.os.AsyncTask;
import android.util.Log;

import com.mobutils.async.AsyncResponses.AsyncResponses;
import com.mobutils.async.AsyncResponses.IGenericAsync;


/**
 * Created by CarlosPinto on 9/06/2017.
 */

public class DoAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    IGenericAsync<Params, Result> callback;
    private AsyncResponses.OnPreExecuteClass onPreExecuteEventListener;
    private AsyncResponses.OnBackgroundClass<Params> onBackGroundExecuteEventListener;
    private AsyncResponses.OnPostExecuteClass<Result> onPostExecuteEventListener;
    private AsyncResponses.OnErrorExecuteClass onErrortExecuteEventListener;
    private final String TAG = "DOASYNCTASK";

    public DoAsyncTask(IGenericAsync<Params, Result> callback) {
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(Result result) {
        if (callback == null) {
            return;
        }
        try {

            callback.onPostExecute(result);
            if (this.onPostExecuteEventListener != null) {
                this.onPostExecuteEventListener.OnPostExecute(result);
            }

        } catch (Exception e) {
            Log.e(TAG, "OnPostExecute: " + getCause(e));
            if (this.onErrortExecuteEventListener != null) {
                this.onErrortExecuteEventListener.OnErrorThrown(e);
            }
        }

    }

    @Override
    protected void onPreExecute() {
        if (callback == null) {
            return;
        }
        try {

            callback.onPreExecute();
            if (this.onPreExecuteEventListener != null) {
                this.onPreExecuteEventListener.OnPreExecute();
            }

        } catch (Exception e) {
            Log.e(TAG, "OnPreExecute: " + getCause(e));
            if (this.onErrortExecuteEventListener != null) {
                this.onErrortExecuteEventListener.OnErrorThrown(e);
            }
        }

    }

    @Override
    protected Result doInBackground(Params... params) {
        if (callback == null) {
            return null;
        }

        try {

            Result resultado = callback.doInBackground(params);
            if (this.onBackGroundExecuteEventListener != null) {
                this.onBackGroundExecuteEventListener.OnBackground(params);
            }
            return resultado;

        } catch (Exception e) {
            Log.e(TAG, "OnBackgroundExecute: " + getCause(e));
            if (this.onErrortExecuteEventListener != null) {
                this.onErrortExecuteEventListener.OnErrorThrown(e);
            }
            return null;
        }
    }

    //Another Binding Callback
    public DoAsyncTask<Params, Progress, Result> onPreExecuteEvent(AsyncResponses.OnPreExecuteClass event) {
        this.onPreExecuteEventListener = event;
        return this;
    }

    public DoAsyncTask<Params, Progress, Result> onPostExecuteEvent(AsyncResponses.OnPostExecuteClass<Result> event) {
        this.onPostExecuteEventListener = event;
        return this;
    }

    public DoAsyncTask<Params, Progress, Result> onBackGroundExecuteEvent(AsyncResponses.OnBackgroundClass<Params> event) {
        this.onBackGroundExecuteEventListener = event;
        return this;
    }

    public DoAsyncTask<Params, Progress, Result> onErrorExecuteEvent(AsyncResponses.OnErrorExecuteClass event) {
        this.onErrortExecuteEventListener = event;
        return this;
    }

    private String getCause(Exception e) {
        return AsyncUtils.constructStackTrace(e);
    }


}

