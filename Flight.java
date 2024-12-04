import java.util.Date;

public class Flight {
	private String departureLocation;
	private String landingLocation;
	private Date departureDate;
	private Date landingDate;
	private int capacity;
	private Ticket[] ticketList;
	
	
	public Flight(String departureLocation, String landingLocation, Date departureDate, Date landingDate,
			int capacity) {
		this.departureLocation = departureLocation;
		this.landingLocation = landingLocation;
		this.departureDate = departureDate;
		this.landingDate = landingDate;
		this.capacity = capacity;
		
		this.ticketList=new Ticket[capacity];
		int numberofVIP=2;
		if (capacity/10 >2) {
			numberofVIP=capacity/10;
		}
		
		for (int i=0;i<numberofVIP;i++) {
			ticketList[i]=new VIPTicket(i);
		}
		for (int i=numberofVIP;i<capacity;i++) {
			ticketList[i]=new RegularTicket(i);
		}
	}
	
	public String getDepartureLocation() {
		return departureLocation;
	}
	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}
	public String getLandingLocation() {
		return landingLocation;
	}
	public void setLandingLocation(String landingLocation) {
		this.landingLocation = landingLocation;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getLandingDate() {
		return landingDate;
	}
	public void setLandingDate(Date landingDate) {
		this.landingDate = landingDate;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Ticket[] getTicketList() {
		return ticketList;
	}

	public void setTicketList(Ticket[] ticketList) {
		this.ticketList = ticketList;
	}
	
	
}	
