package com.example.mvvmdemo.view.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.ActivityMainBinding
import com.example.mvvmdemo.models.PaginatedInfoModel
import com.example.mvvmdemo.models.request.TempModel
import com.example.mvvmdemo.models.response.TempResponseModel
import com.example.mvvmdemo.retrofit.APIClient
import com.example.mvvmdemo.retrofit.ResponseHandler
import com.example.mvvmdemo.utils.constants.ApiConst
import com.example.mvvmdemo.utils.constants.DialogConst
import com.example.mvvmdemo.utils.enums.DialogEventType
import com.example.mvvmdemo.utils.enums.ItemClickType
import com.example.mvvmdemo.utils.helpers.AppHelper
import com.example.mvvmdemo.utils.helpers.DialogHelper
import com.example.mvvmdemo.utils.helpers.GsonHelper
import com.example.mvvmdemo.utils.helpers.LogHelper
import com.example.mvvmdemo.utils.listeners.RecyclerViewItemListener
import com.example.mvvmdemo.view.adapters.TempAdapter
import com.example.mvvmdemo.viewModel.CommonViewModel
import com.example.mvvmdemo.view.view.activities.base.BaseActivity
import com.google.gson.reflect.TypeToken
import retrofit2.Call


class MainActivity : BaseActivity(), RecyclerViewItemListener {

    protected val TAG: String = this::class.java.simpleName
    private var tempAdapter: TempAdapter? = null
    private var list: ArrayList<TempResponseModel> = ArrayList()

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CommonViewModel

    private val requestPaginatedInfoModel: PaginatedInfoModel = PaginatedInfoModel()
    private var isClaimsListLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProviders.of(this).get(CommonViewModel::class.java)

