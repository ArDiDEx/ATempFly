package fr.ardidex.atempfly.objects;

public class TempFlyPlayer {
    long remaining; // remaining time before player started flying
    final long start; // time in millis from when player started flyinh

    public TempFlyPlayer(long remaining) {
        this.remaining = remaining;
        this.start = System.currentTimeMillis();
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    public long getRemaining() {
        return remaining - (System.currentTimeMillis()-start);
    }
}
