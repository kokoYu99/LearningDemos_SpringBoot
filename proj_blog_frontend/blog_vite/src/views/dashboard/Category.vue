<script setup>
/* 1. 导入依赖 */
import { ref, onMounted } from "vue";
import {
    categoryAll,
    categoryPage,
    categoryDetails,
    categoryInsert,
    categoryUpdate,
    categoryDelete,
} from '../../api/category.js';
import { ElMessage, ElMessageBox } from "element-plus";



/* 2. 定义全局常量 */
//类别集合
let categoryList = ref([]);

//输入的关键词，用于搜索类别
const keywordInput = ref({ cname: "" });

//分页数据
const pageParams = ref({
    page: 1, // 页码
    pageSize: 5, // 每页记录数
});

// 分页条总记录数
let total = ref(0);

//类别对象，在新增/修改时接收表单输入的类别数据
const category = ref({
    cid: "",
    cname: "",
});

//对话框的显示开关
const dialogVisible = ref(false);



/* 3. 周期函数，初始化加载数据 */
onMounted(() => {
    //加载全部类别数据
    getCategoryList();
})




/* 4. 事件函数 */

//获取全部类别数据
const getCategoryList = async () => {
    //获取分页后的类别数据
    const { data } = await categoryPage(pageParams.value.page, pageParams.value.pageSize, keywordInput.value.cname);
    //获取类别集合
    categoryList.value = data.list;
    //获取类别总数
    total.value = data.total;
}

//重置类别数据
const resetData = () => {
    //将输入的类别关键字置空
    keywordInput.value.cname = null;
    //重新获取类别集合
    getCategoryList();
}

//新增类别的弹框
const addShow = () => {
    //将类别对象置空
    category.value = {};
    //弹框
    dialogVisible.value = true;
}
//修改类别的弹框
const editShow = (row) => {
    //展示原有数据：将传入的类别对象赋给全局类别对象
    category.value = row;
    //弹框
    dialogVisible.value = true;
}
//提交类别
const submit = async () => {
    if (!category.value.cid) {
        //cid为null，进行新增，获取状态码
        const { code } = await categoryInsert(category.value);
        if (code == 200) {
            ElMessage.success(`${category.value.cname} 添加成功！`);
            getCategoryList();
        } else {
            ElMessage.error(`${category.value.cname} 添加失败！`);
        }
        dialogVisible.value = false;
    } else {
        //cid不为null，进行修改，获取状态码
        const { code } = await categoryUpdate(category.value);
        if (code == 200) {
            ElMessage.success(`${category.value.cname} 修改成功！`);
            getCategoryList();
        } else {
            ElMessage.error(`${category.value.cname} 修改失败！`);
        }
        dialogVisible.value = false;
    }
}

//删除类别
const deleteById = (cid) => {
    //提示框
    ElMessageBox.confirm('确定删除？', 'warning', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

    }).then(async () => {
        //进行删除，获取状态码
        const { code } = await categoryDelete(cid);
        if (code == 200) {
            ElMessage.success(`${category.value.cname} 删除成功！`);
            pageParams.value.page = 1;
            getCategoryList();
        } else {
            ElMessage.error(`${category.value.cname} 删除失败！`);
        }
    })

    //隐藏弹框
    dialogVisible.value = false;
}


</script>


<template>
    <div class="search-div">
        <!-- 搜索表单 -->
        <el-form label-width="70px" size="small">
            <el-form-item label="分类名称">
                <el-input v-model="keywordInput.cname" style="width: 100%" placeholder="角色名称"></el-input>
            </el-form-item>
            <el-row style="display: flex">
                <el-button type="primary" size="small" @click="getCategoryList">
                    搜索
                </el-button>
                <el-button size="small" @click="resetData">重置</el-button>
            </el-row>
        </el-form>

        <!-- 添加按钮 -->
        <div class="tools-div">
            <el-button type="success" size="small" @click="addShow">添 加</el-button>
        </div>

        <!--- 类别表单数据 -->
        <el-table :data="categoryList" style="width: 100%">
            <el-table-column prop="cid" label="分类id" width="180" />
            <el-table-column prop="cname" label="分类名称" width="180" />
            <el-table-column prop="createTime" label="创建时间" />
            <el-table-column label="操作" alignment="center" width="280" #default="scope">
                <el-button type="primary" size="small" @click="editShow(scope.row)">
                    修改
                </el-button>
                <el-button type="danger" size="small" @click="deleteById(scope.row.cid)">
                    删除
                </el-button>
            </el-table-column>
        </el-table>

        <!--分页条-->
        <el-pagination v-model:current-page="pageParams.page" v-model:page-size="pageParams.pageSize"
            @size-change="getCategoryList" @current-change="getCategoryList" layout="total, prev, pager, next"
            :total="total" />

        <el-dialog v-model="dialogVisible" title="添加或修改" width="30%">
            <el-form label-width="120px">
                <el-form-item label="分类名称">
                    <el-input v-model="category.cname" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submit">提交</el-button>
                    <el-button @click="dialogVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
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
