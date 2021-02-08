package com.github.rsheremeta.testrail;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.ExceptionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/*
This was done by Roman Sheremeta - https://github.com/RSheremeta
*/
public class TestRailHelper {
  private static APIClient apiClient;
  private static Long suiteId;


  public static void createNewTestRailRun(TestRailCreds projectCreds, String projectId, String testRunName){
    if (suiteId==null) createRun(projectCreds, projectId, testRunName);
  }

  public static void setTestRailStatus(ExtensionContext extContext, TestResult result) {
    String testId = getTestRailId(extContext);
    String commentValue = "Automatically generated test result";
    int testResult = getTestResult(result);
    Map <String, Object> resultData = new HashMap<>();
    resultData.put("status_id", testResult);

    if (testResult==5) {
      commentValue = "TEST HAS BEEN FAILED. \n STACK TRACE: \n"
        + ExceptionUtils.readStackTrace(extContext.getExecutionException().get());
    }

    resultData.put("comment", commentValue);

    try {
      apiClient.sendPost("add_result_for_case/" + suiteId + "/" + testId, resultData);
    } catch (IOException | APIException e) {
      e.printStackTrace();
    }
  }

  private static void createRun(TestRailCreds projectCreds, String projectId, String testRunName) {
    String currentDateTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));

    apiClient = new APIClient(projectCreds.getProjectUrl());
    apiClient.setUser(projectCreds.getUsername());
    apiClient.setPassword(projectCreds.getPassword());

    Map <String, Object> requestData = new HashMap<>();
    requestData.put("include_all", true);
    requestData.put("name", testRunName + currentDateTime);
    requestData.put("description", "Automatically generated test suite");

    JSONObject obj = null;
    try {
      obj = (JSONObject) apiClient.sendPost("/add_run/" + projectId, requestData);
    } catch (IOException | APIException e) {
      e.printStackTrace();
    }

    suiteId = (Long) obj.get("id");
  }

  private static int getTestResult(TestResult result) {
    int statusId = 0;

    switch (result) {
      case PASSED:
        statusId = 1;
        break;
      case BLOCKED:
        statusId = 2;
        break;
      case FAILED:
        statusId = 5;
        break;
    }

    return statusId;
  }

  private static String getTestRailId(ExtensionContext extContext) {
    String testId = "";
    Method testMethod = extContext.getTestMethod().get();
    if (testMethod.isAnnotationPresent(TestRailCase.class))
      testId = testMethod.getAnnotation(TestRailCase.class).id();

    return testId;
  }

}
