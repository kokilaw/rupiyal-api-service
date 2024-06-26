import ballerina/http;
import ballerina/io;

configurable string coreApiUrl = ?;

type FetchResponse readonly & record {};

public function main() returns error? {

    io:println("Core API URL: " + coreApiUrl);
    http:Client coreApiClient = check new (coreApiUrl);

    json fetchResponse = <json> check coreApiClient->/internal/tasks/fetch.post({
        "processorType": "GOOGLE_SHEET_API"
    });
    io:println("Fetch response: " + fetchResponse.toJsonString());

}