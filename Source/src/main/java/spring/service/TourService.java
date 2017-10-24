package spring.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.entity.Tour;
import spring.repositories.TourRepository;

/**
 * Created by olulrich on 20.10.17.
 */

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;
    
    public TourRepository getTourRepository() {
        return tourRepository;
    }

    public List<Tour> getToursForDriver(String username) {
        List<Tour> tours = new ArrayList<Tour>();
        tourRepository.findByDriver(username).forEach(tours::add);
        return tours;
    }

    /**
     * Returns a <code>List</code> of <code>Tours</code> that are assigned to this driver
     * and are scheduled for today
     * @param username the username of the driver
     * @return a <code>List</code> containing the driver's <code>Tours</code> for today
     */
    public List<Tour> getCurrentToursForDriver(String username) {
        List<Tour> tours = new ArrayList<Tour>();
        LocalDate today = LocalDate.now();
        tourRepository.findByDriverAndDeliveryStartDate(username, today).forEach(tours::add);
        return tours;
    }

    /**
     * Returns a <code>List</code> of <code>Tours</code> that are assigned to this driver
     * and are scheduled for this week
     * @param username the username of the driver
     * @param day the weekday
     * @return a <code>List</code> containing the driver's <code>Tours</code> for the specified day
     */
    public List<Tour> getToursForDriverAndDay(String username, DayOfWeek day) {
        List<Tour> tours = new ArrayList<Tour>();
        LocalDate tourDate = LocalDateTime.now().with(day).toLocalDate();
        tourRepository.findByDriverAndDeliveryStartDate(username, tourDate).forEach(tours::add);
        return tours;
    }

    public List<Tour> getTours() {
    	List<Tour> tours = new ArrayList<Tour>();
    	tourRepository.findAll().forEach(tours::add);
    	return tours;
    }
    
    public List<Tour> getSortedTours(String sortBy) {
    	assert sortBy != null;
    	List<Tour> tours = getTours();
    	tours.sort(new TourComparator(sortBy));
    	return tours;
    }
    
}

class TourComparator implements Comparator<Tour> {
	
	private String sortBy;
	
	public TourComparator(String sortBy) {
		this.sortBy = sortBy;
	}
	
	@Override
	public int compare(Tour tour1, Tour tour2) {
		if (sortBy.equals("Date/Time")) {
			if (tour1.getDeliveryStartDate().toEpochDay() > tour2.getDeliveryStartDate().toEpochDay()) {
    			return 1;
    		} else if (tour1.getDeliveryStartDate().toEpochDay() < tour2.getDeliveryStartDate().toEpochDay()) {
    			return -1;
    		} else if (tour1.getDeliveryStartTime().toNanoOfDay() > tour2.getDeliveryStartTime().toNanoOfDay()) {
    			return 1;
    		} else if (tour1.getDeliveryStartTime().toNanoOfDay() < tour2.getDeliveryStartTime().toNanoOfDay()) {
    			return -1;
    		} else return 0;
		} else if (sortBy.equals("Startin Location")) {
			
		} else if (sortBy.equals("Target Location")) {
			
		} else if (sortBy.equals("Driver")) {
			
		} else if (sortBy.equals("Cargo")) {
			
		}
		return 0;
	}
}