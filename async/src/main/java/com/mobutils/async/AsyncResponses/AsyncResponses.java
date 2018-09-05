package com.mobutils.async.AsyncResponses;

/**
 * Created by carlospinto on 31/10/17.
 */

public class AsyncResponses {

    public abstract static class OnPreExecuteClass {
        public abstract void OnPreExecute();
    }

    public abstract static class OnBackgroundClass<Params> {
        public abstract void OnBackground(Params... params);
    }

    public abstract static class OnPostExecuteClass<Result> {
        public abstract void OnPostExecute(Result result);
    }

    public abstract static class OnErrorExecuteClass {
        public abstract void OnErrorThrown(Exception result);
    }

}