        setListeners()
        init()
    }

    /**
     * Here actionBar is being setup
     */
    private fun setUpToolbar(title: String?) {
        tvTitle?.text = title
    }

    private fun setUpAdapterOrRefresh() {
        if (tempAdapter == null) {
            tempAdapter = TempAdapter(this, list, this)
            binding.recyclerview.adapter = tempAdapter
            binding.recyclerview.addOnScrollListener(listScrollListener)
        }else {
            tempAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * Setting listeners to views
     */
    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener(refreshListener)
        ivSearch.setOnClickListener(clickListener)
        ivBack.setOnClickListener(clickListener)
    }

    /**
     * Initializing the objects and data to initial state
     */
    private fun init() {
        setUpToolbar(title = getString(R.string.app_name))
        setUpSwipeRefreshLayout(binding.swipeRefresh)

        callGetMoviePaginationApi()
        callGetMovieApi()
        callPostApi()

    }

    /**
     * Swipe Refresh event on SwipeRefreshLayout handles here
     */
    private val refreshListener: SwipeRefreshLayout.OnRefreshListener =
        SwipeRefreshLayout.OnRefreshListener {
            refreshContent()
        }

    /**
     * This is called to refresh the list by removing the old data and fetching the
     * server data again
     */
    private fun refreshContent() {
        LogHelper.debug(tag = TAG, msg = "refreshContent")
        isClaimsListLoading = false

        callGetMoviePaginationApi()
        callGetMovieApi()
        callPostApi()
    }

    /**
     * Click event on views handles here
     */
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        if (AppHelper.shouldProceedClick())
            when (view.id) {
                R.id.ivBack -> clickedSearchBack()

                R.id.ivSearch -> clickedSearch()

                R.id.btnLogout -> clickedLogout()
            }
    }

    /**
     * It is called when user click on logout view
     */
    private fun clickedLogout() {
        DialogHelper.showPositiveNegativeAlertDialog(
            baseActivity = this,
            requestCode = DialogConst.REQUEST_CODE.LOGOUT,
            title = null,
            message = getString(R.string.dialog_body_logout),
            positiveButtonText = getString(R.string.yes),
            negativeButtonText = getString(R.string.no),
            data = null,
            listener = this
        )
    }

    /**
     * Scrolling event on ClaimsList RecyclerView handles here
     */
    private val listScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                checkAndLoadMoreData(dy, false)
            }
        }

    /**
     * Here more data is loaded after checking whether recyclerView should load more claims listing data or not.
     *
     * @param dy
     * @param errorMsgStatus
     */
    private fun checkAndLoadMoreData(dy: Int, errorMsgStatus: Boolean) {
        if (AppHelper.shouldLoadMore(
                paginatedInfoModel = requestPaginatedInfoModel,
                dx = null,
                dy = dy,
                layoutManager = binding.recyclerview.layoutManager as LinearLayoutManager,
                isListLoading = isClaimsListLoading,
                isReverseLayout = false
            )
        ) {
            isClaimsListLoading = true

            callGetMoviePaginationApi()

        } else if (errorMsgStatus) {

        }
    }

    private fun callGetMoviePaginationApi() {
        requestPaginatedInfoModel.currentPage += 1

        showHideLoadingDialog(true, "")

        val call = APIClient.getClient.doGetPaginationListResources(requestPaginatedInfoModel.currentPage)
        hitRequestToServer(call, ApiConst.REQUEST_CODE.GET_MOVIE_PAGINATION)
    }

    private fun callGetMovieApi() {
        showHideLoadingDialog(true, "")

        val call = APIClient.getClient.doGetListResources()
        hitRequestToServer(call, ApiConst.REQUEST_CODE.GET_MOVIE)
    }

    private fun callPostApi() {
        val model = TempModel("sample@gmail.com", "123456", "user", "android", "token")

        showHideLoadingDialog(true, "")
        val call = APIClient.getClient.Login(model)
        hitRequestToServer(call, ApiConst.REQUEST_CODE.LOGIN)
    }

    private fun hitRequestToServer(call: Call<Any>, requestCode: String) {
        viewModel.sendRequestToServer(call, requestCode).observe(this@MainActivity) {
            showHideLoadingDialog(false, "")

            when (it.status) {

                ResponseHandler.Status.SUCCESS -> onApiSuccess(it)

                ResponseHandler.Status.ERROR -> onApiFailure(it)

                else -> showToastMessage("Server error")

            }
        }
    }

    private fun onApiSuccess(it: ResponseHandler<Any>?) {
        when (it?.requestCode) {
            ApiConst.REQUEST_CODE.GET_MOVIE_PAGINATION -> onGetMoviePaginationApiSuccess(it.data!!, it.message)

            ApiConst.REQUEST_CODE.GET_MOVIE -> onGetMovieApiSuccess(it.data!!, it.message)

            ApiConst.REQUEST_CODE.LOGIN -> onLoginApiSuccess(it.data!!, it.message)

        }
    }

    private fun onApiFailure(it: ResponseHandler<Any>?) {
        when (it?.requestCode) {
            ApiConst.REQUEST_CODE.GET_MOVIE_PAGINATION -> {
                showToastMessage(it.message)
                isClaimsListLoading = false
            }

            ApiConst.REQUEST_CODE.GET_MOVIE -> showToastMessage(it.message)

            ApiConst.REQUEST_CODE.LOGIN -> showToastMessage(it.message)

        }
    }

    private fun onGetMoviePaginationApiSuccess(data: Any, message: String) {

        var tempList: ArrayList<TempResponseModel> = GsonHelper.convertJsonStringToJavaObject(
            data,
            object : TypeToken<ArrayList<TempResponseModel?>?>() {}.type
        ) as ArrayList<TempResponseModel>

        isClaimsListLoading = false

        addDataToList(tempList)

    }

    private fun addDataToList(listData: ArrayList<TempResponseModel>) {
        val oldSize: Int = list!!.size

        list?.addAll(listData)
        tempAdapter?.notifyItemRangeInserted(oldSize, list!!.size)

        listData.clear()

    }

    private fun onGetMovieApiSuccess(data: Any, message: String) {

        var tempList: ArrayList<TempResponseModel> = GsonHelper.convertJsonStringToJavaObject(
            data,
            object : TypeToken<ArrayList<TempResponseModel?>?>() {}.type
        ) as ArrayList<TempResponseModel>

        list = tempList
        setUpAdapterOrRefresh()

    }

    private fun onLoginApiSuccess(data: Any, message: String) {

    }

    override fun onRecyclerViewItemClick(
         itemClickType: ItemClickType,
        model: Any?,
        position: Int,
        view: View
    ) {
        when (itemClickType) {
            ItemClickType.SELECT_OPTION -> {
//                clickedSelectUser(
//                    tempResponseModel = model as TempResponseModel,
//                    position = position
//                )
            }

            else -> LogHelper.error(
                tag = TAG,
                msg = "onRecyclerViewItemClick: itemClickType- INVALID"
            )
        }
    }

    override fun onDialogEventListener(
        dialogEventType: DialogEventType,
        requestCode: Int,
        model: Any?
    ) {
        when (dialogEventType) {
            DialogEventType.POSITIVE ->
                onDialogPositiveEvent(requestCode = requestCode, model = model)

            else ->
                super.onDialogEventListener(
                    dialogEventType = dialogEventType,
                    requestCode = requestCode,
                    model = model
                )
        }
    }

    override fun onDialogPositiveEvent(requestCode: Int, model: Any?) {
        LogHelper.debug(tag = TAG, msg = "onDialogPositiveEvent: requestCode- $requestCode")

        when (requestCode) {
            DialogConst.REQUEST_CODE.LOGOUT -> {
                callLogoutProcess()
            }

            else -> super.onDialogPositiveEvent(requestCode = requestCode, model = model)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        goBack()

        callForCloseApp()
    }
}