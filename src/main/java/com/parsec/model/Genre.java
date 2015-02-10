package com.parsec.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Genre entity.
 *
 * Created: 09.02.2015
 *
 * @author Oleksandr Zamkovyi
 * @since ???
 */

@Entity
public class Genre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public Genre() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
