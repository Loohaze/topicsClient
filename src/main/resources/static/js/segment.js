
window.onload=function () {
    var vueT=segmentTrain();
    // $("#showSegScroll").mouseup(function () {
    //     console.log(window.getSelection?window.getSelection():document.selection.createRange().text);
    //     vueT.newName=window.getSelection?window.getSelection():document.selection.createRange().text;
    //     console.log("vueT.newName:"+vueT.newName);
    // });

}


function segmentTrain() {
    var segVue=new Vue({
        el:"#segmentVue",
        data:{
            segmentFiles:["没有文件"],
            nowSegmentFile:"",
            segments:[{title:"正在加载分词文件...",segments:["请稍等..."]}],
            oneSeg:"",
            newName:"",
            myselfSegments:["CSSCI"],
            deleteSegment:"",
            inputNumber:1,
            input:"input",
            addInput:"addInput"
        },
        methods:{
            segSelect:function () {
                this.nowSegmentFile=$("#segFileSelect").val();
                console.log("切换文件，新文件请求URL：/seg/getSegments/"+this.nowSegmentFile)
                this.$http.get("/seg/getSegments/"+this.nowSegmentFile).then(function (response) {
                    if(response.data.length>0){
                        this.segments=response.data;
                    }else{
                        this.segments=[{title:"分词文件为空",segments:["请通知管理人员上传分词文件"]}];
                    }
                });
                // this.$http.get("/dict/getDict").then(function (response) {
                this.$http.get("/dict/getDict/"+this.nowSegmentFile).then(function (response) {
                    if (response.data.length>0){
                        this.myselfSegments=response.data;
                    }
                    else {
                        this.myselfSegments=[];
                    }
                })
            },
            chooseAddSeg:function (segment) {
                this.oneSeg=segment;
                this.newName="";
                $("#addModal").modal();
            },
            confirmModify:function () {
                var canTransfer=true;
                var hasNewSeg=false;
                var newSegs=[];
                for(i=1;i<=this.inputNumber;i++){
                    var inputI=$("#input"+''+i).val();
                    if (inputI!="" && inputI!=null && inputI.length>0){
                        hasNewSeg=true;
                        var hasNoDuplicate=true;
                        //检测重复
                        if(newSegs.length>0){
                            var j=0;
                            for(j=0;j<newSegs.length;j++){
                                if(newSegs[j]==inputI){
                                    hasNoDuplicate=false;
                                    canTransfer=false;
                                    break;
                                }
                            }
                        }
                        if (hasNoDuplicate){
                            newSegs.push(inputI);
                        }else {
                            toastr.error("存在重复词："+inputI);
                        }
                    }
                    if(!canTransfer){
                        break;
                    }
                }
                if(canTransfer){
                    if(newSegs.length>0){
                        console.log("新的分词列表:"+newSegs);
                        this.$http.post("/dict/addDictList/"+this.nowSegmentFile+"/"+newSegs);
                        var k=0;
                        for(k=0;k<newSegs.length;k++){
                            this.myselfSegments.push(newSegs[k]);

                        }
                        $("#addModal").map(function () {
                            if (!$(this).is(":hidden")){
                                $(this).modal('hide');
                            }
                        });
                        this.inputNumber=1;
                        $("#input1").val("");
                        toastr.success("已成功添加新的分词！")
                    }else{
                        toastr.error("至少添加一个新的分词！")
                    }
                }


            },
            changeInput:function () {
                console.log("开始选择")
                console.log("选中文字："+window.getSelection?window.getSelection():document.selection.createRange().text);
                $("#singleInputText").val(window.getSelection?window.getSelection():document.selection.createRange().text);
            },
            singleSegAdd:function () {
                var singleSegName=$("#singleInputText").val();
                console.log("添加单独分词之前已有分词是:"+this.myselfSegments);
                if(singleSegName==""){
                    toastr.error("分词不得为空！")

                }
                else{
                    console.log("添加单独分词:"+singleSegName);
                    flag=true;
                    for(i=0;i<this.myselfSegments.length;i++){
                        if(this.myselfSegments[i]==singleSegName){
                            flag=false;
                        }
                    }
                    if(flag){
                        this.$http.post("/dict/addDict/"+this.nowSegmentFile+"/"+singleSegName);
                        // this.$http.post("http://localhost:8080/dict/addDict/"+this.newName);
                        this.myselfSegments.push(singleSegName);
                        $("#singleInputText").val("");
                        toastr.success("已成功添加新的分词！")
                    }else {
                        toastr.warning("已经存在该分词")
                    }

                }
            },
            deleteSeg:function (mySeg) {
                this.deleteSegment=mySeg;
                $("#deleteModal").modal();
                // alert("sure to delete the segment:"+mySeg)
            },
            confirmDelete:function () {
                this.$http.post("/dict/delete/"+this.nowSegmentFile+"/"+this.deleteSegment);
                // this.$http.post("http://localhost:8080/dict/delete/"+this.deleteSegment);
                var newMyselfSegs=[];
                for(i=0;i<this.myselfSegments.length;i++){
                    if(this.myselfSegments[i]!=this.deleteSegment){
                        newMyselfSegs.push(this.myselfSegments[i]);
                    }
                }
                this.myselfSegments=newMyselfSegs;
                $("#deleteModal").map(function () {
                    if (!$(this).is(":hidden")){
                        $(this).modal('hide');
                    }
                });
            },
            addInputDom:function () {
                this.inputNumber=this.inputNumber+1;
            },
            hideInput:function (n) {
                $("#addInput"+''+n).hide();
                $("#input"+''+n).val("");
            }
        },
        mounted:function () {
            this.$http.get("/file/getAllSegmentFiles").then(function (response) {
                if (response.data!=null && response.data.length>0){
                    this.segmentFiles=response.data;
                    this.nowSegmentFile=this.segmentFiles[0];

                    console.log("mounted:/seg/getSegments/"+this.nowSegmentFile)
                    // this.$http.get("/seg/getSegments").then(function (response) {
                    segVue.$http.get("/seg/getSegments/"+segVue.nowSegmentFile).then(function (response) {
                        if(response.data.length>0){
                            segVue.segments=response.data;
                        }else{
                            segVue.segments=[{title:"分词文件为空",segments:["请通知管理人员上传分词文件"]}];
                        }
                    });
                    // this.$http.get("/dict/getDict").then(function (response) {
                    segVue.$http.get("/dict/getDict/"+segVue.nowSegmentFile).then(function (response) {
                        if (response.data.length>0){
                            segVue.myselfSegments=response.data;
                        }
                        else {
                            segVue.myselfSegments=[];
                        }
                    });
                }
                else {
                    this.segments=[{title:"没有分词文件",segments:["请通知管理人员上传分词文件"]}];
                }
            });

        }
    });
    return segVue;
}