window.onload=function () {
    fileUD();
}

// function uploadFile() {
//     $("#fileForm").ajaxSu
// }

function fileUD() {
    var fileUDVue=new Vue({
        el:"#fileVue",
        data:{
            segFiles:[],
            dictFiles:[{name:"testFile",size:2}]
        },
        methods:{
            downLoad:function (dictFile) {

                window.open("/file/download/"+dictFile.name);
            },
            downLoadSource:function (dictFile) {

                window.open("/file/downloadSource/"+dictFile.name);
            },
            uploadFile:function () {
                var fileName=$("#segmentFileInput").val();
                fileNameSplit=fileName.split("\\");
                if (fileNameSplit.length==1){
                    fileNameSplit=fileName.split("/");
                }
                fileName=fileNameSplit[fileNameSplit.length-1];
                console.log("要上传的文件名："+fileName);
                hasSameName=false;
                if(this.segFiles.length>0){
                    for(i=0;i<this.segFiles.length;i++){
                        if (this.segFiles[i]==fileName){
                            hasSameName=true;
                            break;
                        }
                    }
                }
                if(hasSameName){
                    $("#confirmCoverModal").modal();
                }else {
                    $("#fileForm").ajaxSubmit(function (response) {
                        console.log(response)
                        if (response=="success"){
                            toastr.success("上传成功!");
                            console.log("开始更新文件列表")
                            fileUDVue.$http.get("/file/getAllDictFiles").then(function (response) {
                                console.log(response);
                                if(response.data!=null && response.data.length>0){
                                    this.dictFiles=response.data;
                                    document.getElementById("showNo").style.display="none";
                                }else{
                                    this.dictFiles=[];
                                    document.getElementById("showNo").style.display="block";
                                }
                            });

                        }else {
                            toastr.error("上传失败!")
                        }
                        $(this).resetForm();
                        $(this).clearForm();
                    });
                    return false;
                }
            },
            confirmCover:function () {
                $("#confirmCoverModal").map(function () {
                    if (!$(this).is(":hidden")){
                        $(this).modal('hide');
                    }
                });
                $("#fileForm").ajaxSubmit(function (response) {
                    console.log(response)
                    if (response=="success"){
                        toastr.success("上传成功!");
                        console.log("开始更新文件列表")
                        fileUDVue.$http.get("/file/getAllDictFiles").then(function (response) {
                            console.log(response);
                            if(response.data!=null && response.data.length>0){
                                this.dictFiles=response.data;
                                document.getElementById("showNo").style.display="none";
                            }else{
                                this.dictFiles=[];
                                document.getElementById("showNo").style.display="block";
                            }
                        });

                    }else {
                        toastr.error("上传失败!")
                    }
                    $(this).resetForm();
                    $(this).clearForm();
                });
                return false;
            }

        },
        mounted:function () {
            this.$http.get("/file/getAllSegmentFiles").then(function (response) {
                if(response.data!=null && response.data.length>0){
                    this.segFiles=response.data;
                }else{
                    this.segFiles=[];
                }
            })
            this.$http.get("/file/getAllDictFiles").then(function (response) {
                if(response.data!=null && response.data.length>0){
                    this.dictFiles=response.data;
                }else{
                    this.dictFiles=[];
                    document.getElementById("showNo").style.display="block";
                }
            })
        }
    })
}