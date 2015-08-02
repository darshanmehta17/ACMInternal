package studios.slick.acminternal.database;

import com.orm.SugarRecord;

/**
 * Created by Darshan on 17/06/15.
 */
public class Events extends SugarRecord<Events> {

    private int eventId;
    private String eventName;
    private String eventDetails;
    private String eventVenue;
    private String eventDate;
    private String eventTime;

    public Events(int eventId, String eventName, String eventDetails, String eventVenue, String eventDate, String eventTime){
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventVenue = eventVenue;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }
}
