package skiday.dto;

public class SkiSessionDTO {

	public Long userId;         // required
    public Long resortId;       // required if type=RESORT

    public String season;       // "2024/25" (required)
    public String date;         // "YYYY-MM-DD" (required)

    public String type;         // "RESORT" or "BACKCOUNTRY" (required)

    // Resort-specific
    public String favoriteRun;
    public String liftsUsed;

    // Backcountry-specific
    public String region;
    public String gearUsed;
    public Boolean dogFriendly;

   
    public Integer ascent;
    public Integer descent;
    public Double miles;
    public Integer elapsedMinutes;
    public String conditions;
    public String notes;
}

