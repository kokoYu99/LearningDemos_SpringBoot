package com.koko.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.koko.exception.BusinessException;
import com.koko.exception.ResourceNotFoundException;
import com.koko.mapper.CategoryMapper;
import com.koko.pojo.Category;
import com.koko.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper mapper;

    /* 查询全部类别 */
    @Override
    public List<Category> queryAll() {
        List<Category> list = mapper.queryAll();
        return list;
    }

    /* 分页+关键字模糊查询 */
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer pageSize, String keyword) {
        //1. 设置分页参数
        PageHelper.startPage(page, pageSize);

        //2. 根据关键字查询
        List<Category> list = mapper.queryByPage(keyword);

        //3. 封装到pageInfo，取出分页后的数据
        PageInfo<Category> pageInfo = new PageInfo<>(list);

        //4. 将分页后的数据和total总页数，打包为data，返回
        HashMap<String, Object> data = new HashMap<>();
        data.put("total", pageInfo.getTotal());
        data.put("list", pageInfo.getList());

        return data;
    }

    /* 查询类别详情 */
    @Override
    public Category queryDetails(Long id) {
        Category category = mapper.queryDetails(id);
        return category;
    }

    /* crud
     * 查 和 删除 没有失败的!!!
     * 删除即使"失败"，也是因为删除时数据不存在，最终仍然达到了删除的目的
     * ----------------------
     * 修改、添加 有失败的!
     * 数据不合法：数据库 -> 约束 -> 唯一约束 非空约束 主外键约束....
     */

    /* 类别新增 */
    @Override
    public void insert(Category category) {
        /* 检查类别是否已存在，如果类别已存在，抛异常 */
        if (mapper.isCNameExists(category.getCName()) > 0) {
            throw new BusinessException(400, "类别已存在，请修改");
        }

        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        int rows = mapper.insert(category);

        if (rows == 0) {
            throw new BusinessException(500, "新增失败，请稍后再试");
        }
    }

    /* 类别修改 */
    @Override
    public void update(Category category) {
        /* 检查类别是否已存在，如果类别已存在，抛异常 */
        if (mapper.isCNameExists(category.getCName()) > 0) {
            throw new BusinessException(400, "类别已存在，请修改");
        }

        category.setUpdateTime(new Date());
        int rows = mapper.update(category);

        if (rows == 0) {
            throw new BusinessException(500, "修改失败，请稍后再试");
        }
    }

    /* 类别删除 */
    @Override
    public void delete(Long id) {
        /* 检查类别是否存在。不存在，抛异常 */
        if (mapper.queryDetails(id) == null) {
            throw new ResourceNotFoundException("删除失败，类别不存在");
        }

        int rows = mapper.delete(id);

        if (rows == 0) {
            throw new BusinessException(500, "删除失败，请稍后再试");
        }
    }
}
