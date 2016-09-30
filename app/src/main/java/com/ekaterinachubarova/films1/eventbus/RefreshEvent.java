package com.ekaterinachubarova.films1.eventbus;

import com.ekaterinachubarova.films1.rest.model.Film;

import java.util.List;

/**
 * Created by ekaterinachubarova on 30.09.16.
 */

public class RefreshEvent extends Event{
    public RefreshEvent(boolean flagNetwork, List<Film> films) {
        super(flagNetwork, films);
    }
}
