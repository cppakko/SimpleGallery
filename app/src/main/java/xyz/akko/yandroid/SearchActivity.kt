package xyz.akko.yandroid

import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.search_layout.*
import xyz.akko.yandroid.logic.model.ImgAdapter
import xyz.akko.yandroid.ui.homePage.SearchPageViewModel
import xyz.akko.yandroid.util.MyActivityManager
import xyz.akko.yandroid.util.Utils
import java.lang.StringBuilder


class SearchActivity :AppCompatActivity() {

    private lateinit var adapter: ImgAdapter
    private val viewModel by lazy {
        ViewModelProvider(this)[SearchPageViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_layout)
        MyActivityManager().addActivity(this)
        title = "SearchPage"

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        searchRecyclerView.layoutManager = layoutManager
        adapter = ImgAdapter(viewModel.initList)
        searchRecyclerView.adapter = adapter

        viewModel.imgLiveData.observe(this, Observer {
            if (it.isEmpty())
            {
                Toast.makeText(this,"什么都没有搜索到呢", Toast.LENGTH_SHORT).show()
            }
            else
            {
                viewModel.initList.clear()
                viewModel.initList.addAll(it)
                adapter.notifyDataSetChanged()
                searchRecyclerView.visibility = View.VISIBLE
            }
            search_swiperefreshlayout.isRefreshing = false
        })
    }

    override fun onResume() {
        super.onResume()
        searchBar.setOnSearchActionListener(object: MaterialSearchBar.OnSearchActionListener{
            override fun onSearchStateChanged(enabled: Boolean) {
                //do nothing
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                search_swiperefreshlayout.isRefreshing = true
                if (!text.isNullOrEmpty())
                {
                    val tagsArr = text.split(" ")
                    val sb = StringBuilder()
                    for (i in tagsArr)
                    {
                        sb.append("$i+")
                    }
                    viewModel.search(sb.toString())
                    searchBar.closeSearch()
                }
            }

            override fun onButtonClicked(buttonCode: Int) {
                //do nothing
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        search_swiperefreshlayout.visibility = View.VISIBLE
        MyActivityManager().finishActivity(this)
    }
}