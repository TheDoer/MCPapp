package zw.co.munaticommunications.client;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import zw.co.munaticommunications.model.LoginBody;
import zw.co.munaticommunications.model.LoginResponse;

public interface AppLoginClient {
    @POST("thz/api/app-login")
    Call<LoginResponse> loginWithUsenameAndPassword(@Body LoginBody user);
}
