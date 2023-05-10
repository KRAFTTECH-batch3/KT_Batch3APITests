package apiTests.day07;

import apiPOJOTemplates.User;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.util.Map;

public class gsonPractice {

    //de-serilization
    //convert data from json to java

    //serilization
    //convert data java object(collection, class, etc.) to json

    //we will look into the answer of these questions

    @Test
    public void jsonToJava() {
    /*
    {
    "id": 1513738,
    "name": "Amish Khan",
    "email": "amish_khan@greenholt.test",
    "gender": "male",
    "status": "inactive"
    }
    */

        //create a json object
        Gson gson = new Gson();

        String userJsonBody = "{\n" +
                "    \"id\": 1513738,\n" +
                "    \"name\": \"Amish Khan\",\n" +
                "    \"email\": \"amish_khan@greenholt.test\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"status\": \"inactive\"\n" +
                "    }";

        //see how it looks like
        System.out.println(userJsonBody);

        //de-serilization
        //json --> java (Map)
        Map<String,Object> map = gson.fromJson(userJsonBody, Map.class);
        //see how map look like
        System.out.println(map);

        //de-serilization
        //json --> java (User)
        User user = gson.fromJson(userJsonBody, User.class);
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getStatus());
        System.out.println(user.getEmail());
        System.out.println(user.getGender());
    }

    @Test
    public void javaToJson(){
        //create a gson object
        Gson gson = new Gson();

        //create a user object with the following data
        //id --> 1
        //name --> aslıhan
        //email --> aslıhan@aslıhan.com
        //gender --> female
        //status --> inactive

        User user = new User();
        user.setId(1);
        user.setName("aslıhan");
        user.setEmail("aslıhan@aslıhan.com");
        user.setGender("female");
        user.setStatus("inactive");
        System.out.println(user);

        //serilization
        //java to json
        String jsonUser = gson.toJson(user);

        //see how jsonUser looks like
        System.out.println(jsonUser);
    }
}
