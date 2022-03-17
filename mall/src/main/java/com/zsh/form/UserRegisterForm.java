package com.zsh.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterForm {
    @NotBlank //用于String 判断空格
    //@NotEmpty 用于集合
    //@NotNull 用于判断是否为空
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
