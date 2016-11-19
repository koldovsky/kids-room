/**
 * Created by Sviatoslav Hryb on 19-Nov-16.
 */
var withoutFocusColor;

$(function() {

    $("button").focusin(function() {
        withoutFocusColor = window.getComputedStyle(this).getPropertyValue('color');
        this.style.color = '#CACFD2';
    }).focusout(function() {
        this.style.color = withoutFocusColor;
    });

    $("a").focusin(function() {
        withoutFocusColor = window.getComputedStyle(this).getPropertyValue('color');
        this.style.color = '#CACFD2';
    }).focusout(function() {
        this.style.color = withoutFocusColor;
    });

    $("th").focusin(function() {
        withoutFocusColor = window.getComputedStyle(this).getPropertyValue('color');
        this.style.color = '#CACFD2';
    }).focusout(function() {
        this.style.color = withoutFocusColor;
    });

});
