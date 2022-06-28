package kiber.bezbednjaci.events;

public interface BaseAlarm {

	AlarmOccurred alarm();

	Integer getRealEstateId();

	Integer getDeviceId();

	String getMessage();
	
}
