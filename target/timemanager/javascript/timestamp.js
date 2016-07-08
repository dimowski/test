function displayStartTime() {
    var str = "";

    var currentTime = new Date();
    var hours = currentTime.getHours();
    var minutes = currentTime.getMinutes();

    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    str += hours + ":" + minutes;

    document.getElementById("startTimeText").value = str;
}

function displayFinishTime() {
    var str = "";

    var currentTime = new Date();
    var hours = currentTime.getHours();
    var minutes = currentTime.getMinutes();

    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    str += hours + ":" + minutes;

    document.getElementById("finishTimeText").value = str;
}