package fer.proinz.hocuvan.helpers;

public class StatisticsDTO {
    long numberOfEvents;
    long numberOfOrganizers;
    long numberOfVisitors;

    public StatisticsDTO() {
    }

    public StatisticsDTO(long numberOfEvents, long numberOfOrganizers, long numberOfVisitors) {
        this.numberOfEvents = numberOfEvents;
        this.numberOfOrganizers = numberOfOrganizers;
        this.numberOfVisitors = numberOfVisitors;
    }

    public long getNumberOfEvents() {
        return numberOfEvents;
    }

    public void setNumberOfEvents(long numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    public long getNumberOfOrganizers() {
        return numberOfOrganizers;
    }

    public void setNumberOfOrganizers(long numberOfOrganizers) {
        this.numberOfOrganizers = numberOfOrganizers;
    }

    public long getNumberOfVisitors() {
        return numberOfVisitors;
    }

    public void setNumberOfVisitors(long numberOfVisitors) {
        this.numberOfVisitors = numberOfVisitors;
    }
}
