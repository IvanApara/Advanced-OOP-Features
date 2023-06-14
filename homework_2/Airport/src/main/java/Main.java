import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        List<Flight> flights = findPlanesLeavingInTheNextTwoHours(airport);
        flights.stream()
                .sorted(Comparator.comparing(flight -> flight.getDate().getHours()))
                .sorted(Comparator.comparing(flight -> flight.getDate().getMinutes()))
                .forEach(System.out::println);
    }

    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        LocalDateTime now = LocalDateTime.now();

        return airport.getTerminals().stream()
                .map(Terminal::getFlights)
                .flatMap(Collection::stream)
                .filter(f -> f.getType().equals(Flight.Type.DEPARTURE))
                .filter(f -> f.getDate().getHours() > now.getHour())
                .filter(f -> f.getDate().getHours() < (now.getHour() + 2))
                .collect(Collectors.toList());
    }

}