window.onload=function () {
    segmentTrain();
}

function segmentTrain() {
    var segVue=new Vue({
        el:"#segmentVue",
        data:{
            segments:[{title:"我是一只小小小鸟0",segments:["我是","一只","小小","小鸟"]},
                {title:"我是一只小小小鸟3",segments:"我是，一只，小小，小鸟"},
                {title:"我是一只小小小鸟2",segments:"我是，一只，小小，小鸟"},
                {title:"我是一只小小小鸟1",segments:"我是，一只，小小，小鸟"},
                {title:"我是一只小小小鸟4",segments:"我是，一只，小小，小鸟"}],
            oneSeg:"",
            newName:"",
            myselfSegments:["CSSCI","111","2222","3333","4444"],
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
        }
    })
}