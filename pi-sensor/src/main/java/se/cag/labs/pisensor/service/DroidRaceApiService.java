package se.cag.labs.pisensor.service;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import se.cag.labs.pisensor.contract.Pi;
import se.cag.labs.pisensor.contract.SensorId;

public interface DroidRaceApiService {
    @POST("registerSensor")
    Call<Void> registerPi(
            @Body
            Pi pi);

    @POST("passageDetected")
    Call<Void> registerSensorEvent(
            @Query("sensorID")
            SensorId sensorId,
            @Query("timestamp")
            long timestamp);
}