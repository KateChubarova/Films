package com.ekaterinachubarova.films1.eventbus;

import com.ekaterinachubarova.films1.rest.model.Film;

import java.util.List;

/**
 * Created by ekaterinachubarova on 21.09.16.
 */


public class ReadingEvent extends Event{

    public ReadingEvent(boolean flagNetwork, List<Film> films) {
        super(flagNetwork, films);
    }
}
