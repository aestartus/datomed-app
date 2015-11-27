/**
 * Created by aestartus on 26-11-15.
 */

var $ = jQuery.noConflict();

$.ajaxSetup( { async: false } );

function login() {
    $.ajax({
        cache: false,
        crossDomain: true,
        headers: {
            "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a"
        },
        dataType: "json",
        url: "//" + window.urlName + ":" + window.port + "/datomed/rest-api/business-resource/login/",
        type: "POST",
        data: {
            "username": $("#email").val(),
            "password": $("#password").val()
        },
        success: function (jsonObj, textStatus, xhr) {
            sessionStorage.auth_token = jsonObj.auth_token;

            var htmlContent = $("#logMsgDiv").html() + "<p>Entrada correcta Login. auth-token obtenido es: " + sessionStorage.auth_token + "</p>";
            $("#logMsgDiv").html(htmlContent);
        },
        error: function (xhr, textStatus, errorThrown) {
            console.log("HTTP Status: " + xhr.status);
            console.log("Error textStatus: " + textStatus);
            console.log("Error thrown: " + errorThrown);
        }
    });
}


function logout() {
    $.ajax({
        cache: false,
        crossDomain: true,
        headers: {
            "service_key": "3b91cab8-926f-49b6-ba00-920bcf934c2a",
            "auth_token": sessionStorage.auth_token
        },
        url: "//" + window.urlName + ":" + window.port + "/datomed/rest-api/business-resource/logout/",
        type: "POST",
        success: function (jsonObj, textStatus, xhr) {
            var htmlContent = $("#logMsgDiv").html() + "<p>Cierre de Session correcto!</p>";
            $("#logMsgDiv").html(htmlContent);
        },
        error: function (xhr, textStatus, errorThrown) {
            console.log("HTTP Status: " + xhr.status);
            console.log("Error textStatus: " + textStatus);
            console.log("Error thrown: " + errorThrown);
        }
    });
}


