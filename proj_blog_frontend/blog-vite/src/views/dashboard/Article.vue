<template>
    <div class="search-div">

        <!-- 添加按钮 -->
        <div class="tools-div">
            <el-button type="success" size="small" @click="addShow()">添 加</el-button>
        </div>
        
        <!--- 文章表格数据 -->
        <el-table :data="listRef" style="width: 100%">
            <el-table-column prop="id" label="文章ID" width="180" />
            <el-table-column prop="title" label="文章标题" width="180" />
            <el-table-column prop="createTime" label="发布时间" />
            <el-table-column label="操作" align="center" width="280" #default="scope">
                <el-button type="primary" size="small" @click="show(scope.row)">
                    详情
                </el-button>
                <el-button type="info" size="small" @click="update(scope.row)">
                    修改
                </el-button>
                <el-button type="danger" size="small" @click="deleteById(scope.row.id)">
                    删除
                </el-button>
            </el-table-column>
        </el-table>

        <el-dialog v-model="dialogVisible" title="添加或修改文章" width="60%">
            <el-form label-width="100px">
            <el-form-item label="文章标题">
                <el-input v-model="article.title"/>
            </el-form-item>

            <el-form-item label="所属分类">
                <el-select
                class="m-2"
                v-model="article.cid"
                placeholder="选择分类"
                size="small"
                style="width: 100%"
                >
                <el-option
                    v-for="item in categoryListRef"
                    :key="item.id"
                    :label="item.cname"
                    :value="item.cid"
                />
                </el-select>
          </el-form-item>

            <el-form-item label="文章内容">
                <rich-text-editor v-model="article.content"></rich-text-editor>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="submit">提交</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
            </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog v-model="dialogShowVisible" title="文章详情" width="60%">
            <el-tag>{{article.title}}</el-tag>
            <div v-html="article.content"></div>
        </el-dialog>

  </div>

</template>

<script setup>
//1.添加依赖
import { ref , onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus'
import RichTextEditor from '../../components/RichTextEditor.vue'
//自己声明的方法
import {articleList,articleAdd,articleDelete,articleDetail,articleUpdate} from '../../api/article'
import {categoryList} from '../../api/category'
import cookie from 'js-cookie'

//类别的响应式数据
const categoryListRef = ref([])
//文章列表的响应式数据
let listRef = ref([])

//修改保存和详情的dialog框的显示开关,默认关闭
const dialogVisible = ref(false)
const dialogShowVisible = ref(false)


const defaultForm = {
    id: "",
    cid:"",
    title: "",
    content:""
}
//接收修改和保存框的响应对象
const article = ref(defaultForm)   


//3.初始化函数
onMounted(()=>{
    //触发此周期,调用内部函数
    getArticleList();
    getCategoryList();
})

const getArticleList =async ()=>{
    //初始访问,不传
    let {data} = await articleList();
    listRef.value = data;
  }

  const getCategoryList =async()=>{
    let {data} = await categoryList();
    categoryListRef.value = data;
  }

//4. 点击事件对应业务逻辑
const deleteById =async (id)=>{
   const {code} = await articleDelete(id)
   getArticleList();
}


const show =  (detailArticle) =>{
    //把数据复制给展示数据
    article.value = detailArticle
    dialogShowVisible.value = true
}

//弹出添加框
const addShow = ()=>{
   //1. article = null
   article.value = {}
   //2. 弹出添加框
   dialogVisible.value = true
}

const submit = async ()=>{
  
    if(!article.value.id){
       //添加逻辑
        const {code} = await articleAdd(article.value)
        if(code == 200){
            ElMessage(`文章:${article.value.title},添加成功!!`)
            getArticleList();
        }else{
            ElMessage(`文章:${article.value.title},添加失败!!`)
        }
        
    }else{
        const {code} = await articleUpdate(article.value)
        if(code == 200){
            ElMessage(`文章:${article.value.title},修改成功!!`)
            getArticleList();
        }else{
            ElMessage(`文章:${article.value.title},修改失败!!`)
        }
     
    }

    dialogVisible.value = false

   
}

//修改
const update = (updateArticle)=>{

    article.value = updateArticle;
    dialogVisible.value = true
}




</script>

<style scoped>

.search-div {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}

.tools-div {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}

</style>