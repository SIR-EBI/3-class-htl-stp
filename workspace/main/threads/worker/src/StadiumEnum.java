import java.util.Random;

public enum StadiumEnum {

    INITIALIZE, STATUS1(3,7), STATUS2(6,10), STATUS3(2,10), DONE;

    private int duration;

    StadiumEnum() {
        this.duration = 0;
    }

    StadiumEnum(int beginn, int end) {
        this.duration = end - beginn;
    }

    public int getDuration() {
        if (duration == 0)
            return 0;
        return new Random().nextInt(duration) + duration;
    }

}
