package com.dicoding.batinco.data

//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.dicoding.batinco.data.response.RowsItem
//import com.dicoding.batinco.data.retrofit.ApiService
//
//class BatikPagingSource (private val apiService: ApiService, private val token: String) :
//    PagingSource<Int, RowsItem>() {
//
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RowsItem> {
//        return try {
//            val page = params.key ?: INITIAL_PAGE_INDEX
//            val responseDataStories = apiService.getAllBatik(page, params.loadSize)
//
//            LoadResult.Page(
//                data = responseDataStories.data.rows,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (responseDataStories.data.rows.isEmpty()) null else page + 1
//            )
//        } catch (exception: Exception) {
//            return LoadResult.Error(exception)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, RowsItem>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//}