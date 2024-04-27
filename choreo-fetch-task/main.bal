import ballerina/http;
import ballerina/io;

configurable string coreApiUrl = ?;

public function main() returns error? {
    io:println("Core API URL: " + coreApiUrl);
    http:Client coreApiClient = check new (coreApiUrl);

    check coreApiClient->/tasks/fetch.post({
        "processorType": "GOOGLE_SHEET_API"
    });

}