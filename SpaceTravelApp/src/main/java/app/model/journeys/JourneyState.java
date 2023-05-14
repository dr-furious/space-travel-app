package app.model.journeys;

/**
 * The Different Types of JourneyState
 * PENDING means that the Guide created new Journey, and it needs to be approved by an Owner
 * AVAILABLE means that the Owner has approved the Journey, or a Journey that was previously FULLY_BOOKED is again available
 * CANCELLED means that the Owner has rejected the Journey
 * FULLY_BOOKED means that the maximum capacity of the Journey was reached and Travelers cannot add it until it's AVAILABLE again
 */
public enum JourneyState {
    PENDING,
    AVAILABLE,
    CANCELLED,
    FULLY_BOOKED
}
