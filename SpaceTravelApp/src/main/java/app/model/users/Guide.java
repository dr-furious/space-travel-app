package app.model.users;

import app.model.Utility;
import app.model.journeys.ElderJourney;
import app.model.journeys.Journey;
import app.model.journeys.JourneyState;
import app.model.observer.Observable;

import java.util.ArrayList;

public class Guide extends Staff {
    private double lockedMoney;
    private ArrayList<Journey> myJourneys;
    private ArrayList<Journey> approvedJourneys;
    private ArrayList<Journey> canceledJourneys;

    static {
        setAccessToken(1111);
    }

    public Guide(String username, int birthYear, int password) {
        super(username, birthYear, password, UserType.GUIDE);
        myJourneys = new ArrayList<>();
        approvedJourneys = new ArrayList<>();
        canceledJourneys = new ArrayList<>();
    }

    @Override
    public void printMyInfo() {

    }
    @Override
    public boolean pay(double amount, User to) {
        try {
            getTravelCard().decreaseBalance(amount);
        } catch (NotEnoughMoneyException exception) {
            System.out.println("Not enough money on card.");
            return false;
        }

        return to.getTravelCard().increaseBalance(amount);
    }

    public void lockMoney(double amount) {
        try {
            getTravelCard().decreaseBalance(amount);
        } catch (NotEnoughMoneyException exception) {
            System.out.println("Not enough money on card.");
            return;
        }

        lockedMoney += amount;
    }

    @Override
    public boolean deposit(double amount) {
        return getTravelCard().increaseBalance(amount);
    }

    @Override
    public boolean withdraw(double amount) {
        try {
            getTravelCard().decreaseBalance(amount);
        } catch (NotEnoughMoneyException exception) {
            System.out.println("Not enough money on card.");
            return false;
        }

        return true;
    }

