package com.agamy.closeyourmouth.presentation.home.homechat

sealed  class HomeIntent {
    data class LoadChats(val userId: String) : HomeIntent()

}