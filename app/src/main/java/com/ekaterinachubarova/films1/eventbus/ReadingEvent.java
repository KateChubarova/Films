package com.ekaterinachubarova.films1.eventbus;

import com.ekaterinachubarova.films1.rest.model.Film;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ekaterinachubarova on 21.09.16.
 */

@Getter
@Setter
public class ReadingEvent {
    public static final int INFORMATION_FROM_DATABASE = 0;
    public static final int INFORMATION_FROM_NETWORK = 1;

    private List<Film> films;
    private int flag;

    public ReadingEvent(List<Film> films, int flag) {
        this.films = films;
        this.flag = flag;
    }
}
