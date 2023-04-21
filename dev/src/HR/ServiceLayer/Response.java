package HR.ServiceLayer;

import java.util.Objects;

public class Response {

    private String errorMessage;
    public Response(){}

    public Response(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public boolean errorOccurred(){
        return !Objects.equals(errorMessage, "");
    }


    public String getErrorMessage() {
        return errorMessage;
    }
}
