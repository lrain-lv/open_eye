package com.app.eye.event

import com.app.eye.ui.mvp.model.entity.LoginEntity

data class LoginEvent(val loginEntity: LoginEntity) {
    override fun toString(): String {
        return "LoginEvent(loginEntity=$loginEntity)"
    }
}