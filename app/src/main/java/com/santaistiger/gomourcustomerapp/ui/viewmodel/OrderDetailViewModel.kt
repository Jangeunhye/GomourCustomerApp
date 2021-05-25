package com.santaistiger.gomourcustomerapp.ui.viewmodel

import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.santaistiger.gomourcustomerapp.data.model.AccountInfo
import com.santaistiger.gomourcustomerapp.data.model.Order
import com.santaistiger.gomourcustomerapp.data.repository.Repository
import com.santaistiger.gomourcustomerapp.data.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OrderDetailViewModel(val orderId: String) : ViewModel() {
    companion object {
        private const val TAG = "OrderDetailViewModel"
    }

    val order: MutableLiveData<Order> = liveData(Dispatchers.IO) {
        emit(repository.readOrderDetail(orderId))
    } as MutableLiveData<Order>

    val accountInfo = ObservableField<String>()
    val isCallBtnClick = MutableLiveData<Boolean>()
    val isTextBtnClick = MutableLiveData<Boolean>()

    private val repository: Repository = RepositoryImpl

    fun getAccountInfo() {
        viewModelScope.launch {
            accountInfo.set(repository.readDeliveryManAccount(order.value!!.deliveryManUid!!))
        }
    }

    fun refresh() {
        viewModelScope.launch {
            order.value = repository.readOrderDetail(orderId)
        }
    }

    fun onCallBtnClick() {
        isCallBtnClick.value = true
    }

    fun doneCallBtnClick() {
        isCallBtnClick.value = false
    }

    fun onTextBtnClick() {
        isTextBtnClick.value = true
    }

    fun doneTextBtnClick() {
        isTextBtnClick.value = false
    }

    fun getDeliveryManUid() = order.value!!.deliveryManUid!!
}