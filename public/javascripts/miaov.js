var radius = 200;
var dtr = Math.PI / 180;
var d = 300;

var mcList = [];
var active = false;
var lasta = 1;
var lastb = 1;
var distr = true;
var tspeed = 10;
var size = 250;

var mouseX = 0;
var mouseY = 0;

var howElliptical = 1;

var aA = null;
var oDiv = null;




function OnLoadFunction(divId) {
     radius = 200;
    dtr = Math.PI / 180;
    d = 300;

    mcList = [];
    active = false;
     lasta = 1;
     lastb = 1;
     distr = true;
     tspeed = 10;
     size = 250;

     mouseX = 0;
     mouseY = 0;

     howElliptical = 1;

    var i = 0;
    var oTag = null;

    oDiv = document.getElementById(divId);
    aA = oDiv.getElementsByTagName('a');

    for (i = 0; i < aA.length; i++) {
        oTag = {};

        oTag.offsetWidth = aA[i].offsetWidth;
        oTag.offsetHeight = aA[i].offsetHeight;

        mcList.push(oTag);
    }

    sineCosine(0, 0, 0);

    positionAll();

    oDiv.onmouseover = function () {
        active = true;
    };

    oDiv.onmouseout = function () {
        active = false;
    };

    oDiv.onmousemove = function (ev) {
        var oEvent = window.event || ev;

        mouseX = oEvent.clientX - (oDiv.offsetLeft + oDiv.offsetWidth / 2);
        mouseY = oEvent.clientY - (oDiv.offsetTop + oDiv.offsetHeight / 2);

        mouseX /= 5;
        mouseY /= 5;
    };

    setInterval(update, 30);
}


//window.onload=function ()
//{
//	loadSystemTag();
//    loadTableByUserId("", 1, 6);
//};

function update() {
    var a;
    var b;

    if (active) {
        a = (-Math.min(Math.max(-mouseY, -size), size) / radius ) * tspeed;
        b = (Math.min(Math.max(-mouseX, -size), size) / radius ) * tspeed;
    }
    else {
        a = lasta * 0.98;
        b = lastb * 0.98;
    }

    lasta = a;
    lastb = b;

    if (Math.abs(a) <= 0.01 && Math.abs(b) <= 0.01) {
        return;
    }

    var c = 0;
    sineCosine(a, b, c);
    for (var j = 0; j < mcList.length; j++) {
        var rx1 = mcList[j].cx;
        var ry1 = mcList[j].cy * ca + mcList[j].cz * (-sa);
        var rz1 = mcList[j].cy * sa + mcList[j].cz * ca;

        var rx2 = rx1 * cb + rz1 * sb;
        var ry2 = ry1;
        var rz2 = rx1 * (-sb) + rz1 * cb;

        var rx3 = rx2 * cc + ry2 * (-sc);
        var ry3 = rx2 * sc + ry2 * cc;
        var rz3 = rz2;

        mcList[j].cx = rx3;
        mcList[j].cy = ry3;
        mcList[j].cz = rz3;

        per = d / (d + rz3);

        mcList[j].x = (howElliptical * rx3 * per) - (howElliptical * 2);
        mcList[j].y = ry3 * per;
        mcList[j].scale = per;
        mcList[j].alpha = per;

        mcList[j].alpha = (mcList[j].alpha - 0.6) * (10 / 6);
    }

    doPosition();
    depthSort();
}

function depthSort() {
    var i = 0;
    var aTmp = [];

    for (i = 0; i < aA.length; i++) {
        aTmp.push(aA[i]);
    }

    aTmp.sort
    (
        function (vItem1, vItem2) {
            if (vItem1.cz > vItem2.cz) {
                return -1;
            }
            else if (vItem1.cz < vItem2.cz) {
                return 1;
            }
            else {
                return 0;
            }
        }
    );

    for (i = 0; i < aTmp.length; i++) {
        aTmp[i].style.zIndex = i;
    }
}

function positionAll() {
    var phi = 0;
    var theta = 0;
    var max = mcList.length;
    var i = 0;

    var aTmp = [];
    var oFragment = document.createDocumentFragment();

    //随机排序
    for (i = 0; i < aA.length; i++) {
        aTmp.push(aA[i]);
    }

    aTmp.sort
    (
        function () {
            return Math.random() < 0.5 ? 1 : -1;
        }
    );

    for (i = 0; i < aTmp.length; i++) {
        oFragment.appendChild(aTmp[i]);
    }

    oDiv.appendChild(oFragment);

    for (var i = 1; i < max + 1; i++) {
        if (distr) {
            phi = Math.acos(-1 + (2 * i - 1) / max);
            theta = Math.sqrt(max * Math.PI) * phi;
        }
        else {
            phi = Math.random() * (Math.PI);
            theta = Math.random() * (2 * Math.PI);
        }
        //坐标变换
        mcList[i - 1].cx = radius * Math.cos(theta) * Math.sin(phi);
        mcList[i - 1].cy = radius * Math.sin(theta) * Math.sin(phi);
        mcList[i - 1].cz = radius * Math.cos(phi);

        aA[i - 1].style.left = mcList[i - 1].cx + oDiv.offsetWidth / 2 - mcList[i - 1].offsetWidth / 2 + 'px';
        aA[i - 1].style.top = mcList[i - 1].cy + oDiv.offsetHeight / 2 - mcList[i - 1].offsetHeight / 2 + 'px';
    }
}

