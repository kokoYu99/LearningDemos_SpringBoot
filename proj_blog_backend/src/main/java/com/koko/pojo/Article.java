package com.koko.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Schema(description = "文章参数")
@Data
public class Article {

    @Schema(description = "文章id")
    private Long id;

    @Schema(description = "文章标题")
    @NotBlank(message = "标题不能为空")
    @Size(min = 1, max = 30, message = "标题必须在1-30个字符之间")
    private String title; //文章标题

    @Schema(description = "文章内容")
    @NotBlank(message = "内容不能为空")
    @Size(min = 1, max = 140, message = "内容必须在1-140个字符之间")
    private String content; //文章内容

    @Schema(description = "类别id")
    @NotNull(message = "请选择分类")
    private Long cId; //分类id

    @Schema(description = "用户id")
    private Long uId; //用户id

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;

}