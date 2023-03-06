package com.kotlin.lib.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * T表示item的数据源类型
 */
abstract class BasePagingDataSource<T : Any>() : PagingSource<Int, T>() {
    /**
     * 获取数据
     */
    abstract suspend fun getData(pageSize: Int, pageNo: Int): List<T>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        try {
            val pageNo = params.key ?: 1
            val response = getData(params.loadSize, pageNo)

            val prevKey = if (pageNo > 1) pageNo - 1 else null
            val nextKey = if (response.isNotEmpty()) pageNo + 1 else null

            return LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}