package com.ekaterinachubarova.films1.eventbus;

import com.ekaterinachubarova.films1.rest.model.Film;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ekaterinachubarova on 30.09.16.
 */
@Getter
@Setter
@AllArgsConstructor(suppressConstructorProperties = true)
public abstract class Event {
    public static boolean INFORMATION_FROM_NETWORK = true;

    private boolean flagNetwork;
    private List<Film> films;
}
