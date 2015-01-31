package com.pennappspring15.bullethole.bullethole;

public class Spaceship {

    public int[] sslocation = new int[2];
    public int[] ssvelocities = new int[2];
    public int[] max = new int[2];
    public Spaceship(int xlocation, int ylocation, int maxx, int maxy) {
        sslocation[0] = xlocation - 45;
        sslocation[1] = ylocation - 33;
        max[0] = maxx;
        max[1] = maxy;
    }

    public void setVelocities(int[] velocity) {
        ssvelocities[0] = velocity[0];
        ssvelocities[1] = velocity[1];
    }

    public void updateSS(int frame) {
        sslocation[0] += ssvelocities[0] * frame;
        sslocation[1] += ssvelocities[1] * frame;
        if(sslocation[0] < 0){
            sslocation[0] = 0;
            ssvelocities[0] = 0;
        }
        if(sslocation[1] < 0){
            sslocation[1] = 0;
            ssvelocities[1] = 0;
        }
        if(sslocation[0] > max[0] - 90){
            sslocation[0] = max[0] - 90;
            ssvelocities[0] = 0;
        }
        if(sslocation[1] > max[1] - 90){
            sslocation[1] = max[1] - 90;
            ssvelocities[1] = 0;
        }
    }

    public int[] getLocation() {
        return sslocation;
    }

    public boolean testCollision(int[] bulletlocation) {
        boolean xoverlap = (sslocation[0] < bulletlocation[0]+ 8) && (bulletlocation[0] < sslocation[0] + 59);
        boolean yoverlap = (sslocation[1] < bulletlocation[1] + 14) && (bulletlocation[1] < sslocation[1] + 53);
        boolean overlap = xoverlap && yoverlap;
        return overlap;
    }

}