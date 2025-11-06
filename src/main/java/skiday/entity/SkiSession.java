package skiday.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import skiday.enums.DayType;

@Entity
public class SkiSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	// Core session info
	private LocalDate date; // required
	@Enumerated(EnumType.STRING)
	private DayType type; // RESORT or BACKCOUNTRY

	private String season;

	// RESORT fields
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "resort_id")
	private Resort resort; // only for RESORT
	private String favoriteRun;
	private String liftsUsed; // CSV for MVP

	// BACKCOUNTRY fields
	private String region; // only for BACKCOUNTRY
	private String gearUsed; // CSV for MVP
	private Boolean dogFriendly;

	// Shared metrics
	private Integer ascent; // feet
	private Integer descent; // feet
	private Double miles; // e.g., 12.3
	private Integer elapsedMinutes; // total time (minutes)
	private String conditions; // short text
	@Lob
	private String notes; // long text

	// Constructors
	public SkiSession() {
	}

	public SkiSession(User user, LocalDate date, DayType type) {
		this.user = user;
		this.date = date;
		this.type = type;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public DayType getType() {
		return type;
	}

	public void setType(DayType type) {
		this.type = type;
	}

	public Resort getResort() {
		return resort;
	}

	public void setResort(Resort resort) {
		this.resort = resort;
	}

	public String getFavoriteRun() {
		return favoriteRun;
	}

	public void setFavoriteRun(String favoriteRun) {
		this.favoriteRun = favoriteRun;
	}

	public String getLiftsUsed() {
		return liftsUsed;
	}

	public void setLiftsUsed(String liftsUsed) {
		this.liftsUsed = liftsUsed;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getGearUsed() {
		return gearUsed;
	}

	public void setGearUsed(String gearUsed) {
		this.gearUsed = gearUsed;
	}

	public Boolean getDogFriendly() {
		return dogFriendly;
	}

	public void setDogFriendly(Boolean dogFriendly) {
		this.dogFriendly = dogFriendly;
	}

	public Integer getAscent() {
		return ascent;
	}

	public void setAscent(Integer ascent) {
		this.ascent = ascent;
	}

	public Integer getDescent() {
		return descent;
	}

	public void setDescent(Integer descent) {
		this.descent = descent;
	}

	public Double getMiles() {
		return miles;
	}

	public void setMiles(Double miles) {
		this.miles = miles;
	}

	public Integer getElapsedMinutes() {
		return elapsedMinutes;
	}

	public void setElapsedMinutes(Integer elapsedMinutes) {
		this.elapsedMinutes = elapsedMinutes;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getSeason() {
		return season;
	}
		
	public void setSeason(String season) {
		this.season= season;	
	}
}