function doPosition() {
    var l = oDiv.offsetWidth / 2;
    var t = oDiv.offsetHeight / 2;
    for (var i = 0; i < mcList.length; i++) {
        aA[i].style.left = mcList[i].cx + l - mcList[i].offsetWidth / 2 + 'px';
        aA[i].style.top = mcList[i].cy + t - mcList[i].offsetHeight / 2 + 'px';

        aA[i].style.fontSize = Math.ceil(12 * mcList[i].scale / 2) + 8 + 'px';

        aA[i].style.filter = "alpha(opacity=" + 100 * mcList[i].alpha + ")";
        aA[i].style.opacity = mcList[i].alpha;
    }
}

function sineCosine(a, b, c) {
    sa = Math.sin(a * dtr);
    ca = Math.cos(a * dtr);
    sb = Math.sin(b * dtr);
    cb = Math.cos(b * dtr);
    sc = Math.sin(c * dtr);
    cc = Math.cos(c * dtr);
}



var searchToken = "";
    var currentPage = 1;
    var searchType = 1;     <!--1表示按照用户I的查找，0表示按照tag查找 -->

    function loadSystemTag(){
        var options = {
            url: "/Search/getAllTag",
            type: "post",
            global: true,
            success: function (msg) {
                if (msg.success) {
                    var list = msg.data;
                    initBoll(list);
                }
                OnLoadFunction("bollDiv");
            }
        };

        $.ajax(options);
        document.getElementById("tagLabel").innerHTML="<b>系统标签</b>";
    }

    function loadTableByUserId(token, page, perpage) {
        var params = {userId: token, curPage: page, perPage: perpage};
        var options = {
            url: "/Search/searchByUser",
            data: params,
            global: true,
            success: function (msg) {
                if (msg.success) {
                    var msgData = msg.data;
                    var userList = msgData.sinaUserList;
                    searchToken = token;
                    currentPage = page;
                    searchType = 1;
                    if(userList==null||userList.length<1){
                        alert("没有更多内容了");
                        return;
                    }
                    drawTable(userList);
                }
            }
        };

        $.ajax(options);
    }

    function loadTableByTag(token,page,perpage){
        var params = {tag: token, curPage: page, perPage: perpage};
        var options = {
            url: "/Search/searchByTag",
            data: params,
            global: true,
            success: function (msg) {
                if (msg.success) {
                    var msgData = msg.data;
                    var userList = msgData.sinaUserList;
                    searchToken = token;
                    currentPage = page;
                    searchType = 0;
                    if(userList==null||userList.length<1){
                        alert("没有更多内容了");
                        return;
                    }
                    drawTable(userList);
                    }
                }
            };

        $.ajax(options);
    }

    function drawTable(data) {
        var html = [];
        html.push('<table id="tableContent" class="table table-hover" style="width:1000px;height: 500px;"> ');
        html.push('<thead><tr>');
        html.push('<th id="userId" style="width:30%;text-align:center">用户ID</th> ');
        html.push('<th id ="userTag" style="width:70%;text-align:center">用户标签</th> ');
        html.push('</tr></thead>');
        html.push('<tbody id="contentlist"> ');
        $.each(data, function (index, item) {
            html.push('<tr>');
            html.push('<td style="text-align: left;"><a href="#" onclick=changeBollByUserId("'+item.userId+'","'+item.userTagStr+'")>' + item.userId + '</a></td>');
            var temp = item.userTagStr;
            if(temp.length>=50){
                temp = temp.substr(0,48)+"......";
            }
            html.push('<td style="text-align: left;">' + temp+ '</td>');
            html.push('</tr>');
        });
        html.push('</tbody></table>');
        document.getElementById("tableDiv").innerHTML = html.join("");
    }

    function searchButtonClick(){
        var token = document.getElementById("token").value;
        if(token=="搜索请输入用户ID关键字"){
            token = "";
        }
        loadTableByUserId(token,1,6);
        loadSystemTag();
    }

    function tagSearch(token){
        loadTableByTag(token,1,6);
     }

    function initBoll(data){
        var html = [];
        $.each(data, function (index, item) {
            if (index%2 == 0) {
                html.push("<a href='#' class='yellow' onclick=tagSearch('"+item+"')>" + item + "</a>");
            } else if (index%3 == 0) {
                html.push("<a href='#' class='red' onclick=tagSearch('"+item+"')>" + item + "</a>");
            } else {
                html.push("<a href='#' class='blue' onclick=tagSearch('"+item+"')>" + item + "</a>");
            }
        });
        document.getElementById("bollDiv").innerHTML = html.join("");
    }

    function changeBollByUserId(userId,data){
        document.getElementById("tagLabel").innerHTML="用户 <b>"+userId+"</b>"+" 的标签";
        var list = data.split(";");
        initBoll(list);
        OnLoadFunction("bollDiv");
    }

    function pageTurn(pageEvent){
        if(pageEvent=="pre"){
            if(currentPage==1){
                alert("当前是第一页，没有上一页了");
                return;
            }
            if(searchType==0){
                loadTableByTag(searchToken,currentPage-1,6);
            }else{
                loadTableByUserId(searchToken,currentPage-1,6);
            }
        }else{
            if(searchType==0){
                loadTableByTag(searchToken,currentPage+1,6);
            }else{
                loadTableByUserId(searchToken,currentPage+1,6);
            }
        }

    }