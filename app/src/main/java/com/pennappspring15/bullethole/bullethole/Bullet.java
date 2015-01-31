package com.pennappspring15.bullethole.bullethole;

public class Bullet {

    public int[] location = new int [2];
    public int[] velocity = new int [2];

    public Bullet(int xlocation, int ylocation, int xvelocity, int yvelocity) {
        location[0] = xlocation - 13;
        location[1] = ylocation - 13;
        velocity[0] = xvelocity;
        velocity[1] = yvelocity;
    }


    public void bulletUpdate (int frame) {
        int xchange = velocity[0] * frame;
        location[0] += xchange;

        int ychange = velocity[1] * frame;
        location[1] += ychange;
    }

    public int[] getLocation() {
        return location;
    }
}