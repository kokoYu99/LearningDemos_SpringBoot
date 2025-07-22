<script setup>
/* 
    1. 导入依赖
    2. 定义页面展示的数据，包括响应式数据
        响应式数据：修改数据后，页面展示的数据随之更新
        Vue3的数据默认不具备响应性，必须主动设置
    3. 定义Vue的周期函数，加载初始化数据
    4. 定义事件函数，自定义逻辑
*/

/* 1. 导入依赖和自己定义的api方法 */
import { ref, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import RichTextEditor from "../../components/RichTextEditor.vue";
import {
    articleList,
    articleDetails,
    articleInsert,
    articleUpdate,
    articleDelete,
} from '../../api/article';
import {
    categoryAll
} from '../../api/category.js';
import cookie from 'js-cookie';


/* 2. 定义全局常量 */
//类别集合的响应式数据
const categoryList = ref([]);

//文章列表的响应式数据
let artiList = ref([]);

//修改/新增、展示详情的对话框的显示开关
const dialogVisible = ref(false);
const dialogShowVisible = ref(false);



//文章的响应式对象，接收修改/新增的文章数据
let article = ref({
    id: "",
    cid: "",
    title: "",
    content: "",
    uid: ``
}); // 使用ref包裹该对象，使用reactive不方便进行重置


/* 3. 定义周期函数 */
onMounted(() => {
    getArticleList();
    getCategoryList();
});
const getArticleList = async () => {
    const { data } = await articleList();
    artiList.value = data;
}
const getCategoryList = async () => {
    const { data } = await categoryAll();
    categoryList.value = data;
}

/* 4. 定义事件函数 */
//删除
const deleteById = (id) => {
    ElMessageBox.confirm("确定删除？", {
        confirmButtonText: "确定",
        cancelButtonText: '取消',
        type: 'warning'
    }).then(async () => {
        const { code, message } = await articleDelete(id);
        if (code == 200) {
            ElMessage.success('删除成功');
            getArticleList();
        } else {
            ElMessage.error(message);
        }
    });
}
//详情
const show = (articleDetail) => {
    //更新文章对象
    article.value = articleDetail;
    //弹框
    dialogShowVisible.value = true;
};
//添加(新增)时弹出对话框
const addShow = () => {
    //将文章置空
    article.value = {};
    //弹框
    dialogVisible.value = true;
}
//修改时弹出对话框
const updateShow = (articleUpdated) => {
    //文章回显
    article.value = articleUpdated;
    //弹框
    dialogVisible.value = true;
}
//提交
const submit = async () => {
    //从cookie获取uid
    const uid = cookie.get('uid');
    //给文章对象添加uid
    article.value.uid = uid;

    /* 调用后端接口 */
    if (!article.value.id) { //id为null，==false，!false==true
        //新增(无id值)
        const { code } = await articleInsert(article.value);
        if (code == 200) {
            ElMessage.success(`文章 ${article.value.title} 添加成功！`);
            getArticleList();
        } else {
            ElMessage.error(`文章 ${article.value.title} 添加失败！`);
        }
    } else {
        //修改(有id值)
        const { code } = await articleUpdate(article.value);
        if (code == 200) {
            ElMessage.success(`文章 ${article.value.title} 修改成功！`);
            getArticleList();
        } else {
            ElMessage.error(`文章 ${article.value.title} 修改失败！`);
        }
    }

    //操作完毕，关闭对话框
    dialogVisible.value = false;
}

</script>


<template>
    <div class="search-div">
        <!-- 添加按钮 -->
        <div class="tools-div">
            <el-button type="success" size="small" @click="addShow">添 加</el-button>
        </div>

        <!--- 文章表格数据 -->
        <el-table :data="artiList" style="width: 100%">
            <el-table-column prop="id" label="文章ID" width="180" />
            <el-table-column prop="title" label="文章标题" width="180" />
            <el-table-column prop="createTime" label="发布时间" />
            <el-table-column label="操作" alignment="center" width="280" #default="scope">
                <el-button type="primary" size="small" @click="show(scope.row)">
                    查看详情
                </el-button>
                <el-button type="warning" size="small" @click="updateShow(scope.row)">修 改</el-button>
                <el-button type="danger" size="small" @click="deleteById(scope.row.id)">
                    删 除
                </el-button>
            </el-table-column>
        </el-table>

        <!-- 文章增改对话框 -->
        <el-dialog v-model="dialogVisible" title="添加或修改文章" width="60%">
            <el-form label-width="100px">
                <el-form-item label="文章标题">
                    <el-input v-model="article.title" />
                </el-form-item>

                <el-form-item label="所属分类">
                    <el-select class="m-2" v-model="article.cid" placeholder="选择分类" size="small" style="width: 100%">
                        <el-option v-for="item in categoryList" :key="item.id" :label="item.cname" :value="item.cid" />
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

        <!-- 文章详情对话框 -->
        <el-dialog v-model="dialogShowVisible" title="文章详情" width="60%">
            <el-tag>{{ article.title }}</el-tag>
            <div v-html="article.content"></div>
        </el-dialog>

    </div>
</template>


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
