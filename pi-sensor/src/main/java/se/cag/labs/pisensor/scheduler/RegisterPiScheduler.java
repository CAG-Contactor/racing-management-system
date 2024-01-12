package se.cag.labs.pisensor.scheduler;

import com.pi4j.system.SystemInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import se.cag.labs.pisensor.contract.Pi;
import se.cag.labs.pisensor.service.DroidRaceApiService;
import se.cag.labs.pisensor.utils.NetworkUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

@Component
@Slf4j
public class RegisterPiScheduler {

    private DroidRaceApiService droidRaceApiService;

    @Autowired
    public RegisterPiScheduler(DroidRaceApiService droidRaceApiService) {
        this.droidRaceApiService = droidRaceApiService;
    }

    @Scheduled(fixedRate = 30000)
    public void registerPi() {
        try {
            Pi pi = createPiData();
            Call<Void> call = droidRaceApiService.registerPi(pi);
            Response<Void> response = call.execute();

            if (!response.isSuccessful()) {
                log.error("Couldn't register pi IP, got: " + response.code() + " - " + response.message());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Couldn't register pi", e);
        }
    }

    private Pi createPiData() throws IOException, InterruptedException {
        try {
            return new Pi("pi-sensor_" + SystemInfo.getSerial(), NetworkUtils.getIpAddress());
        } catch (UnsupportedOperationException | FileNotFoundException e) {
            log.warn("We are not running on a pi...");
            return new Pi("pi-sensor", NetworkUtils.getIpAddress());
        }
    }
}
