package app.model.journeys;

import app.model.users.Guide;

/**
 * ElderJourney extends Journey and it adds ageThreshold as a new field - only Travelers that are above certain age can add this
 * journey
 */
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
        return super.info() + "\nAge Threshold: " + ageThreshold + " years";
    }
}
