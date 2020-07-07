package com.axxess.android.axxesschallenge.app.activity

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axxess.android.axxesschallenge.R
import com.axxess.android.axxesschallenge.app.adapter.SearchListAdapter
import com.axxess.android.axxesschallenge.app.listeners.ItemClickListener
import com.axxess.android.axxesschallenge.app.model.search.ImgurDataItem
import com.axxess.android.axxesschallenge.app.utils.MConstants
import com.axxess.android.axxesschallenge.app.utils.afterTextChanged
import com.axxess.android.axxesschallenge.app.utils.isInLandScapeMode
import com.axxess.android.axxesschallenge.app.utils.showOkaySnackBar
import com.axxess.android.axxesschallenge.app.viewModel.MainActivityViewModel
import com.axxess.android.axxesschallenge.core.ui.ViewState
import com.axxess.android.axxesschallenge.core.ui.base.BaseActivity
import com.axxess.android.axxesschallenge.core.utils.*
import com.axxess.android.axxesschallenge.databinding.ActivityMainBinding

class MainActivity : BaseActivity(), ItemClickListener {

    private val TAG = "MainActivity"

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by lazy { getViewModel<MainActivityViewModel>() }
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var scrollListener: RecyclerView.OnScrollListener

    private val searchListAdapter = SearchListAdapter()
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initView()
        setUpPagination()
        setEtSearchListener()
        setObservers()
    }

    private fun setEtSearchListener() {
        val pageNum =
            if (viewModel.prevPageNum == MConstants.PAGINATION_INITIAL_PAGE_NUMBER) MConstants.START_PAGE_NUMBER else viewModel.prevPageNum

        binding.searchEt.afterTextChanged {
            if (it.isNotEmpty())
                viewModel.getImgurSearchData(pageNum.toString(), it)
        }
    }

    private fun setObservers() {
        viewModel._imgurSearchResponse.observeNotNull(this) { state ->
            when (state) {
                is ViewState.Success -> {
                    if (state.data.currentPageNum == MConstants.START_PAGE_NUMBER && state.data.data.isNullOrEmpty()) {
                        binding.userMsgTv.visible()
                        binding.searchRecyclerView.invisible()
                        binding.userMsgTv.text =
                            getString(R.string.no_results, binding.searchEt.text.toString().trim())
                    } else {
                        binding.userMsgTv.invisible()
                        binding.searchRecyclerView.visible()
                        searchListAdapter.setData(state.data.data)
                    }
                }
                is ViewState.Loading -> {
                    binding.searchProgressBar.setVisibilityWithINVISIBLE(state.data)

                    if (viewModel.nextPageNumber == MConstants.START_PAGE_NUMBER) {
                        binding.searchProgressBar.setVisibilityWithINVISIBLE(state.data)
                    }

                    isLoading = state.data
                }
                is ViewState.Error -> {
                    showOkaySnackBar(binding.root, state.message)
                }
            }
        }
    }

    override fun onItemClick(item: ImgurDataItem) {
        gotoDetailsActivity(item)
    }

    private fun gotoDetailsActivity(data: ImgurDataItem) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MConstants.SEARCH_ITEM_DETAILS_INTENT_FILTER_NAME, data)
        startActivity(intent)
    }

    private fun initView() {
        gridLayoutManager = if (resources.isInLandScapeMode()) {
            GridLayoutManager(this, MConstants.SEARCH_LIST_LANDSCAPE_MODE_ROW_ITEM_COUNT)
        } else {
            GridLayoutManager(this, MConstants.SEARCH_LIST_PORTRAIT_MODE_ROW_ITEM_COUNT)
        }

        binding.searchRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = searchListAdapter
        }
        searchListAdapter.setItemClickListener(this)

        val spacing =
            resources.getDimensionPixelSize(R.dimen.search_list_item_spacing) / 2

        binding.searchRecyclerView.setPadding(spacing, spacing, spacing, spacing)
        binding.searchRecyclerView.clipToPadding = false
        binding.searchRecyclerView.clipChildren = false
        binding.searchRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        })
    }

    private fun setUpPagination() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val visibleItemCount: Int = gridLayoutManager.getChildCount()
                val totalItemCount: Int = gridLayoutManager.getItemCount()
                val firstVisibleItemPosition: Int =
                    gridLayoutManager.findFirstVisibleItemPosition()

                if (!isLoading) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0 && !viewModel.isReachedToEndOfList
                    ) {
                        viewModel.getImgurSearchData(
                            pageNum = viewModel.nextPageNumber.toString(),
                            searchQuery = binding.searchEt.text.toString().trim()
                        )
                    }
                }
            }
        }
        binding.searchRecyclerView.addOnScrollListener(scrollListener)
    }
}