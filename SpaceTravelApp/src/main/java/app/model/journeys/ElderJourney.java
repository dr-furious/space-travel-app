package app.model.journeys;

import app.model.users.Guide;

public class ElderJourney extends Journey {
    private int ageThreshold;
    public ElderJourney(long id, String name, int price, int capacity, Guide author, String date, JourneyState journeyState, int ageThreshold) {
        super(id, name, price, capacity, author, date, journeyState);
        this.ageThreshold = ageThreshold;
    }

    public int getAgeThreshold() {
        return ageThreshold;
    }

    public void setAgeThreshold(int ageThreshold) {
        this.ageThreshold = ageThreshold;
    }

    @Override
    public String info() {
        return super.info() + ", age threshold: " + ageThreshold;
    }
}
