package com.mobutils.async.AsyncResponses;

public interface IGenericAsync<Params,Result> {

    public  Result doInBackground(Params... params);
    public  void onPostExecute(Result result);
    public  void onPreExecute();

}