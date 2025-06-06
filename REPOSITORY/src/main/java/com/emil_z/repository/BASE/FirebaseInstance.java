package com.emil_z.repository.BASE;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseInstance {
    private static volatile FirebaseInstance _instance = null;
    public static FirebaseApp app;

    private FirebaseInstance(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("ultimate-tic-tac-toe-project")
                .setApplicationId("1:273203212742:android:5e05863b0c6b3a1722c2c7")
                .setApiKey("AIzaSyCqKjSsYdTE9JuwOTWBxpm55qxTiUFNQxs")
                .setStorageBucket("ultimate-tic-tac-toe-project.firebasestorage.app")
                .build();

        app = FirebaseApp.initializeApp(context, options, "ultimate-tic-tac-toe-project");
    }

    public static FirebaseInstance instance(Context context) {
        if (_instance == null) {  // 1st check
            synchronized (FirebaseInstance.class) {
                if (_instance == null){ // 2nd check
                    _instance = new FirebaseInstance(context);
                }
            }
        }

        return _instance;
    }
}