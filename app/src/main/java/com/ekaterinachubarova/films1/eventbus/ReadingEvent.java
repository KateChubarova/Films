package com.ekaterinachubarova.films1.eventbus;

import com.ekaterinachubarova.films1.rest.model.Film;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ekaterinachubarova on 21.09.16.
 */

@Getter
@Setter
@AllArgsConstructor(suppressConstructorProperties = true)
public class ReadingEvent {
    public static final boolean INFORMATION_FROM_DATABASE = false;
    public static final boolean INFORMATION_FROM_NETWORK = true;

    private List<Film> films;
    private boolean flag;

}
