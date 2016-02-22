
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    var value = 2;
    $("#addRespuesta").on('click', function () {
        var r = "<div class=\"radio\"> <label> <input type=\"radio\" name=\"respuesta\"id=\"respuesta\" value=\"" + (value) + "\"> <input type=\"text\" class=\"form-control\" id=\"Respuesta\" name=\"respuestaText[]\" value placeholder=\"Respuesta\" required> </label> </div>";
        $("#answersadded").append(r);
        value++;
    });



    $("#removeRespuesta").on('click', function () {
        $("#answersadded div:last-child").remove();
        value--;
    });

});