    public Journey createJourney(ArrayList<Journey> journeys) {
        ArrayList<Long> ids = extractJourneyIDs(journeys);
        long id;

        do {
            id = Utility.generateRandom(1, 1_000_000_000);
        } while (ids.contains(id));

        String[] names = {
                "Starlight Odyssey",
                "Cosmic Quest",
                "Galactic Trek",
                "Solar Adventure",
                "Celestial Cruise",
                "Nebula Expedition",
                "Astral Odyssey",
                "Interstellar Safari",
                "Beyond Infinity",
                "Journey to Mars",
                "Cosmic Explorer",
                "Orbiting Worlds",
                "Lunar Excursion",
                "Space Odyssey",
                "Exploration Mission",
                "Cosmic Crossing",
                "Uncharted Cosmos",
                "Planetary Passage",
                "Cosmic Encounter",
                "Cosmic Journey",
                "Cosmic Passage",
                "Cosmic Tour",
                "Galactic Adventure",
                "Galactic Escape",
                "Galactic Journey",
                "Galactic Passage",
                "Galactic Tour",
                "Galactic Odyssey",
                "Interstellar Adventure",
                "Interstellar Escape",
                "Interstellar Journey",
                "Interstellar Passage",
                "Interstellar Tour",
                "Interstellar Odyssey",
                "Stellar Adventure",
                "Stellar Escape",
                "Stellar Journey",
                "Stellar Passage",
                "Stellar Tour",
                "Stellar Odyssey",
                "Voyage to Mars",
                "Mars Expedition",
                "Mars Odyssey",
                "Mars Quest",
                "Mars Mission",
                "Journey to Saturn",
                "Saturn Expedition",
                "Saturn Odyssey",
                "Saturn Quest",
                "Saturn Mission",
                "Journey to Jupiter",
                "Jupiter Expedition",
                "Jupiter Odyssey",
                "Jupiter Quest",
                "Jupiter Mission",
                "Journey to Venus",
                "Venus Expedition",
                "Venus Odyssey",
                "Venus Quest",
                "Venus Mission",
                "Journey to Mercury",
                "Mercury Expedition",
                "Mercury Odyssey",
                "Mercury Quest",
                "Mercury Mission",
                "Journey to Neptune",
                "Neptune Expedition",
                "Neptune Odyssey",
                "Neptune Quest",
                "Neptune Mission",
                "Journey to Uranus",
                "Uranus Expedition",
                "Uranus Odyssey",
                "Uranus Quest",
                "Uranus Mission",
                "Journey to Pluto",
                "Pluto Expedition",
                "Pluto Odyssey",
                "Pluto Quest",
                "Pluto Mission",
                "Cosmic Escapade",
                "Cosmic Safari",
                "Cosmic Sojourn",
                "Cosmic Travels",
                "Cosmic Venture",
                "Galactic Sojourn",
                "Galactic Travels",
                "Interstellar Safari",
                "Interstellar Sojourn",
                "Interstellar Travels",
                "Stellar Sojourn",
                "Stellar Travels",
                "Voyage Beyond",
                "Voyage Cosmos",
                "Voyage Galaxia",
                "Voyage Nova",
                "Voyage Space",
                "Voyage Universe",
                "Cosmic Discovery",
                "Cosmic Exploration",
                "Cosmic Frontier",
                "Cosmic Odyssey",
                "Cosmic Pathway",
                "Cosmic Quest",
                "Cosmic Trail",
                "Galactic Discovery",
                "Galactic Exploration",
                "Galactic Frontier",
                "Galactic Odyssey",
                "Galactic Pathway",
                "Galactic Quest",
                "Galactic Trail",
                "Interstellar Discovery",
                "Interstellar Exploration",
                "Interstellar"
        };
        int namePicker = (int) Utility.generateRandom(0, names.length);
        String date = Utility.generateRandom(1,13) + "-" + Utility.generateRandom(2030, 2050);
        int price = (int) Utility.generateRandom(1000, 50_001);
        int capacity = (int) Utility.generateRandom(10, 101);
        int ageThreshold = (int) Utility.generateRandom(15, 61);

        int prob = (int) Utility.generateRandom(1,6);
        Journey journey;
        if (prob == 1) {
            journey = new Journey(id, names[namePicker], price, capacity, this, date, JourneyState.PENDING);
        } else {
            journey = new ElderJourney(id, names[namePicker], price, capacity, this, date, JourneyState.PENDING, ageThreshold);
        }

        return journey;
    }

    private ArrayList<Long> extractJourneyIDs(ArrayList<Journey> journeys) {
        ArrayList<Long> ids = new ArrayList<>();
        for (Journey journey : journeys) {
            ids.add(journey.getId());
        }

        return ids;
    }

    @Override
    public void update(Observable subject) {
        if (subject.getClass() != Journey.class && subject.getClass() != ElderJourney.class ) {
            return;
        }

        Journey journey = (Journey) subject;
        JourneyState journeyState = journey.getJourneyState();

        if (journeyState == JourneyState.PENDING && journey.getAuthor() == this) {
            System.out.println("Seeeee ya");
            if (myJourneys.contains(journey)) {
                return;
            }
            myJourneys.add(journey);
            return;
        }

        if (journeyState == JourneyState.CANCELLED && journey.getAuthor() == this) {
            if (canceledJourneys.contains(journey)) {
                return;
            }
            canceledJourneys.add(journey);
            return;
        }

        if (journeyState == JourneyState.AVAILABLE && journey.getAuthor() == this) {
            if (approvedJourneys.contains(journey)) {
                return;
            }
            approvedJourneys.add(journey);
        }
    }

    public ArrayList<Journey> getMyJourneys() {
        return myJourneys;
    }

    public ArrayList<Journey> getApprovedJourneys() {
        return approvedJourneys;
    }

    public ArrayList<Journey> getCanceledJourneys() {
        return canceledJourneys;
    }
}
