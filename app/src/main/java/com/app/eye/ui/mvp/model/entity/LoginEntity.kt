package com.app.eye.ui.mvp.model.entity

data class LoginEntity(
    val error: Int,
    val member: Member?,
    val msg: String?
){
    override fun toString(): String {
        return "LoginEntity(error=$error, member=$member, msg=$msg)"
    }
}

data class Member(
    val avatar: String,
    val bindStatus: Int,
    val complete: Boolean,
    val description: String,
    val devices: MutableList<Device>,
    val emailValidated: Boolean,
    val enabled: Boolean,
    val gender: String,
    val needUpdatePassword: Boolean,
    val nick: String,
    val registDate: Long,
    val registerSource: String,
    val roles: MutableList<String>,
    val socials: MutableList<Any>,
    val telephone: String,
    val telephoneValidated: Boolean,
    val trusted: Boolean,
    val uid: Int,
    val username: String
)

data class Device(
    val id: Int,
    val udid: String
)