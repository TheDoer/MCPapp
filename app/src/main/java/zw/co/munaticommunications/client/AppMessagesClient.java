package zw.co.munaticommunications.client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import zw.co.munaticommunications.model.MessagesResponse;

public interface AppMessagesClient {
    @GET("thz/api/app-dashboard")
    Call<MessagesResponse> getAllMesages(@Header("Authorization") String token);
}
