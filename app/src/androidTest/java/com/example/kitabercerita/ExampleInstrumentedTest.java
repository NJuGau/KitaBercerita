package com.example.kitabercerita;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.kitabercerita.model.User;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws ExecutionException, InterruptedException {
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        FirebaseApp.initializeApp(appContext);
        HashMap<String, String> postMap = new HashMap<>();
        postMap.put("postDescription", "he");
//        postMap.put("postUserId", "asndkjasndj");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Tasks.await(db.collection("Post").add(postMap));
    }
}