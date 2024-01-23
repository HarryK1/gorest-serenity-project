package com.gorest.gorestinfo;

import com.gorest.constants.EndPoints;
import com.gorest.constants.Path;
import com.gorest.model.UserPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class UsersSteps {
    @Step("Creating student with name : {0}, email : {1}, gender : {2}, status : {3}")
    public ValidatableResponse createUser(String name, String email, String gender, String status) {
        UserPojo userPojo = UserPojo.getUserPojo(name, email, gender, status);
        return SerenityRest.given()
                .body(userPojo)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 10efcc0eda0dc46c3d8b3d415af61bad8412b32398edede555b02a9ab8860eb4")
                .when()
                .post(Path.USERS)
                .then().statusCode(201);
    }

    @Step("Verifying user is created with userID : {0}")
    public HashMap<String, Object> getUserInfoByUserID(int userID) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 10efcc0eda0dc46c3d8b3d415af61bad8412b32398edede555b02a9ab8860eb4")
                .pathParam("userID", userID)
                .when()
                .get(Path.USERS + EndPoints.GET_SINGLE_USER_BY_ID)
                .then().statusCode(200).extract().path("");
    }

    @Step("Updating user with userId : {0},name : {1}, email : {2}, gender {3}, status : {4}")
    public ValidatableResponse updateUser(int userID, String name, String email, String gender, String status) {
        UserPojo userPojo = UserPojo.getUserPojo(name, email, gender, status);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 10efcc0eda0dc46c3d8b3d415af61bad8412b32398edede555b02a9ab8860eb4")
                .pathParam("userID", userID)
                .body(userPojo)
                .when()
                .put(Path.USERS + EndPoints.UPDATE_USER_BY_ID)
                .then();
    }

    @Step("Deleting user information with userId : {0}")
    public ValidatableResponse deleteUser(int userID) {
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 10efcc0eda0dc46c3d8b3d415af61bad8412b32398edede555b02a9ab8860eb4")
                .pathParam("userID", userID)
                .when()
                .delete(Path.USERS + EndPoints.DELETE_USER_BY_ID)
                .then().log().all();
    }

    @Step("Getting user information with userId : {0}")
    public ValidatableResponse getUserById(int userID) {
        return SerenityRest.given()
                .pathParam("userID", userID)
                .when()
                .get(Path.USERS + EndPoints.GET_SINGLE_USER_BY_ID)
                .then().log().all();
    }
}
