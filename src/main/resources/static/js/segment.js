window.onload=function () {
    segmentTrain();
}

function segmentTrain() {
    var segVue=new Vue({
        el:"#segmentVue",
        data:{
            segments:[{title:"正在加载分词文件...",segments:["请稍等..."]}],
            oneSeg:"",
            newName:"",
            myselfSegments:["CSSCI"],
            deleteSegment:""
        },
        methods:{
            chooseAddSeg:function (segment) {
                this.oneSeg=segment;
                this.newName="";
                $("#addModal").modal();
            },
            confirmModify:function () {
                if(this.newName==""){
                    toastr.error("分词不得为空！")

                }else{
                    flag=true;
                    for(i=0;i<this.myselfSegments.length;i++){
                        if(this.myselfSegments[i]==this.newName){
                            flag=false;
                        }
                    }
                    if(flag){
                        this.$http.post("http://47.100.37.139:8080/dict/addDict/"+this.newName);
                        // this.$http.post("http://localhost:8080/dict/addDict/"+this.newName);
                        this.myselfSegments.push(this.newName);
                        $("#addModal").map(function () {
                            if (!$(this).is(":hidden")){
                                $(this).modal('hide');
                            }
                        });
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
                this.$http.post("http://47.100.37.139:8080/dict/delete/"+this.deleteSegment);
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
            }
        },
        mounted:function () {
            // this.$http.get("http://localhost:8080/seg/getSegments").then(function (response) {
            this.$http.get("http://47.100.37.139:8080/seg/getSegments").then(function (response) {
                if(response.data.length>0){
                    this.segments=response.data;
                }else{
                    this.segments=[{title:"分词文件为空",segments:"请通知管理人员上传分词文件"}];
                }
            });
            // this.$http.get("http://localhost:8080/dict/getDict").then(function (response) {
            this.$http.get("http://47.100.37.139:8080/dict/getDict").then(function (response) {
                if (response.data.length>0){
                    this.myselfSegments=response.data;
                }
                else {
                    this.myselfSegments=[];
                }
            })
        }
    })
}