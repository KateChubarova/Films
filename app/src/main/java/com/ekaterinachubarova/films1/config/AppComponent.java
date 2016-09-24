package com.ekaterinachubarova.films1.config;

import com.ekaterinachubarova.films1.ui.fragment.FilmsListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ekaterinachubarova on 20.09.16.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface AppComponent {

    void inject(FilmsListFragment mainActivity);

}

