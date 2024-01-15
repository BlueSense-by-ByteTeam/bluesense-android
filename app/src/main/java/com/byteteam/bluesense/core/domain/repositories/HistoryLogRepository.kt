package com.byteteam.bluesense.core.domain.repositories

import com.byteteam.bluesense.core.data.common.Resource
import com.byteteam.bluesense.core.data.remote.network.response.MessageResponse
import com.byteteam.bluesense.core.domain.model.DeviceEntity
import com.byteteam.bluesense.core.domain.model.DeviceLatestInfoEntity
import com.byteteam.bluesense.core.domain.model.DevicePost
import com.byteteam.bluesense.core.domain.model.LogHistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryLogRepository {
    suspend fun getTodayHistory() : Flow<Resource<LogHistoryEntity>>
    suspend fun getWeekHistory() : Flow<Resource<LogHistoryEntity>>
    suspend fun getMonthHistory() : Flow<Resource<LogHistoryEntity>>
    suspend fun getYearHistory() : Flow<Resource<LogHistoryEntity>>
}