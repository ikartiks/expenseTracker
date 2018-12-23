package ikartiks.expensetracker;


import ikartiks.expensetracker.entities.Repo;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.ArrayList;
import java.util.List;

public interface RetrofitInterface {

    @GET("users/{user}/repos")
    Single<ArrayList<Repo>> listRepos(@Path("user") String user);
}

