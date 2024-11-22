package org.challenge.core.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtil {

	public static LocalDateTime fromInstant(Instant instant, ZoneId zone){

		return instant == null ? null : LocalDateTime.ofInstant(instant, zone);
	}
}
