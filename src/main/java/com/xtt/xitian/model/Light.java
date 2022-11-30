package com.xtt.xitian.model;

import java.io.Serializable;
import java.util.Objects;

public class Light implements Serializable {
    String param1;
    String param2;

    public Light(String param1, String param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Light)) return false;
        Light light = (Light) o;
        return Objects.equals(param1, light.param1) && Objects.equals(param2, light.param2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(param1, param2);
    }

    @Override
    public String toString() {
        return "Light{" +
                "param1='" + param1 + '\'' +
                ", param2='" + param2 + '\'' +
                '}';
    }
}
