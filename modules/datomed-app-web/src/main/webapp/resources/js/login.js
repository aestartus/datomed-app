/**
 * Created by aestartus on 26-11-15.
 */

var $ = jQuery.noConflict();

// Disable async
$.ajaxSetup( { async: false } );

// Using Service Key 3b91cab8-926f-49b6-ba00-920bcf934c2a and username2

// This is what happens when there you call the REST APIs without a service key and authorisation token
$.ajax( {
    cache: false,
    crossDomain: true,
    url: "//www.developerscrappad.com:8080/RESTSecurityWithHTTPHeaderDemo/rest-api/demo-business-resource/demo-post-method/",
    type: "POST",
    success: function( jsonObj, textStatus, xhr ) {
        var htmlContent = $( "#logMsgDiv" ).html( ) + "<p style='color: red;'>If this is portion is executed, something must be wrong</p>";
        $( "#logMsgDiv" ).html( htmlContent );
    },
    error: function( xhr, textStatus, errorThrown ) {
        var htmlContent = $( "#logMsgDiv" ).html( )
            + "<p style='color: red;'>This is what happens when there you call the REST APIs without a service key and authorisation token."
            + "<br />HTTP Status: " + xhr.status + ", Unauthorized access to demo-post-method</p>";

        $( "#logMsgDiv" ).html( htmlContent );
    }
} );

// Performing login with username2 and passwordForUser2
$.ajax( {
    cache: false,
    crossDomain: true,
    headers: {
        "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a"
    },
    dataType: "json",
    url: "//www.developerscrappad.com:8080/RESTSecurityWithHTTPHeaderDemo/rest-api/demo-business-resource/login/",
    type: "POST",
    data: {
        "username": "username2",
        "password": "passwordForUser2"
    },
    success: function( jsonObj, textStatus, xhr ) {
        sessionStorage.auth_token = jsonObj.auth_token;

        var htmlContent = $( "#logMsgDiv" ).html( ) + "<p>Perform Login. Gotten auth-token as: " + sessionStorage.auth_token + "</p>";
        $( "#logMsgDiv" ).html( htmlContent );
    },
    error: function( xhr, textStatus, errorThrown ) {
        console.log( "HTTP Status: " + xhr.status );
        console.log( "Error textStatus: " + textStatus );
        console.log( "Error thrown: " + errorThrown );
    }
} );

// After login, execute demoteGetMethod with the auth-token obtained
$.ajax( {
    cache: false,
    crossDomain: true,
    headers: {
        "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
        "auth_token": sessionStorage.auth_token
    },
    dataType: "json",
    url: "//www.developerscrappad.com:8080/RESTSecurityWithHTTPHeaderDemo/rest-api/demo-business-resource/demo-get-method/",
    type: "GET",
    success: function( jsonObj, textStatus, xhr ) {
        var htmlContent = $( "#logMsgDiv" ).html( ) + "<p>After login, execute demoteGetMethod with the auth-token obtained. JSON Message: " + jsonObj.message + "</p>";
        $( "#logMsgDiv" ).html( htmlContent );
    },
    error: function( xhr, textStatus, errorThrown ) {
        console.log( "HTTP Status: " + xhr.status );
        console.log( "Error textStatus: " + textStatus );
        console.log( "Error thrown: " + errorThrown );
    }
} );

// Execute demoPostMethod with the auth-token obtained
$.ajax( {
    cache: false,
    crossDomain: true,
    headers: {
        "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
        "auth_token": sessionStorage.auth_token
    },
    dataType: "json",
    url: "//www.developerscrappad.com:8080/RESTSecurityWithHTTPHeaderDemo/rest-api/demo-business-resource/demo-post-method/",
    type: "POST",
    success: function( jsonObj, textStatus, xhr ) {
        var htmlContent = $( "#logMsgDiv" ).html( ) + "<p>Execute demoPostMethod with the auth-token obtained. JSON message: " + jsonObj.message + "</p>";
        $( "#logMsgDiv" ).html( htmlContent );
    },
    error: function( xhr, textStatus, errorThrown ) {
        console.log( "HTTP Status: " + xhr.status );
        console.log( "Error textStatus: " + textStatus );
        console.log( "Error thrown: " + errorThrown );
    }
} );

// Let's logout after all the above. No content expected
$.ajax( {
    cache: false,
    crossDomain: true,
    headers: {
        "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
        "auth_token": sessionStorage.auth_token
    },
    url: "//www.developerscrappad.com:8080/RESTSecurityWithHTTPHeaderDemo/rest-api/demo-business-resource/logout/",
    type: "POST",
    success: function( jsonObj, textStatus, xhr ) {
        var htmlContent = $( "#logMsgDiv" ).html( ) + "<p>Let's logout after all the above. No content expected.</p>";
        $( "#logMsgDiv" ).html( htmlContent );
    },
    error: function( xhr, textStatus, errorThrown ) {
        console.log( "HTTP Status: " + xhr.status );
        console.log( "Error textStatus: " + textStatus );
        console.log( "Error thrown: " + errorThrown );
    }
} );

// This is what happens when someone reuses the authorisation token after a user had been logged out
$.ajax( {
    cache: false,
    crossDomain: true,
    headers: {
        "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
        "auth_token": sessionStorage.auth_token
    },
    url: "//www.developerscrappad.com:8080/RESTSecurityWithHTTPHeaderDemo/rest-api/demo-business-resource/demo-get-method/",
    type: "GET",
    success: function( jsonObj, textStatus, xhr ) {
        var htmlContent = $( "#logMsgDiv" ).html( ) + "<p style='color: red;'>If this is portion is executed, something must be wrong</p>";
        $( "#logMsgDiv" ).html( htmlContent );
    },
    error: function( xhr, textStatus, errorThrown ) {
        var htmlContent = $( "#logMsgDiv" ).html( )
            + "<p style='color: red;'>This is what happens when someone reuses the authorisation token after a user had been logged out"
            + "<br />HTTP Status: " + xhr.status + ", Unauthorized access to demo-get-method</p>";

        $( "#logMsgDiv" ).html( htmlContent );
    }
} );
