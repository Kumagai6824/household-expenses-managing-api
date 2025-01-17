package household_expenses_managing_api.com.demo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private T data;
    private String message;

    public ApiResponse(T data, String message){
        this.data=data;
        this.message=message;
    }
}
