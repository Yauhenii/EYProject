const button = document.getElementById("myButton");
const fileInput = document.getElementById("customFileLang");

window.onload = function () {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', 'api/init');
  xhr.send();

  xhr.onload = function () {
    fileNamesString = xhr.response;
    if (fileNamesString.trim() != "".trim()) {
      fileNames = fileNamesString.split(' ');
      for (i = 0; i < fileNames.length; i++) {
        //add new item to list
        var buttonElement = document.createElement("button");
        buttonElement.type = "button";
        buttonElement.classList.add("list-group-item",
            "list-group-item-action");
        buttonElement.innerText = fileNames[i];
        document.getElementById("filesList").appendChild(buttonElement);
        const currentFileName=fileNames[i];
        //add listener
        buttonElement.addEventListener("click",function () {
          openTablePage(currentFileName);
        });
      }
    }
  };
};

fileInput.addEventListener("change", function () {

  var fileList = this.files;
  var file = fileList[0];
  var fileName = file.name;
  var fromData = new FormData();
  fromData.append('file', file);

  //add new item to list
  var buttonElement = document.createElement("button");
  buttonElement.type = "button";
  buttonElement.classList.add("list-group-item", "list-group-item-action");
  buttonElement.innerText = "Loading...";
  document.getElementById("filesList").appendChild(buttonElement);

  //send data
  var xhr = new XMLHttpRequest();
  xhr.open('POST', 'api/add');
  xhr.send(fromData);

  xhr.onload = function () {
    //change list item text
    buttonElement.innerText = fileName;

    const currentFileName=fileName;
    //add listener
    buttonElement.addEventListener("click",function () {
      openTablePage(currentFileName);
    });
  };
});

function openTablePage(fileName) {
  var opened = window.open("html/table.html");

  opened.onload = function () {
    //set table name
    opened.document.getElementById("tableTitle").innerText = fileName;

    var fromDataGet = new FormData();
    fromDataGet.append('name', fileName);
    var xhrGet = new XMLHttpRequest();
    xhrGet.open('POST', 'api/request');
    xhrGet.send(fromDataGet);
    xhrGet.onload = function () {
      csv = xhrGet.response.toString().split('\n');
      for (i = 0; i < csv.length - 1; i++) {
        var values = csv[i].split(',');
        var rowElement = document.createElement("tr");

        //create row
        for (j = 0; j < 7; j++) {
          var valueElement = document.createElement("td");
          valueElement.innerText = values[j];
          rowElement.appendChild(valueElement);
        }

        opened.document.getElementById("tableBody").appendChild(rowElement);
      }

    };
  };
}

