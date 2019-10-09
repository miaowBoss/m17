package com.access.m17codetask.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.access.m17codetask.R
import com.access.m17codetask.dataClass.User
import com.access.m17codetask.util.ApiResponseHelper
import com.access.m17codetask.viewModel.MainViewModel
import com.access.m17codetask.viewModel.MainViewModelFactory
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val adapter: UserAdapter = UserAdapter()

    val vm: MainViewModel by lazy {
        ViewModelProviders.of(this, MainViewModelFactory()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModelObserver()
        setUserRecyclerViewView()
        setListener()
    }

    private fun setListener() {
        ibSend.setOnClickListener(this)
        etSearch.setOnEditorActionListener { v, actionId, event ->
            sendSearch(v.text.toString())
            false
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ibSend -> {
                sendSearch(etSearch.text.toString())
            }
        }
    }

    private fun sendSearch(name : String){
        vm.getUser(name)
        adapter.userList.clear()
        showLoading(true)
    }

    private fun setUserRecyclerViewView() {

        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = LinearLayoutSpaceItemDecoration(10)
        rvUserList.adapter = adapter
        rvUserList.layoutManager = layoutManager
        rvUserList.addItemDecoration(dividerItemDecoration)

        rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var mShouldReload = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                mShouldReload = firstVisibleItem + visibleItemCount == totalItemCount && dy > 0
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mShouldReload) {
                    showLoading(true)
                    vm.nextPage()
                }
            }
        })
    }

    private fun setupViewModelObserver() {
        vm.userResponseLiveData.observe(
            this, ApiResponseHelper.handle(
                Consumer { isLoading -> showLoading(isLoading) },
                Consumer { error -> showError(error) },
                Consumer { response ->
                    response?.items?.forEach { item ->
                        val user = User(item.avatar_url, item.login)
                        adapter.userList.add(user)
                        showLoading(false)
                    }
                    adapter.notifyDataSetChanged()
                })
        )
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            pgLoad.visibility = View.VISIBLE
            ibSend.visibility = View.INVISIBLE
        } else {
            pgLoad.visibility = View.INVISIBLE
            ibSend.visibility = View.VISIBLE
        }
    }

    fun showError(e: Throwable) {
        Toast.makeText(applicationContext, "error:$e", Toast.LENGTH_SHORT).show()
    }

}
