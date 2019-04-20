package ru.home.geekbrains.java.core_03.threading3.entities.geo;


import com.sun.istack.internal.NotNull;
import ru.home.geekbrains.java.core_03.threading3.entities.infrastructure.Port;

/**
 * Гавань, может иметь порт
 */
public class Harbor extends Geo {

    private Port port = null;

    public Harbor(@NotNull String name) {
        super(name);
    }

    public Harbor(@NotNull String name, int length, Port port) {
        super(name, length);
        this.port = port;
    }

    public Port getPort() {
        return port;
    }

}