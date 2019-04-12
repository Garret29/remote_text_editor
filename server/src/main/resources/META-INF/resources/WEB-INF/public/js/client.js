let docChanged = false;
let serverURL = "localhost";
let caretPosition;

$(document).ready(function () {
    $("#textarea").on('input', function () {
        setChanged(true);
        setCaretPosition($("#textarea").getCaretPosition());
    });

    $("#refreshDocsButton").click(function () {
        loadDocs();
    });

    $("#serverButton").click(function () {
        setServerUrl($("#serverInfo").val());
    });

    $("#addDocButton").click(function () {
        if ($("#newDocName").val() !== "") {
            addDoc($("#newDocName").val())
        } else {
            alert("Nazwa nie może być pusta!")
        }
    });

    $("#selectDoc").on("change", function () {
        setChanged(false)
    });

    $("#deleteDocButton").click(function () {
        if ($("#selectDoc").val() !== null) {
            deleteDoc($("#selectDoc").val());
        }
    });

    $("#changeNameButton").click(function () {
        if($("#newName").val()!=="" && $("#newName").val()!==null){
            changeName($("#newName").val())
        }
    });

    loadDocs();
    window.setInterval(function () {
            if (!docChanged && $("#selectDoc").val() !== null) {
                setCaretPosition($("#textarea").getCaretPosition());
                $.ajax({
                        url: "http://" + serverURL + ":8080/docs/" + $("#selectDoc").val(),
                        method: "post",
                        contentType: "application/json",
                        crossDomain: true,
                        dataType: "JSON",
                        data: JSON.stringify({
                            "text": $("#textarea").val(),
                            "name": $("#selectDoc").val(),
                            "caretPosition": caretPosition
                        }),
                        success: function (data, text, xhr) {
                            $("#textarea").val(data.text);
                            setCaretPosition(data.caretPosition-1, data.caretPosition);
                            $("#textarea").selectRange(data.caretPosition);
                        }
                        ,
                        error(jqXHR, textStatus, errorThrown)
                        {
                            console.log(jqXHR.status);
                            console.log(textStatus);
                            console.log(errorThrown);
                        }
                    }
                )
                ;
            }
            setTimeout(function () {
                if (docChanged && $("#selectDoc").val() !== null) {
                    text = $("#textarea").val();

                    if (text === "") {
                        text = "****EMPTY_TEXT_CODE****";
                    }

                    $.ajax({
                        url: "http://" + serverURL + ":8080/docs/" + $("#selectDoc").val() + "/update",
                        method: "post",
                        crossDomain: true,
                        contentType: "application/text; charset=UTF-8",
                        dataType: "text",
                        data: text,
                        success: function () {
                            setChanged(false);
                        },
                        error(jqXHR, textStatus, errorThrown){
                            console.log(jqXHR.status);
                            console.log(textStatus);
                            console.log(errorThrown);
                        }
                    })
                }
            }, 200)
        },
        500
    )
    ;
})
;

function deleteDoc(name) {
    $.ajax({
        url: "http://" + serverURL + ":8080/docs/",
        method: "delete",
        contentType: "application/text; charset=UTF-8",
        crossDomain: true,
        dataType: "text",
        data: name,
        success: function () {
            loadDocs();
            setChanged(false);
            $("#textarea").val("");
        }
    })
}

function setCaretPosition(val) {
    caretPosition = val;
}

function addDoc(name) {
    $.ajax({
        url: "http://" + serverURL + ":8080/docs/",
        method: "post",
        contentType: "application/text; charset=UTF-8",
        crossDomain: true,
        dataType: "text",
        data: name,
        success: function () {
            loadDocs();
        },
        error(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function changeName(newName) {
    $.ajax({
        url: "http://" + serverURL + ":8080/docs/"+$("#selectDoc").val(),
        method: "put",
        contentType: "application/text; charset=UTF-8",
        crossDomain: true,
        dataType: "text",
        data: newName,
        success: function () {
            loadDocs();
        },
        error(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function setServerUrl(url) {
    serverURL = url;
}

function loadDocs() {
    $.ajax({
        url: "http://" + serverURL + ":8080/docs/",
        method: "get",
        contentType: "application/text; charset=UTF-8",
        crossDomain: true,
        dataType: "text",
        success: function (data, text, xhr) {
            const docs = JSON.parse(data);
            $("#selectDoc").empty();

            docs.forEach(function (val) {
                $("#selectDoc").append("<option value=" + val + ">" + val + "</option>")
            })
        },
        error(jqXHR, textStatus, errorThrown){
            console.log(jqXHR.status);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });

}


function setChanged(val) {
    docChanged = val;
}

$.fn.selectRange = function (start, end) {
    if (end === undefined) {
        end = start;
    }
    return this.each(function () {
        if ('selectionStart' in this) {
            this.selectionStart = start;
            this.selectionEnd = end;
        } else if (this.setSelectionRange) {
            this.setSelectionRange(start, end);
        } else if (this.createTextRange) {
            const range = this.createTextRange();
            range.collapse(true);
            range.moveEnd('character', end);
            range.moveStart('character', start);
            range.select();
        }
    });
};

$.fn.getCaretPosition = function () {
    const el = $(this).get(0);
    let pos = 0;
    if ('selectionStart' in el) {
        pos = el.selectionStart;
    } else if ('selection' in document) {
        el.focus();
        const Sel = document.selection.createRange();
        const SelLength = document.selection.createRange().text.length;
        Sel.moveStart('character', -el.value.length);
        pos = Sel.text.length - SelLength;
    }
    return pos;
};
