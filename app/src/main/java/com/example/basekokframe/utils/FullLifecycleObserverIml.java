package com.example.basekokframe.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.orhanobut.logger.Logger;

public class FullLifecycleObserverIml implements LifecycleEventObserver {

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        Logger.d("lifeCycle:" + event.name()+"+==="+source.getClass().getSimpleName());
        switch (event) {
            case ON_CREATE:
                onCreate(source);
                break;
            case ON_START:
                onStart(source);
                break;
            case ON_RESUME:
                onResume(source);
                break;
            case ON_PAUSE:
                onPause(source);
                break;
            case ON_STOP:
                onStop(source);
                break;
            case ON_DESTROY:
                onDestroy(source);
                break;
        }
    }

    public void onCreate(LifecycleOwner owner) {
    }

    public void onStart(LifecycleOwner owner) {
    }

    public void onResume(LifecycleOwner owner) {
    }

    public void onPause(LifecycleOwner owner) {
    }

    public void onStop(LifecycleOwner owner) {
    }

    public void onDestroy(LifecycleOwner owner) {
    }
}
