window.onload=function (ev) {
    tag();
}

function tag() {
    var tagVue=new Vue({
        el:"#tagVue",
        data:{
            hotShow:true,
            tagInfos:[{title:"正在加载标注文件...",tags:["正在加载标注..."]}],
            specialTagInfos:[{title:"正在加载标注文件...",tags:["正在加载标注..."]}],
            //oneTag：被选中添加tag的那条
            oneTag:[{title:"正在加载标注文件...",tags:["正在加载标注..."]}],
            inputNumber:1,
            input:"input",
            addInput:"addInput",
            newTagText:"新标签",
            hotButtons:[],
            allTags:["tag1","tag2"],
            nowTag:"",
            allShow:true
        },
        methods:{
            chooseAddTag:function (tagInfo) {
                this.inputNumber=1;
                $("#input1").val("");
                this.oneTag=tagInfo;
                $("#addModal").modal();
                $("#titleAddTagButton").attr("disabled",false);
            },
            showHot:function(){
                this.hotShow=!this.hotShow;
            },
            getTagData:function(tag){
              this.$http.get("/tags/getTagInfosByTag/"+tag)
                  .then(function (value) {
                      if (value.data.length>0){
                          this.specialTagInfos=value.data;
                          this.nowTag=tag;
                          this.allShow=false;
                          $("#searchTagInput").val("");
                          $("#showAllButton").attr("disabled",false);
                      } else{
                          toastr.error("不存在该标签")
                      }

                  })
                  .catch(function (e) {
                  console.log(e);
                  toastr.error("错误"+e.status+":"+e.statusText);
              });


            },
            searchTag:function(){
                var tag=$("#searchTagInput").val();
                var exist=false;
                // for(var i=0;i<this.allTags.length;i++){
                //     if (this.allTags[i]==tag){
                //         exist=true;
                //         break;
                //     }
                // }
                if (tag.length>0){
                    exist=true;
                }
                if (exist){
                    this.getTagData(tag);
                } else{
                    toastr.error("不存在该标签");
                }
            },
            showAll:function(){

                $("#showAllButton").attr("disabled",'disabled');
                if (this.allShow==false){
                  this.allShow=true;
                  toastr.success("已加载全部");
                }else{
                  toastr.warning("已经是全部标签内容");
                }
            },
            bulkTagsAdd:function () {
                var originString=$("#tagOriginsTextArea").val();
                var tagString=$("#bulkTagsInputText").val();
                if (originString!=null && tagString!=null && originString.length>0 && tagString.length>0){
                    var originStringList=originString.split(/[,/，;；。.]/);
                    var transferTagInfo=tagString+":";
                    var hasOrigin=false;
                    for (var i=0;i<originStringList.length;i++){
                        var eachListElement=originStringList[i];
                        if (eachListElement.length>0){
                            hasOrigin=true;
                            transferTagInfo=transferTagInfo+eachListElement+"-";
                        }
                    }
                    if (hasOrigin){
                        console.log(transferTagInfo);
                        this.$http.get("/tags/bulkAddTags/"+transferTagInfo).then(
                            function (value) {
                                toastr.success("添加成功");
                                $("#tagOriginsTextArea").val("");
                                $("#bulkTagsInputText").val("");
                            }
                        );
                    } else{
                        toastr.warning("被标注内容全为分隔符！");
                    }

                }else{
                    toastr.error("标签和标注的词都不得为空");
                }
            },
            addInputDom:function () {
                this.inputNumber=this.inputNumber+1;
            },
            hideInput:function (n) {
                if (this.inputNumber==1){
                    toastr.error("不可少于一个输入框")
                }else{
                    this.inputNumber=this.inputNumber-1;
                }
                // $("#addInput"+''+n).hide();
                // $("#input"+''+n).val("");
            },
            confirmAddTags:function () {
                $("#titleAddTagButton").attr("disabled","disabled");
                var hasTag=false;
                var tagInfoString=this.oneTag.title+":";
                var newTags=[];
                for(var i=0;i<this.inputNumber;i++){
                    var index=i+1;
                    var tagInputI=$("#input"+''+index).val();
                    if (tagInputI!=null && tagInputI.length>0){
                        hasTag=true;
                        tagInfoString=tagInfoString+tagInputI+"-";
                        newTags.push(tagInputI);
                    }
                }
                console.log(tagInfoString);
                if (hasTag){
                    this.$http.get("/tags/addTitleTag/"+tagInfoString).then(function (value) {
                        for(var j=0;j<this.tagInfos.length;j++){
                            if (this.tagInfos[j].title==this.oneTag.title){
                                for(var k=0;k<newTags.length;k++){
                                    this.tagInfos[j].tags.push(newTags[k]);
                                }
                            }
                        }
                        this.inputNumber=1;
                        $("#input1").val("");
                        $("#addModal").map(function () {
                            if (!$(this).is(":hidden")){
                                $(this).modal('hide');
                            }
                        });
                        toastr.success("添加成功！")
                    })
                } else{
                    toastr.error("至少存在一个分词！");
                }
                $("#titleAddTagButton").attr("disabled",false);

            }
        },
        mounted:function () {
            this.$http.get("/tags/getAllTags")
                .then(function (value) {
                    this.tagInfos=value.data;
                })
                .catch(function (e) {
                console.log(e);
                toastr.error("错误"+e.status+":"+e.statusText);
            });
            this.$http.get("/tags/getTagList").then(function (value) {
                this.allTags=value.data;
                if (this.allTags.length>10){
                    this.hotButtons=[];
                    for(var i=0;i<10;i++){
                        this.hotButtons.push(this.allTags[i]);
                    }
                }else{
                    this.hotButtons=this.allTags;
                }
            });
        }
    })
}