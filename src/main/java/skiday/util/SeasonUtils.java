package skiday.util;

import java.time.LocalDate;

public class SeasonUtils {

	public static int parseSeasonStartYear(String season) {
        return Integer.parseInt(season.substring(0, 4));
	}
	
	 public static boolean dateInSeason(LocalDate date, String season) {
	        int startYear = parseSeasonStartYear(season);
	        LocalDate start = LocalDate.of(startYear, 11, 1);
	        LocalDate end = LocalDate.of(startYear + 1, 4, 30);
	        return !date.isBefore(start) && !date.isAfter(end);
	 }
}