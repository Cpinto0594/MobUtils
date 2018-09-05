package com.mobutils.http;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.mobutils.http.converter.HttpManagerConverterFactory;
import com.mobutils.http.handlers.HttpAbstractResult;
import com.mobutils.http.headers.HttpHeaders;
import com.mobutils.http.request.HttpManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        /*
        HttpManager.setRequestHeader("Cookie", "ci_session_estudiantes_simon=7f83b63f5e75f211ecb7e8aa6c8f076dc5b0c038;;path=/;LB=webportal-01");
        new HttpManager("http://portal.unisimon.edu.co/estudiantes/index.php/notas/programas")
                .setTimeOutTime(5000)
                .post(new HttpAbstractResult() {
                    @Override
                    public void onError(Exception excp, HttpHeaders responseHeaders) {
                        super.onError(excp, responseHeaders);
                    }

                    @Override
                    public void onSuccess(Object resultValue, HttpHeaders responseHeaders, int responseCode) {
                        Log.d("Result" , resultValue.toString());
                    }
                });
        */

        assertEquals("com.mobutils.http.test", appContext.getPackageName());
    }


}
