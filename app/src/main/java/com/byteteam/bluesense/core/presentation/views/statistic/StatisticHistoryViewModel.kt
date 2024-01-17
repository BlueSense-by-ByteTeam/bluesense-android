package com.byteteam.bluesense.core.presentation.views.statistic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.domain.model.LogHistoryEntity
import com.byteteam.bluesense.core.domain.repositories.HistoryLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticHistoryViewModel  @Inject constructor(
    private val historyLogRepository: HistoryLogRepository
): ViewModel(){
    private var _historyLogs: MutableStateFlow<Resource<LogHistoryEntity>> = MutableStateFlow(Resource.Loading())
    val historyLogs: StateFlow<Resource<LogHistoryEntity>> = _historyLogs

    fun getTodayHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            _historyLogs.value = Resource.Loading()
            try {
                historyLogRepository.getTodayHistory().catch {
                    Log.d("TAG", "getTodayHistory: viewmodel")

                    val message = if(it.message == "Index: 0, Size: 0") "Belum ada data history" else it.message
                    _historyLogs.value = Resource.Error(message ?: "Error saat mengambil data history")
                }.collect{
                    _historyLogs.value = it
                }
            }catch (e: Exception){
                    Log.d("TAG", "getTodayHistory: viewmodel")
                val message = if(e.message == "Index: 0, Size: 0") "Belum ada data history" else e.message
             _historyLogs.value = Resource.Error(message ?: "Error saat mengambil data history")
            }
        }
    }

    fun getWeekHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            _historyLogs.value = Resource.Loading()
            try {
                historyLogRepository.getWeekHistory().catch {
                    _historyLogs.value = Resource.Error(it.message ?: "Error saat mengambil data history")
                }.collect{
                    _historyLogs.value = it
                }
            }catch (e: Exception){
                _historyLogs.value = Resource.Error(e.message ?: "Error saat mengambil data history")
            }
        }
    }

    fun getMonthHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            _historyLogs.value = Resource.Loading()
            try {
                historyLogRepository.getMonthHistory().catch {
                    _historyLogs.value = Resource.Error(it.message ?: "Error saat mengambil data history")
                }.collect{
                    _historyLogs.value = it
                }
            }catch (e: Exception){
                _historyLogs.value = Resource.Error(e.message ?: "Error saat mengambil data history")
            }
        }
    }

    fun getYearHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            _historyLogs.value = Resource.Loading()
            try {
                historyLogRepository.getYearHistory().catch {
                    _historyLogs.value = Resource.Error(it.message ?: "Error saat mengambil data history")
                }.collect{
                    _historyLogs.value = it
                }
            }catch (e: Exception){
                _historyLogs.value = Resource.Error(e.message ?: "Error saat mengambil data history")
            }
        }
    }
}