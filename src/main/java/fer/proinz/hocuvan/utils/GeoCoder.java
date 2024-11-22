package fer.proinz.hocuvan.utils;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import fer.proinz.hocuvan.domain.Event;
import fer.proinz.hocuvan.helpers.EventLocationDTA;
import fer.proinz.hocuvan.helpers.EventLongLat;

public class GeoCoder {

    static JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("300ca3753585497c9c96835638dcc6e8");

    public static EventLocationDTA codeAddressToLatAndLng(Event event) {
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(event.getLocation());
        request.setMinConfidence(1);
        request.setNoAnnotations(false);
        request.setNoDedupe(true);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result
        return new EventLocationDTA(firstResultLatLng.getLat(),firstResultLatLng.getLng(),event);
    }

    public static EventLongLat getEventLongLat(Event event){
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(event.getLocation());
        request.setMinConfidence(1);
        request.setNoAnnotations(false);
        request.setNoDedupe(true);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result
        return new EventLongLat(event.getEventId(),firstResultLatLng.getLat(),firstResultLatLng.getLng());

    }

}
